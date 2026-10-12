// FileName CountingDownDemo.java
// Written by: Jonathon Meyer
// Written on: 10/12/2026

public class CountingDownDemo
{
   public static void main(String[] args)
   {
      countingDown(10);
      System.out.println("Blastoff!");
   }
   
   public static void countingDown(int count)
   {
      if(count > 0)
      {
         System.out.println(count);
         countingDown(count - 1);
      }
   }
}