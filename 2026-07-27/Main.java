
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
            int[] a = new int[n];
            for (int idx = 0; idx < n; idx++) a[idx] = Integer.parseInt(st.nextToken());
 
            sb.append(solve(n, a)).append("\n");
        }
 
        System.out.print(sb);
    }
 
    static String solve(int n, int[] a) {
        int[] S1 = new int[n+1];
        int[] T = new int[n+1];
        for (int idx = 0; idx < n; idx++) {
            S1[idx+1] = S1[idx] + (a[idx] == 1 ? 1 : -1);
            T[idx+1]  = T[idx]  + (a[idx] == 1 || a[idx] == 2 ? 1 : -1);
        }
 
        int minTsofar = Integer.MAX_VALUE;
        for (int j = 2; j <= n-1; j++) {
            int iCandidate = j - 1;
            if (S1[iCandidate] >= 0) {
                if (T[iCandidate] < minTsofar) minTsofar = T[iCandidate];
            }
            if (minTsofar <= T[j]) {
                return "YES";
            }
        }
        return "NO";
    }
}