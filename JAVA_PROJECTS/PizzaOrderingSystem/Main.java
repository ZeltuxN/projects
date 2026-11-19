package LAB4;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("  Welcome to the Java Pizzeria!");
        System.out.println("=====================================");

        Pizzaria pizzeria = new Pizzaria();
        pizzeria.startOrder(scanner);

        System.out.println("\nThank you for your order! Enjoy your pizza!");
        scanner.close();
    }
}