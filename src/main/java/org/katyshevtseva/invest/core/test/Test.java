package org.katyshevtseva.invest.core.test;

import org.katyshevtseva.invest.core.AssetType;
import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Asset;
import org.katyshevtseva.invest.core.entity.Payment;
import org.katyshevtseva.invest.core.service.AccountCalculationService;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        test4();
    }

    private static void test1() {
        System.out.println("платежи есть только у облигаций");

        for (Asset asset : Dao.getAllAssets()) {
            List<Payment> payments = Dao.findPaymentsByAsset(asset);
            System.out.println(asset + " " + payments);
            if (asset.getType() != AssetType.BOND && payments.size() > 0) {
                throw new RuntimeException();
            }
        }
        System.out.println("Success");
    }

    private static void test3() {
        System.out.println("type, title, location, purchase не должны быть нулевыми");

        for (Asset asset : Dao.getAllAssets()) {
            if (asset.getType() == null
                    || asset.getTitle() == null
                    || asset.getLocation() == null
                    || asset.getPurchase() == null) {
                throw new RuntimeException(asset.toString());
            }
        }
        System.out.println("Success");
    }

    private static void test4() {
        System.out.println("сумма счета равна сумме операций");

        for (Account account : Dao.getAllAccounts()) {
            Float sum = AccountCalculationService.calculateAccountSum(account);
            if (!sum.equals(account.getAmount())) {
                throw new RuntimeException();
            }
        }
        System.out.println("Success");
    }

//    private void test5() {
//        System.out.println("покупка раньше всех выплат и продажи");
//    }
//    private void test2() {
//        System.out.println("у облигации продажа позже всех выплат и покупки");
//    }
}
