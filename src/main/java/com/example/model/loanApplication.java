package com.example.model;

public class loanApplication {
    private String name;
    private int age;
    private int income;
    private int creditScore;
    private String status;

    public loanApplication(String name, int age, int income, int creditScore) {
        this.name = name;
        this.age = age;
        this.income = income;
        this.creditScore = creditScore;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public int getIncome() { return income; }
    public int getCreditScore() { return creditScore; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
