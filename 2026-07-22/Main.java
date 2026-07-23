/*
 * Problem: 2238B - Crimson Triples
 * Link: https://codeforces.com/problemset/problem/2238/B
 * Rating: 900
 * Tags: dp, math, number theory
 *
 * Question:
 * Count triples (a,b,c) in [1,n] such that gcd(lcm(a,b), lcm(b,c)) = gcd(a,c).
 * Simplifies to counting triples where b divides both a and c:
 * answer = sum_{b=1}^{n} floor(n/b)^2
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        
        for (int test = 0; test < t; test++) {
            long n = sc.nextLong();
            long ans = 0;
            
            for (long b = 1; b <= n; b++) {
                long cnt = n / b;
                ans += cnt * cnt;
            }
            
            sb.append(ans).append("\n");
        }
        
        System.out.print(sb);
    }
}