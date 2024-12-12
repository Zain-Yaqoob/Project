package com.example.project;

public class Employee {
    private String employeeId;
    private String name;
    private String cnic;
    private String age;

    // Constructor to initialize the Employee object
    public Employee(String employeeId, String name, String age, String cnic) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.cnic = cnic;
    }

    // Getters for the fields
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }

    public String getAge() {
        return age;
    }
}
