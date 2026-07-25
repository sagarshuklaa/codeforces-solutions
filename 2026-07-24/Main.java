/*
 * Problem: 2238F - Paths on a Grid
 * Link: https://codeforces.com/problemset/problem/2238/F
 * Rating: very high (2500+), uses dominator/post-dominator trees
 * Tags: graph theory, dominator tree, dsu
 *
 * Question:
 * Count "good sets" of cells S such that for every cell in S, every
 * (1,1)->(n,m) path through it also passes through all other cells in S.
 * answer = (2^k - 1) + sum over equivalence-classes-of-on-path-cells (2^size - 1)
 * where k = cells never on any path, and classes = cells sharing an
 * identical set of paths through them (found via idom/ipdom matching).
 */

import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 998244353;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        // precompute powers of 2 up to 1e6+5
        int MAXP = 1000005;
        long[] pow2 = new long[MAXP];
        pow2[0] = 1;
        for (int i = 1; i < MAXP; i++) pow2[i] = (pow2[i-1]*2) % MOD;

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            char[][] grid = new char[n][];
            for (int i = 0; i < n; i++) {
                grid[i] = br.readLine().toCharArray();
            }
            long ans = solve(n, m, grid, pow2);
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }

    static long solve(int n, int m, char[][] grid, long[] pow2) {
        boolean[][] free = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                free[i][j] = grid[i][j] == '1';

        boolean[][] reach = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!free[i][j]) continue;
                if (i == 0 && j == 0) { reach[i][j] = true; continue; }
                boolean up = i > 0 && reach[i-1][j];
                boolean left = j > 0 && reach[i][j-1];
                reach[i][j] = up || left;
            }
        }

        boolean[][] canreach = new boolean[n][m];
        for (int i = n-1; i >= 0; i--) {
            for (int j = m-1; j >= 0; j--) {
                if (!free[i][j]) continue;
                if (i == n-1 && j == m-1) { canreach[i][j] = true; continue; }
                boolean down = i < n-1 && canreach[i+1][j];
                boolean right = j < m-1 && canreach[i][j+1];
                canreach[i][j] = down || right;
            }
        }

        boolean[][] active = new boolean[n][m];
        int activeCount = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                active[i][j] = reach[i][j] && canreach[i][j];
                if (active[i][j]) activeCount++;
            }

        long totalCells = (long) n * m;
        long k = totalCells - activeCount;

        long ans = (pow2[(int)k] - 1 + MOD) % MOD;

        if (activeCount == 0) {
            return ans;
        }

        // build order: active cells sorted by (i+j) ascending; index by cellId = i*m+j
        int[] cellIdToOrder = new int[n*m]; // -1 if inactive
        Arrays.fill(cellIdToOrder, -1);
        int[] orderToCellId = new int[activeCount];

        // bucket by diagonal i+j (0..n+m-2)
        int maxDiag = n + m - 2;
        int[] diagCount = new int[maxDiag + 2];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (active[i][j]) diagCount[i+j]++;
        int[] diagStart = new int[maxDiag + 2];
        for (int d = 1; d <= maxDiag; d++) diagStart[d] = diagStart[d-1] + diagCount[d-1];
        int[] fillPos = diagStart.clone();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (active[i][j]) {
                    int d = i + j;
                    int pos = fillPos[d]++;
                    orderToCellId[pos] = i*m+j;
                    cellIdToOrder[i*m+j] = pos;
                }
            }
        }

        int N = activeCount;
        int startOrder = cellIdToOrder[0];
        int sinkOrder = cellIdToOrder[(n-1)*m + (m-1)];

        // idom computation (forward), process in increasing order (topo order = diagonal order)
        int[] idom = new int[N];
        Arrays.fill(idom, -1);
        idom[startOrder] = startOrder;

        for (int pos = 0; pos < N; pos++) {
            if (pos == startOrder) continue;
            int cellId = orderToCellId[pos];
            int i = cellId / m, j = cellId % m;
            int p1 = -1, p2 = -1;
            if (i > 0 && active[i-1][j]) p1 = cellIdToOrder[(i-1)*m+j];
            if (j > 0 && active[i][j-1]) {
                if (p1 == -1) p1 = cellIdToOrder[i*m+(j-1)];
                else p2 = cellIdToOrder[i*m+(j-1)];
            }
            int newIdom = p1;
            if (p2 != -1) {
                newIdom = intersect(newIdom, p2, idom);
            }
            idom[pos] = newIdom;
        }

        // ipdom computation (backward), process in decreasing order
        int[] ipdom = new int[N];
        Arrays.fill(ipdom, -1);
        ipdom[sinkOrder] = sinkOrder;

        for (int pos = N-1; pos >= 0; pos--) {
            if (pos == sinkOrder) continue;
            int cellId = orderToCellId[pos];
            int i = cellId / m, j = cellId % m;
            int s1 = -1, s2 = -1;
            if (i+1 < n && active[i+1][j]) s1 = cellIdToOrder[(i+1)*m+j];
            if (j+1 < m && active[i][j+1]) {
                if (s1 == -1) s1 = cellIdToOrder[i*m+(j+1)];
                else s2 = cellIdToOrder[i*m+(j+1)];
            }
            int newIpdom = s1;
            if (s2 != -1) {
                newIpdom = intersectRev(newIpdom, s2, ipdom, N);
            }
            ipdom[pos] = newIpdom;
        }

        // union find, merge pos with idom[pos] when ipdom[idom[pos]] == pos
        int[] parent = new int[N];
        int[] size = new int[N];
        for (int i = 0; i < N; i++) { parent[i]=i; size[i]=1; }

        for (int pos = 0; pos < N; pos++) {
            if (pos == startOrder) continue;
            int x = idom[pos];
            if (x != -1 && ipdom[x] == pos) {
                union(parent, size, x, pos);
            }
        }

        // gather component sizes: mark roots, sum contribution from each root's size
        boolean[] counted = new boolean[N];
        for (int pos = 0; pos < N; pos++) {
            int r = find(parent, pos);
            if (!counted[r]) {
                counted[r] = true;
                ans = (ans + pow2[size[r]] - 1 + MOD) % MOD;
            }
        }

        return ans;
    }

    // order-based intersect for forward idom (order numbers increase = pos itself, smaller pos = closer to start/root)
    static int intersect(int u, int v, int[] idom) {
        while (u != v) {
            while (u > v) u = idom[u];
            while (v > u) v = idom[v];
        }
        return u;
    }

    // for ipdom we use reverse order number = N-1-pos, so "closer to sink" (larger pos) means smaller rev order.
    // we want to walk toward sink (larger pos actually corresponds to smaller rev-order... let's just use raw pos with comparison flipped)
    static int intersectRev(int u, int v, int[] ipdom, int N) {
        while (u != v) {
            while (u < v) u = ipdom[u];
            while (v < u) v = ipdom[v];
        }
        return u;
    }

    static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    static void union(int[] parent, int[] size, int a, int b) {
        a = find(parent, a);
        b = find(parent, b);
        if (a == b) return;
        if (size[a] < size[b]) { int tmp=a; a=b; b=tmp; }
        parent[b] = a;
        size[a] += size[b];
    }
}