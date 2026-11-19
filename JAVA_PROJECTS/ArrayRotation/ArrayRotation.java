package LAB5;

import java.util.Random;
import java.util.Scanner;

public class ArrayRotation {

    public static void makePositive(int[][] arr, int n) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] < 0)
                    arr[i][j] = Math.abs(arr[i][j]);
    }

    public static int[][] rotate90(int[][] arr, int n) {
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                rotated[j][n - 1 - i] = arr[i][j];
        return rotated;
    }

    public static void printMatrix(int[][] arr, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Give N: ");
        int n = in.nextInt();

        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = rand.nextInt(21)-10;

        System.out.println("Original:");
        printMatrix(matrix, n);

        makePositive(matrix, n);
        System.out.println("After absolute:");
        printMatrix(matrix, n);

        int[][] rotated = rotate90(matrix, n);
        System.out.println("Rotated 90 clockwise:");
        printMatrix(rotated, n);
    }
}