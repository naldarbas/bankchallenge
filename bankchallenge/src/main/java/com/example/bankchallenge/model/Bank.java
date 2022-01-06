package com.example.bankchallenge.model;

public class Bank {
    private double bankBalance;

    public Bank(double bankBalance) {
        this.bankBalance = 4000.0;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }
}
