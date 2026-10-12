// FileName DinnerParty2.java
// Written by: Jonathon Meyer
// Written on: 10/07/2026

public class DinnerParty2 extends Party
{
   private int dinnerChoice;
   
   public int getDinnerChoice()
   {
      return dinnerChoice;
   }
   public void setDinnerChoice(int choice)
   {
      dinnerChoice = choice;
   }
   
   @Override
   public void displayInvitation()
   {
      System.out.println("Please come to my dinner party!");
   }
}