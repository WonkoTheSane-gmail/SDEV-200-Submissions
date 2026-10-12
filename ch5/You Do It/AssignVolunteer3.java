// FileName AssignVolunteer3.java
// Written by: Jonathon Meyer
// Written on: 10/02/2026

import java.util.Scanner;

public class AssignVolunteer3
{
   public static void main(String[] args)
   {
        final int CLOTHING_CODE = 1;
        final String CLOTHING_PRICER = "Regina";
        final String OTHER_PRICER = "Marco";
        int donationType;
        String volunteer;
        String message;
        Scanner keyish = new Scanner(System.in);
        
        System.out.println("What type of donation is this?");
        System.out.print("Enter " + CLOTHING_CODE + " for clothing, 2 for anything else... ");
        donationType = keyish.nextInt();
        System.out.println("You entered " + donationType);
        
        if(donationType == CLOTHING_CODE || donationType == 2)
        {
            if(donationType == CLOTHING_CODE)
            {
                volunteer = CLOTHING_PRICER;
                message = "a clothing donation";
            }
            else
            {
                volunteer = OTHER_PRICER;
                message = "a non-clothing donation";
            }
        }
        else
        {
            volunteer = "invalid";
            message = "an invalid donation type";
        }

        
        System.out.println("This is " + message);
        System.out.println("The volunteer who will price this item is " + volunteer);
   }
}