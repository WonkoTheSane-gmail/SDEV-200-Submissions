// FileName PreschoolStudent.java
// Written by: Jonathon Meyer
// Written on: 10/02/2026

public class PreschoolStudent {
    // Step 1: Fields
    int idNum;
    int age;

    // Step 2: Constants
    public final int HIGH_ID = 9999;
    public final int HIGH_AGE = 6;

    // Step 6: Constructor (placed above setters as requested)
    public PreschoolStudent(int num, int studentAge) {
        setIdNum(num);
        setAge(studentAge);
    }

    // Step 3: setIdNum method with validation
    public void setIdNum(int num) {
        if (num <= HIGH_ID) {
            idNum = num;
        } else {
            idNum = 0;
        }
    }

    // Step 4: setAge method with validation
    public void setAge(int studentAge) {
        if (studentAge <= HIGH_AGE) {
            age = studentAge;
        } else {
            age = 0;
        }
    }

    // Step 5: Getter methods
    public int getIdNum() {
        return idNum;
    }

    public int getAge() {
        return age;
    }
}