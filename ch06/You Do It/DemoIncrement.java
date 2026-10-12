// FileName DemoIncrement.java
// Written by: Jonathon Meyer
// Written on: 10/02/2026

public class DemoIncrement
{
   public static void main(String[] args)
   {
      int v = 4;
      int plusPlusV = ++v;
      
      v = 4;
      int vPlusPlus = v++;
      
      System.out.println("v is " + v);
      System.out.println("++v is " + plusPlusV);
      System.out.println("v++ is " + vPlusPlus);
   }
}