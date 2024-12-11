package com.example.project;

public class Student {
    private String name;
    private String guardianName;
    private String age;
    private String cnic;

    public Student(String name, String guardianName, String age, String cnic) {
        this.name = name;
        this.guardianName = guardianName;
        this.age = age;
        this.cnic = cnic;
    }

    public String getName() {
        return name;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getAge() {
        return age;
    }

    public String getCnic() {
        return cnic;
    }
}
