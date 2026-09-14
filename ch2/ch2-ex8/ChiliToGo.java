import java.util.Scanner;

public class ChiliToGo {
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        double priceAdult =7.0, priceChild = 4.0;
        

        System.out.print("Please enter the number of children's meals >> ");
        int childMeals = input.nextInt();


        System.out.print("Please enter the number of adult meals >> ");
        int adultMeals = input.nextInt();

        double totalAdult = adultMeals * priceAdult, totalChild = childMeals * priceChild;
        double totalCost = totalAdult + totalChild;

        System.out.printf("The total amount of money collected for children's meals is $%.2f\n", totalChild);
        System.out.printf("The total amount of money collected for adult meals is $%.2f\n", totalAdult);
        System.out.printf("The total amount of money collected is $%.2f\n", totalCost);

        input.close();
    }
}
