/*
 * Problem: 2218B - The 67th 6-7 Integer Problem
 * Link: https://codeforces.com/problemset/problem/2218/B
 * Rating: 800
 * Tags: greedy, math
 *
 * Question:
 * You are given 7 integers a1, a2, ..., a7.
 * You must negate 6 out of the 7 integers (multiply them by -1).
 * Find the maximum possible sum after negating exactly 6 of them.
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int t = sc.nextInt();  // number of test cases
        
        // process each test case one by one
        for (int test = 0; test < t; test++) {
            
            int[] a = new int[7];  // array to store the 7 numbers
            int sum = 0;           // to add up all 7 numbers
            int maxVal = -1000;    // to track the biggest number (start very small)
            
            // read the 7 numbers one by one
            for (int i = 0; i < 7; i++) {
                a[i] = sc.nextInt();
                sum = sum + a[i];
                
                // check if this number is the biggest so far
                if (a[i] > maxVal) {
                    maxVal = a[i];
                }
            }
            
            // apply the formula: answer = -sum + 2*maxVal
            int answer = (-1 * sum) + (2 * maxVal);
            
            System.out.println(answer);
        }
    }
}
