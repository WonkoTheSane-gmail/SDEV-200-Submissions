// FileName TestSandwich.java
// Written by: Jonathon Meyer
// Written on: 09/15/2026

import java.util.Scanner;

public class TestSandwich
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the main ingredient: ");
        String mainIngredient = input.nextLine();

        System.out.print("Enter the bread type: ");
        String breadType = input.nextLine();

        System.out.print("Enter the price: ");
        double price = input.nextDouble();

        Sandwich sandwich = new Sandwich();

        sandwich.setMainIngredient(mainIngredient);
        sandwich.setBreadType(breadType);
        sandwich.setPrice(price);

        System.out.println();
        System.out.println("Main ingredient: " + sandwich.getMainIngredient());
        System.out.println("Bread type: " + sandwich.getBreadType());
        System.out.println("Price: $" + sandwich.getPrice());

        input.close();
    }
}