// FileName ChiliToGoProfit.java
// Written by: Jonathon Meyer
// Written on: 08/31/2026

import java.util.Scanner;

public class ChiliToGoProfit {
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        double priceAdult =7.0, priceChild = 4.0;

        double costAdult = 4.35, costChild = 3.10;
        

        System.out.print("Please enter the number of children's meals >> ");
        int childMeals = input.nextInt();


        System.out.print("Please enter the number of adult meals >> ");
        int adultMeals = input.nextInt();

        double totalAdult = adultMeals * priceAdult, totalChild = childMeals * priceChild;
        double totalCost = totalAdult + totalChild;

        double profitAdult = totalAdult - (adultMeals * costAdult);
        double profitChild = totalChild - (childMeals * costChild);
        double totalProfit = profitAdult + profitChild;

        System.out.printf("The total amount of money collected for children's meals is $%.2f\n", totalChild);
        System.out.printf("The total amount of money collected for adult meals is $%.2f\n", totalAdult);
        System.out.printf("The total amount of money collected is $%.2f\n", totalCost);

        System.out.printf("The total profit from children's meals is $%.2f\n", profitChild);
        System.out.printf("The total profit from adult meals is $%.2f\n", profitAdult);
        System.out.printf("The total profit from all meals is $%.2f\n", totalProfit);

        input.close();
    }
}
