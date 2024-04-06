package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.TransactionType;
import org.katyshevtseva.invest.core.entity.Transaction;

public class TransactionImpactService {

    public static void makeAddChanges(Transaction transaction) {
        if(transaction.getType()== TransactionType.ACCOUNT_REPLENISHMENT){

        }
    }

    public static void makeDeleteChanges(Transaction transaction) {
        if(transaction.getType()== TransactionType.ACCOUNT_REPLENISHMENT){

        }
    }
}
