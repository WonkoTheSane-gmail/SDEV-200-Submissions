// FileName Sailboat.java
// Written by: Jonathon Meyer
// Written on: 10/09/2026

import javax.swing.*;
public class Sailboat extends Vehicle
{
   private int length;
   
   public Sailboat()
   {
      super("wind", 0);
      setLength();
   }
   
   public void setLength()
   {
      String entry;
      entry = JOptionPane.showInputDialog
         (null, "Enter sailboat length in feet ");
      length = Integer.parseInt(entry);
   }
   public int getLength()
   {
      return length;
   }
   
   @Override
   public void setPrice()
   {
      String entry;
      final int MAX = 100000;
      entry = JOptionPane.showInputDialog
         (null, "Enter sailboat price ");
      price = Integer.parseInt(entry);
      if(price > MAX)
         price = MAX;
   }
   
   @Override
   public String toString()
   {
      return("The " + length + " foot sailboat is powered by " +
         getPowerSource() + "; it has " + getWheels() +
         " wheels and costs $" + getPrice());
   }
}