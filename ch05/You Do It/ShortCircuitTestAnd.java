// FileName ShortCircuitTestAnd.java
// Written by: Jonathon Meyer
// Written on: 10/02/2026

public class ShortCircuitTestAnd
{
    public static void main(String[] args)
    {
        if (falseMethod() && trueMethod())
            System.out.println("Both are true");
        else
            System.out.println("Both are not true");
    }

    public static boolean trueMethod()
    {
        System.out.println("Inside trueMethod()");
        return true;
    }

    public static boolean falseMethod()
    {
        System.out.println("Inside falseMethod()");
        return false;
    }
}
