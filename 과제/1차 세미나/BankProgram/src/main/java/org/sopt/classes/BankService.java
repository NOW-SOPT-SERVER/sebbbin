package org.sopt.classes;

public class BankService {
    private BankSystem bankSystem;

    // 생성자에서 BankSystem 인스턴스를 받도록 변경
    public BankService(BankSystem bankSystem) {
        this.bankSystem = bankSystem;
    }

    public void processBankService(String action, Customer customer) {
        if (action.equals("예적금")) {
            Deposits deposits = new Deposits(customer, bankSystem);
            deposits.getUserInput();
        } else if (action.equals("적금")) {
            InstallmentSavings installmentSavings = new InstallmentSavings();

        } else if (action.equals("투자")) {
            Stock stock = new Stock();

        } else if (action.equals("펀드")) {
            Fund fund = new Fund();

        } else if (action.equals("카드")) {
            Card card = new Card();

        } else if (action.equals("종료")) {

        } else {
            System.out.println("다시 입력해주세요.");
            return;
        }

    }


}
