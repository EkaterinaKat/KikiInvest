package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.*;

public class AccountCalculationService {

    public static void updateAccountAmount(Account account) {
        account.setAmount(calculateAccountSum(account));
        Dao.saveEdited(account);
    }

    public static Float calculateAccountSum(Account account) {
        Double amount = 0.0;

        for (Replenishment replenishment : Dao.findReplenishmentsByAccount(account)) {
            amount += replenishment.getAmount();
        }
        for (Payment payment : Dao.findPaymentsByAccount(account)) {
            amount += payment.getAmount();
        }
        for (Sale sale : Dao.findSalesByAccount(account)) {
            amount += sale.getAmount();
        }
        for (Withdrawal withdrawal : Dao.findWithdrawalsByAccount(account)) {
            amount -= withdrawal.getAmount();
        }
        for (Purchase purchase : Dao.findPurchasesByAccount(account)) {
            amount -= purchase.getAmount();
        }

        return amount.floatValue();
    }
}
