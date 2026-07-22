/*
 * Problem: 2240A - Another Popcount Problem
 * Link: https://codeforces.com/problemset/problem/2240/A
 * Rating: 800
 * Tags: greedy
 *
 * Question:
 * Construct k non-negative integers with sum <= n maximizing total popcount.
 * Output the maximum possible total popcount.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        
        for (int test = 0; test < t; test++) {
            long n = sc.nextLong();
            long k = sc.nextLong();
            
            long remaining = n;
            long ans = 0;
            long cost = 1; // cost = 2^j at bit level j
            
            while (cost <= remaining) {
                long canBuy = Math.min(k, remaining / cost);
                ans += canBuy;
                remaining -= canBuy * cost;
                cost *= 2;
            }
            
            sb.append(ans).append("\n");
        }
        
        System.out.print(sb);
    }
}