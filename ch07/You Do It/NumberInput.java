// FileName NumberInput.java
// Written by: Jonathon Meyer
// Written on: 10/04/2026

import javax.swing.*;

public class NumberInput
{
   public static void main(String[] args)
   {
      String inputString;
      int inputNumber;
      int result;
      
      final int FACTOR = 10;
      
      inputString = JOptionPane.showInputDialog(null,
         "Enter a number");
         
      inputNumber = Integer.parseInt(inputString);
      result = inputNumber * FACTOR;
      JOptionPane.showMessageDialog(null,
         inputNumber + " * " + FACTOR + " = " + result);
   }
}