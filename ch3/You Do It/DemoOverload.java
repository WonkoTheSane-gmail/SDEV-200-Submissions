// FileName DemoOverload.java
// Written by: Jonathon Meyer
// Written on: 09/14/2026

public class DemoOverload {
    public static void main(String[] args) {
        int month = 6, day = 24, year = 2023;

        displayDate(month);
        displayDate(month, day);
        displayDate(month, day, year);
    }
    
    public static void displayDate(int mm) {
        System.out.println("Event date: " + mm + "/1/2024");
    }

    public static void displayDate(int mm, int dd) {
        System.out.println("Event date: " + mm + "/" + dd + "/2024");
    }

    public static void displayDate(int mm, int dd, int yyyy) {
        System.out.println("Event date: " + mm + "/" + dd + "/" + yyyy);
    }
}
