package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.dialogconstructor.*;
import com.katyshevtseva.general.NoArgsKnob;
import org.katyshevtseva.invest.core.AssetType;
import org.katyshevtseva.invest.core.entity.*;
import org.katyshevtseva.invest.core.service.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DialogUtil {

    public static void openReplenishmentEditDialog(Replenishment replenishment, Account account, NoArgsKnob onSave) {
        boolean newReplenishment = replenishment == null;

        DcTextArea commentField = new DcTextArea(false, newReplenishment ? "" : replenishment.getComment());
        DcDatePicker datePicker = new DcDatePicker(true, newReplenishment ? null : replenishment.getDate());
        DcFloatNumField amountField = new DcFloatNumField(true, newReplenishment ? null : replenishment.getAmount());

        DialogConstructor.constructDialog(() -> {
            ReplenishmentService.save(replenishment, datePicker.getValue(), amountField.getValue(),
                    commentField.getValue(), account);
            onSave.execute();
        }, datePicker, amountField, commentField);
    }

    public static void openWithdrawalEditDialog(Withdrawal withdrawal, Account account, NoArgsKnob onSave) {
        boolean newWithdrawal = withdrawal == null;

        DcTextArea commentField = new DcTextArea(false, newWithdrawal ? "" : withdrawal.getComment());
        DcDatePicker datePicker = new DcDatePicker(true, newWithdrawal ? null : withdrawal.getDate());
        DcFloatNumField amountField = new DcFloatNumField(true, newWithdrawal ? null : withdrawal.getAmount());

        DialogConstructor.constructDialog(() -> {
            WithdrawalService.save(withdrawal, datePicker.getValue(), amountField.getValue(),
                    commentField.getValue(), account);
            onSave.execute();
        }, datePicker, amountField, commentField);
    }

    public static void openAccountEditDialog(Account account, NoArgsKnob onSave) {
        boolean newAccount = account == null;
        DcTextField titleField = new DcTextField(true, newAccount ? "" : account.getTitle(), "title");
        DcComboBox<Location> locationDcComboBox = new DcComboBox<>(true, newAccount ? null : account.getLocation(),
                LocationService.getLocations());

        DialogConstructor.constructDialog(() -> {
            AccountService.save(account, titleField.getValue(), locationDcComboBox.getValue());
            onSave.execute();
        }, titleField, locationDcComboBox);
    }

    public static void openAssetCreationDialog(NoArgsKnob onSave) {
        DcTextField titleField = new DcTextField(true, "", "title");
        DcComboBox<Location> locationDcComboBox = new DcComboBox<>(true, null,
                LocationService.getLocations());
        DcFloatNumField purchasePriceField = new DcFloatNumField(true, null, "purchase price");
        DcDatePicker datePicker = new DcDatePicker(true, new Date());
        DcTextArea commentField = new DcTextArea(false, "", "comment");
        DcComboBox<AssetType> typeCb = new DcComboBox<>(true, null, Arrays.asList(AssetType.values()));
        DcTextArea purchaseCommentField = new DcTextArea(false, "", "purchase comment");

        List<Account> accounts = AccountService.getAccounts();
        Account firstAccount = accounts.isEmpty() ? null : accounts.get(0);
        DcComboBox<Account> accountCb = new DcComboBox<>(true, firstAccount, accounts);

        DialogConstructor.constructDialog(() -> {
                    AssetService.saveNew(
                            titleField.getValue(),
                            locationDcComboBox.getValue(),
                            purchasePriceField.getValue(),
                            datePicker.getValue(),
                            commentField.getValue(),
                            typeCb.getValue(),
                            accountCb.getValue(),
                            purchaseCommentField.getValue());
                    onSave.execute();
                }, 850, locationDcComboBox, typeCb, titleField, purchasePriceField, datePicker, commentField,
                accountCb, purchaseCommentField);
    }

    public static void openAssetEditDialog(Asset asset, NoArgsKnob onSave) {
        DcTextField titleField = new DcTextField(true, asset.getTitle(), "title");
        DcComboBox<Location> locationDcComboBox = new DcComboBox<>(true, asset.getLocation(),
                LocationService.getLocations());
        DcTextArea purchaseCommentField = new DcTextArea(false, asset.getComment(), "comment");

        DialogConstructor.constructDialog(() -> {
            AssetService.edit(
                    asset,
                    titleField.getValue(),
                    locationDcComboBox.getValue(),
                    purchaseCommentField.getValue());
            onSave.execute();
        }, locationDcComboBox, titleField, purchaseCommentField);
    }

    public static void openSellCreationDialog(Asset asset, NoArgsKnob onSave) {
        List<Account> accounts = AccountService.getAccounts();
        Account firstAccount = accounts.isEmpty() ? null : accounts.get(0);
        DcComboBox<Account> accountCb = new DcComboBox<>(true, firstAccount, accounts);

        DcFloatNumField sellPriceField = new DcFloatNumField(true, null, "sell price");
        DcDatePicker datePicker = new DcDatePicker(true, new Date());
        DcTextArea commentField = new DcTextArea(false, "", "comment");


        DialogConstructor.constructDialog(() -> {
            AssetService.sale(
                    asset,
                    accountCb.getValue(),
                    sellPriceField.getValue(),
                    datePicker.getValue(),
                    commentField.getValue());
            onSave.execute();
        }, accountCb, sellPriceField, datePicker, commentField);
    }

    public static void openPaymentCreationDialog(Asset asset, NoArgsKnob onSave) {
        List<Account> accounts = AccountService.getAccounts();
        Account firstAccount = accounts.isEmpty() ? null : accounts.get(0);
        DcComboBox<Account> accountCb = new DcComboBox<>(true, firstAccount, accounts);

        DcFloatNumField sellPriceField = new DcFloatNumField(true, null, "payment amount");
        DcDatePicker datePicker = new DcDatePicker(true, new Date());
        DcTextArea commentField = new DcTextArea(false, "", "comment");

        DialogConstructor.constructDialog(() -> {
            AssetService.payment(
                    asset,
                    accountCb.getValue(),
                    sellPriceField.getValue(),
                    datePicker.getValue(),
                    commentField.getValue());
            onSave.execute();
        }, accountCb, sellPriceField, datePicker, commentField);
    }

    public static void openPurchaseEditDialog(Purchase purchase, NoArgsKnob onSave) {
        DcFloatNumField purchasePriceField = new DcFloatNumField(true, purchase.getAmount(), "purchase price");
        DcDatePicker datePicker = new DcDatePicker(true, purchase.getDate());
        DcTextArea commentField = new DcTextArea(false, purchase.getComment(), "comment");

        List<Account> accounts = AccountService.getAccounts();
        DcComboBox<Account> accountCb = new DcComboBox<>(true, purchase.getFrom(), accounts);

        DialogConstructor.constructDialog(() -> {
            AssetService.editPurchase(
                    purchase,
                    purchasePriceField.getValue(),
                    datePicker.getValue(),
                    commentField.getValue(),
                    accountCb.getValue());
            onSave.execute();
        }, purchasePriceField, datePicker, commentField, accountCb);
    }
}
