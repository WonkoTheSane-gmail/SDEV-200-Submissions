// FileName PartyWithConstructor2.java
// Written by: Jonathon Meyer
// Written on: 10/07/2026

public class PartyWithConstructor2
{
   private int guests;
   
   public PartyWithConstructor2(int numGuests)
   {
      guests = numGuests;
   }
   
   public int getGuests()
   {
      return guests;
   }
   public void setGuests(int numGuests)
   {
      guests = numGuests;
   }
   
   public void displayInvitation()
   {
      System.out.println("Please come to my party!");
   }
}