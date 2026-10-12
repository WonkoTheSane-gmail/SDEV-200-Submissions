// FileName BookstoreCredit.java
// Written by: Jonathon Meyer
// Written on: 09/14/2026

import java.util.Scanner;

public class BookstoreCredit {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your name >> ");
        String name = input.nextLine();
        System.out.print("Please enter your current GPA >> ");
        double gpa = input.nextDouble();

        displayMessage(name, gpa);

        input.close();
    }

    public static void displayMessage(String name, double gpa) {
        double credit = gpa * 10;

        System.out.println("Hello " + name + "!");
        System.out.println("Your current GPA is " + gpa);
        System.out.println("You have earned $" + credit + " in bookstore credit.");
    }
}
