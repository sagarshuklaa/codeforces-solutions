/*
 * Problem: 2238A - Another Puzzle from Papyrus
 * Link: https://codeforces.com/problemset/problem/2238/A
 * Rating: 800
 * Tags: greedy, math, sortings
 *
 * Question:
 * Convert array a to array b using decrement (cost 1) and reorder (cost c) operations.
 * Find minimum total cost, or -1 if impossible.
 * Compare: (1) no reorder - direct pairing, (2) reorder - sort both and pair.
 */

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        
        for (int test = 0; test < t; test++) {
            int n = sc.nextInt();
            int c = sc.nextInt();
            
            int[] a = new int[n];
            int[] b = new int[n];
            
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            for (int i = 0; i < n; i++) b[i] = sc.nextInt();
            
            // Strategy 1: no reorder
            boolean valid1 = true;
            long cost1 = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] < b[i]) {
                    valid1 = false;
                    break;
                }
                cost1 += (a[i] - b[i]);
            }
            
            // Strategy 2: reorder (sort both)
            int[] sortedA = a.clone();
            int[] sortedB = b.clone();
            Arrays.sort(sortedA);
            Arrays.sort(sortedB);
            
            boolean valid2 = true;
            long cost2 = 0;
            for (int i = 0; i < n; i++) {
                if (sortedA[i] < sortedB[i]) {
                    valid2 = false;
                    break;
                }
                cost2 += (sortedA[i] - sortedB[i]);
            }
            if (valid2) cost2 += c;
            
            long ans;
            if (!valid1 && !valid2) {
                ans = -1;
            } else if (valid1 && valid2) {
                ans = Math.min(cost1, cost2);
            } else if (valid1) {
                ans = cost1;
            } else {
                ans = cost2;
            }
            
            sb.append(ans).append("\n");
        }
        
        System.out.print(sb);
    }
}