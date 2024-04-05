package org.sopt.classes;

import java.util.Scanner;

public class Deposits {
    private Customer customer;
    private BankSystem bankSystem;

    public Deposits(Customer customer, BankSystem bankSystem) {
        this.customer = customer;
        this.bankSystem = bankSystem;
    }

    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("어떤 서비스를 원하시나요? (현재잔액확인/입금/출금/계좌이체): ");
        String serviceAction = scanner.nextLine();

        switch (serviceAction) {
            case "현재잔액확인":
                System.out.println("현재 잔액: " + customer.getBalance() + "원");
                break;
            case "입금":
                System.out.println("입금액을 입력해주세요: ");
                int amount = scanner.nextInt();
                customer.deposit(amount);
                System.out.println("입금 후 잔액: " + customer.getBalance() + "원");
                break;
            case "출금":
                System.out.println("출금액을 입력해주세요: ");
                amount = scanner.nextInt();
                customer.withdraw(amount);
                System.out.println("출금 후 잔액: " + customer.getBalance() + "원");
                break;
            case "계좌이체":
                System.out.println("계좌이체할 계좌번호를 입력해주세요: "); //DB에 쌓인 곳으로 입력
                String account = scanner.nextLine();
                System.out.println("계좌이체 금액 입력해주세요: ");
                amount = scanner.nextInt();
                bankSystem.accountTransfer(customer, account, amount);
                System.out.println("계좌이체 후 잔액: " + customer.getBalance() + "원");
                break;
            default:
                System.out.println("다시 입력해주세요.");
        }
    }
}
