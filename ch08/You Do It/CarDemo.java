// FileName CarDemo.java
// Written by: Jonathon Meyer
// Written on: 10/07/2026

public class CarDemo
{
   public static void main(String[] args)
   {
      Car firstCar = new Car(2021, Model.MINIVAN, Color.BLUE);
      Car secondCar = new Car(2024, Model.CONVERTIBLE, Color.RED);
      firstCar.display();
      secondCar.display();
   }
}