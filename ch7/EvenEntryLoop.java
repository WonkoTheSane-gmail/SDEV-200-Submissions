// FileName EvenEntryLoop.java
// Written by: Jonathon Meyer
// Written on: 09/16/2026


import java.util.Scanner;

public class EvenEntryLoop {
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        boolean continueLooping = true;

        System.out.println("\n\n\nWelcome to EvenEntryLoop.java by Jonathon Meyer!\n");
        System.out.println("You will be asked to enter an even number. If you enter an odd number, " +
                           "it will ask you to try again. You can exit the program by entering 999.\n\n");

        
        while(continueLooping) {
            continueLooping = Loop(continueLooping, input);
        }

        System.out.println("\n\nThank you for using EvenEntryLoop.java by Jonathon Meyer. Goodbye!\n\n\n");

        input.close();

    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean Loop(boolean continueLooping, Scanner input) {
        
        System.out.print("\nPlease enter an even number >> \n\n");
        
        int number = input.nextInt();

        if(number == 999) {
            System.out.println("\nYou have entered 999. Exiting the program.\n");
            continueLooping = false;
        }
        
        else if(isEven(number)) {
            System.out.println("\nCorrect! " + number + " is an even number!\n");
        }
        else {
            System.out.println("\nNo, " + number + " is an odd number. Please try again or enter 999 to exit.\n");
        }

        return continueLooping;
    }
}
