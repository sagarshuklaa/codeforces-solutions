/*
 * Problem: 2241A - Divide and Conquer
 * Link: https://codeforces.com/problemset/problem/2241/A
 * Rating: 800
 * Tags: greedy, math, number theory
 *
 * Question:
 * Given x and y, repeatedly choose z dividing x and set x := x/z.
 * Determine if x can become exactly y.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        
        StringBuilder sb = new StringBuilder();
        
        for (int test = 0; test < t; test++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            
            if (x % y == 0) {
                sb.append("YES").append("\n");
            } else {
                sb.append("NO").append("\n");
            }
        }
        
        System.out.print(sb);
    }
}