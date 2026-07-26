/*
 * Problem: 2236A - Games on the Train
 * Link: https://codeforces.com/problemset/problem/2236/A
 * Rating: 800
 * Tags: greedy, math
 *
 * Question:
 * Choose smallest k such that each tower height h[i] can be increased by
 * some x[i] in [1,k] to make all towers equal.
 * Answer = max(h) - min(h) + 1
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        
        for (int test = 0; test < t; test++) {
            int n = sc.nextInt();
            int maxH = Integer.MIN_VALUE;
            int minH = Integer.MAX_VALUE;
            
            for (int i = 0; i < n; i++) {
                int h = sc.nextInt();
                maxH = Math.max(maxH, h);
                minH = Math.min(minH, h);
            }
            
            int k = maxH - minH + 1;
            sb.append(k).append("\n");
        }
        
        System.out.print(sb);
    }
}