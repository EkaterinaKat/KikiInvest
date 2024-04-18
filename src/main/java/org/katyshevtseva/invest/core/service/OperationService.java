package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Account;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OperationService {

    public static List<Operation> getOperations(Account account) {
        List<Operation> operations = new ArrayList<>();
        operations.addAll(Dao.findReplenishmentsByAccount(account));
        operations.addAll(Dao.findPaymentsByAccount(account));
        operations.addAll(Dao.findSalesByAccount(account));
        operations.addAll(Dao.findWithdrawalsByAccount(account));
        operations.addAll(Dao.findPurchasesByAccount(account));
        return operations.stream().sorted(Comparator.comparing(Operation::getDate)).collect(Collectors.toList());
    }
}
