package org.sopt.classes;

import java.util.Scanner;

public class Customer {
    private String accountNumber; // 고객의 계좌 번호
    private int balance; // 계좌 잔액

    public Customer(String accountNumber, int initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) { //예금
        balance += amount;

    }

    public boolean withdraw(int amount) { //출금
        if (amount <= balance) {
            balance -= amount;
            return true; //출금 성공
        } else {
            System.out.println("잔액이 부족합니다.");
            return false; // 실패
        }
    }

}
