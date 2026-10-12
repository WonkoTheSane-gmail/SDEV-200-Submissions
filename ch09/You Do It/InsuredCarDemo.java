// FileName InsuredCarDemo.java
// Written by: Jonathon Meyer
// Written on: 10/09/2026

import javax.swing.*;
public class InsuredCarDemo
{
   public static void main(String[] args)
   {
      InsuredCar myCar = new InsuredCar();
      JOptionPane.showMessageDialog(null,
         myCar.toString());
   }
}