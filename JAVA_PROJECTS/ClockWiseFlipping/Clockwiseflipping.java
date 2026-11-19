package LAB5;

import java.util.Random;
import java.util.Scanner;

public class Clockwiseflipping {

    public static void makePositive(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            if (arr[i] < 0) {
                arr[i] = Math.abs(arr[i]);
            }
        }
    }

    public static void rotate90(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Give N: ");
        int n = in.nextInt();

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(21) - 10;
        }

        System.out.print("Original: ");
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        System.out.println();

        makePositive(arr, n);

        System.out.print("After absolute: ");
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        System.out.println();

        System.out.println("Rotated 90:");
        rotate90(arr, n);
    }
}
