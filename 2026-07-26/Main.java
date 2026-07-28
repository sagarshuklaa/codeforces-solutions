/*
 * Problem: 2237A - Destroying Towers
 * Link: https://codeforces.com/problemset/problem/2237/A
 * Rating: 800
 * Tags: games, greedy, schedules
 *
 * Question:
 * n towers in a line; operating on tower i cuts the first taller tower to
 * its right down to its own height. Choose the order of operations
 * (each tower exactly once) to minimize the final total height.
 *
 * Insight: a tower can only ever be reduced by a value that originally
 * sat to its left, so the best any tower j can reach is min(a[0..j]).
 * That's always achievable by operating the smallest towers first.
 * Answer = sum of the running prefix minimum.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();

        for (int test = 0; test < t; test++) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();

            long sum = 0;
            int prefixMin = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                prefixMin = Math.min(prefixMin, a[i]);
                sum += prefixMin;
            }

            sb.append(sum).append("\n");
        }

        System.out.print(sb);
    }
}