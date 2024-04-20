package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.LabelBuilder;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.TableUtils;
import com.katyshevtseva.fx.dialogconstructor.*;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.entity.Replenishment;
import org.katyshevtseva.invest.core.entity.Withdrawal;
import org.katyshevtseva.invest.core.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.katyshevtseva.fx.Styler.StandardColor.ORANGE;
import static com.katyshevtseva.fx.Styler.StandardColor.SCREAMING_GREEN;
import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;

public class AccountsController implements SectionController {
    @FXML
    private Button addAccountButton;
    @FXML
    private GridPane accountsPane;
    @FXML
    private Label infoLabel;
    @FXML
    private TableView<Operation> operationTable;
    @FXML
    private TableColumn<Operation, String> dateColumn;
    @FXML
    private TableColumn<Operation, Float> amountColumn;
    @FXML
    private TableColumn<Operation, String> fromColumn;
    @FXML
    private TableColumn<Operation, String> toColumn;
    @FXML
    private TableColumn<Operation, String> commentColumn;
    @FXML
    private Button replenishButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button editButton;
    private Map<Long, Label> accountIdPointLabelMap;

    @FXML
    private void initialize() {
        addAccountButton.setOnAction(event -> openAccountEditDialog(null));
        fillAccountTable(null);
        adjustOperationTable();
    }

    @Override
    public void update() {
        fillAccountTable(null);
    }

    private void openReplenishmentEditDialog(Replenishment replenishment, Account account) {
        boolean newReplenishment = replenishment == null;

        DcTextArea commentField = new DcTextArea(false, newReplenishment ? "" : replenishment.getComment());
        DcDatePicker datePicker = new DcDatePicker(true, newReplenishment ? null : replenishment.getDate());
        DcFloatNumField amountField = new DcFloatNumField(true, newReplenishment ? null : replenishment.getAmount());

        DialogConstructor.constructDialog(() -> {
            ReplenishmentService.save(replenishment, datePicker.getValue(), amountField.getValue(),
                    commentField.getValue(), account);
            fillAccountTable(account);
        }, datePicker, amountField, commentField);
    }

    private void openWithdrawalEditDialog(Withdrawal withdrawal, Account account) {
        boolean newWithdrawal = withdrawal == null;

        DcTextArea commentField = new DcTextArea(false, newWithdrawal ? "" : withdrawal.getComment());
        DcDatePicker datePicker = new DcDatePicker(true, newWithdrawal ? null : withdrawal.getDate());
        DcFloatNumField amountField = new DcFloatNumField(true, newWithdrawal ? null : withdrawal.getAmount());

        DialogConstructor.constructDialog(() -> {
            WithdrawalService.save(withdrawal, datePicker.getValue(), amountField.getValue(),
                    commentField.getValue(), account);
            fillAccountTable(account);
        }, datePicker, amountField, commentField);
    }

    private void openAccountEditDialog(Account account) {
        boolean newAccount = account == null;
        DcTextField titleField = new DcTextField(true, newAccount ? "" : account.getTitle(), "title");
        DcComboBox<Location> locationDcComboBox = new DcComboBox<>(true, newAccount ? null : account.getLocation(),
                LocationService.getLocations());

        DialogConstructor.constructDialog(() -> {
            AccountService.save(account, titleField.getValue(), locationDcComboBox.getValue());
            fillAccountTable(account);
        }, titleField, locationDcComboBox);
    }

    private void fillAccountTable(Account accountToShow) {
        boolean accountToShowWasShowed = false;
        accountsPane.getChildren().clear();
        List<Account> accounts = AccountService.getAccounts();
        accountIdPointLabelMap = new HashMap<>();
        int rowIndex = 0;
        for (Account account : accounts) {
            Label label = new LabelBuilder().text(account.getTitle()).width(200).build();
            Label point = new Label();
            accountIdPointLabelMap.put(account.getId(), point);
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showAccount(account));
            accountsPane.add(point, 0, rowIndex);
            accountsPane.add(label, 1, rowIndex);
            rowIndex++;

            if (account.equals(accountToShow)) {
                showAccount(account);
                accountToShowWasShowed = true;
            }
        }
        if (!accountToShowWasShowed) {
            showAccount(null);
        }
    }

    private void showAccount(Account account) {
        infoLabel.setText(account != null ? account.getFullInfo() : "");
        showOperations(account);

        accountIdPointLabelMap.values().forEach(label -> label.setText(""));
        if (account != null) {
            accountIdPointLabelMap.get(account.getId()).setText("* ");
        }

        replenishButton.setVisible(account != null);
        withdrawButton.setVisible(account != null);
        editButton.setVisible(account != null);
        if (account != null) {
            replenishButton.setOnAction(event -> openReplenishmentEditDialog(null, account));
            withdrawButton.setOnAction(event -> openWithdrawalEditDialog(null, account));
            editButton.setOnAction(event -> openAccountEditDialog(account));
        }
    }

    private void showOperations(Account account) {
        ObservableList<Operation> entries = FXCollections.observableArrayList();
        entries.addAll(OperationService.getOperations(account));
        operationTable.getItems().clear();
        operationTable.setItems(entries);
    }

    private void adjustOperationTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("fromString"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("toString"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        TableUtils.adjustRows(operationTable, (operation, control) -> {
            control.setContextMenu(getContextMenu(operation));

            if (operation instanceof Withdrawal) {
                control.setStyle(Styler.getColorfullStyle(BACKGROUND, ORANGE));
            } else if (operation instanceof Replenishment) {
                control.setStyle(Styler.getColorfullStyle(BACKGROUND, SCREAMING_GREEN));
            }
        });
    }

    private ContextMenu getContextMenu(Operation operation) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> {
            if (operation instanceof Replenishment) {
                Replenishment replenishment = (Replenishment) operation;
                openReplenishmentEditDialog(replenishment, replenishment.getTo());
            }
            if (operation instanceof Withdrawal) {
                Withdrawal withdrawal = (Withdrawal) operation;
                openWithdrawalEditDialog(withdrawal, withdrawal.getFrom());
            }
        });
        contextMenu.getItems().add(editItem);

        return contextMenu;
    }
}
