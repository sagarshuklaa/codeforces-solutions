/*
 * Problem: Threshold Movement
 * Rating: 800-900ish
 * Tags: constructive, math
 *
 * Question:
 * n elements at positions 1..n move left (w<k), right (w>k), or the
 * process fails (w==k). Find if some integer k makes every position
 * 1..n end up occupied exactly once.
 *
 * Insight: the only pattern that avoids collisions/escapes is strict
 * alternating R,L,R,L,... pairing (1,2),(3,4),...,(n-1,n), each pair
 * needing w[odd] > k and w[even] < k. So n must be even, and we need
 * max(even-position weights) < k < min(odd-position weights), i.e.
 * min(odd) - max(even) >= 2.
 */

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            long[] w = new long[n];
            for (int i = 0; i < n; i++) w[i] = Long.parseLong(st.nextToken());

            sb.append(solve(n, w)).append("\n");
        }

        System.out.print(sb);
    }

    static String solve(int n, long[] w) {
        if (n % 2 != 0) return "NO";

        long minOdd = Long.MAX_VALUE;
        long maxEven = Long.MIN_VALUE;
        for (int idx = 0; idx < n; idx++) {
            // idx is 0-indexed; 1-indexed position = idx+1
            if ((idx + 1) % 2 == 1) { // odd position
                minOdd = Math.min(minOdd, w[idx]);
            } else { // even position
                maxEven = Math.max(maxEven, w[idx]);
            }
        }

        return (minOdd - maxEven >= 2) ? "YES" : "NO";
    }
}