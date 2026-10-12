// FileName DivideEvenly.java
// Written by: Jonathon Meyer
// Written on: 10/02/2026

public class DivideEvenly
{
   public static void main(String[] args)
   {
      final int LIMIT = 100;
      int var;
      
      System.out.print(LIMIT + " is evenly divisible by ");
      
      for(var = 1; var <= LIMIT; ++var)
         if(LIMIT % var == 0)
            System.out.print(var + " ");
            
      System.out.println();
   }
}