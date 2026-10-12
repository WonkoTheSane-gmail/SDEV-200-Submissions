// FileName PreschoolDemo.java
// Written by: Jonathon Meyer
// Written on: 10/02/2026

public class PreschoolDemo {
    public static void main(String args[]) {

        PreschoolStudent ps1 = new PreschoolStudent(2345, 5);
        PreschoolStudent ps2 = new PreschoolStudent(67890, 0);

        display(ps1);
        display(ps2);

        ps1.setAge(6);
        display(ps1);

        ps1.setAge(7);
        display(ps1);
    }

    public static void display(PreschoolStudent ps) {
        System.out.print("Student ID " + ps.getIdNum());
        System.out.println(" Student age " + ps.getAge());
        System.out.println();
    }
}