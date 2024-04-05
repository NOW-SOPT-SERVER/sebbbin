package org.sopt.classes;

import java.util.HashMap;
import java.util.Map;

public class BankSystem {
    //여러 고객 관리를 위한 은행 시스템
    private Map<String, Customer> customers;

    public BankSystem() {
        this.customers = new HashMap<>();
        customerDatabase();
    }
    //미리 고객 정보 쌓아두기
    private void customerDatabase(){
        addCustomer("123", new Customer("123", 10000));
        addCustomer("456", new Customer("456", 15000));
        addCustomer("789", new Customer("789", 5000));
    }

    // 새로운 고객 추가
    public void addCustomer(String accountNumber, Customer customer) {
        customers.put(accountNumber, customer);
    }

    // 고객 조회
    public Customer getCustomer(String accountNumber) {
        return customers.get(accountNumber);
    }

    public void accountTransfer(Customer fromCustomer, String toAccountNumber, int amount) {
        Customer toCustomer = customers.get(toAccountNumber); // 입금할 계좌의 고객 객체 조회

        if (toCustomer == null) {
            System.out.println("이체 대상 계좌를 찾을 수 없습니다.");
            return; // 이체 대상 계좌가 존재하지 않음
        }

        // 출금 성공 여부에 따라 입금 처리
        if (fromCustomer.withdraw(amount)) {
            toCustomer.deposit(amount); // 출금 성공 시 입금 처리
            System.out.println(amount + "원이 " + toAccountNumber + " 계좌로 이체되었습니다.");
        } else {
            System.out.println("계좌 이체 실패: 출금할 잔액이 부족합니다.");
        }
    }


}
