package org.sopt;

import org.sopt.classes.BankService;
import org.sopt.classes.BankSystem;
import org.sopt.classes.Customer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        Scanner scanner = new Scanner(System.in);
        while(true) {
        System.out.print("계좌번호를 입력해주세요: "); // 계좌 번호로 고객 구분하기
        String accountNumber = scanner.nextLine();
        Customer customer = bankSystem.getCustomer(accountNumber); // 계좌 번호로 고객 조회

        if (customer == null) {
            System.out.println("해당 계좌를 찾을 수 없습니다.");
            return; // 고객이 없다면 프로그램 종료
        }

        BankService bankService = new BankService(bankSystem);
            System.out.print("어떤 업무를 보시고 싶으신가요? (예적금/적금/투자/펀드/카드/종료): ");
            String action = scanner.nextLine();
            if(action.equals("종료")){
                System.out.println("서비스를 종료합니다.");
                break;
            }
            bankService.processBankService(action, customer);
        }
    }
}