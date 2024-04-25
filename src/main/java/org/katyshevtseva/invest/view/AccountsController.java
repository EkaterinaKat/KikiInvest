package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.LabelBuilder;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.TableUtils;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.katyshevtseva.invest.core.Operation;
import org.katyshevtseva.invest.core.OperationType;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Purchase;
import org.katyshevtseva.invest.core.entity.Replenishment;
import org.katyshevtseva.invest.core.entity.Withdrawal;
import org.katyshevtseva.invest.core.service.AccountService;
import org.katyshevtseva.invest.core.service.AssetService;
import org.katyshevtseva.invest.core.service.OperationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.katyshevtseva.fx.Styler.StandardColor.ORANGE;
import static com.katyshevtseva.fx.Styler.StandardColor.SCREAMING_GREEN;
import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;
import static org.katyshevtseva.invest.view.DialogUtil.*;

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
    private TableColumn<Operation, OperationType> typeColumn;
    @FXML
    private Button replenishButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button editButton;
    private Map<Long, Label> accountIdPointLabelMap;

    @FXML
    private void initialize() {
        addAccountButton.setOnAction(event -> openAccountEditDialog(null, () -> fillAccountTable(null)));
        fillAccountTable(null);
        adjustOperationTable();
    }

    @Override
    public void update() {
        fillAccountTable(null);
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

            replenishButton.setOnAction(event -> openReplenishmentEditDialog(null, account,
                    () -> fillAccountTable(account)));

            withdrawButton.setOnAction(event -> openWithdrawalEditDialog(null, account,
                    () -> fillAccountTable(account)));

            editButton.setOnAction(event -> openAccountEditDialog(account,
                    () -> fillAccountTable(account)));
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
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableUtils.adjustRows(operationTable, (operation, control) -> {
            control.setContextMenu(getContextMenu(operation));

            switch (operation.getType()) {
                case WITHDRAWAL:
                case PURCHASE:
                    control.setStyle(Styler.getColorfullStyle(BACKGROUND, ORANGE));
                    break;
                case REPLENISHMENT:
                case SALE:
                case PAYMENT:
                    control.setStyle(Styler.getColorfullStyle(BACKGROUND, SCREAMING_GREEN));
            }
        });
    }

    private ContextMenu getContextMenu(Operation operation) {
        ContextMenu contextMenu = new ContextMenu();
        OperationType type = operation.getType();

        if (type != OperationType.SALE && type != OperationType.PAYMENT) {
            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(event -> {
                if (type == OperationType.REPLENISHMENT) {
                    Replenishment replenishment = (Replenishment) operation;
                    Account account = replenishment.getTo();
                    openReplenishmentEditDialog(replenishment, account, () -> fillAccountTable(account));
                }
                if (type == OperationType.WITHDRAWAL) {
                    Withdrawal withdrawal = (Withdrawal) operation;
                    Account account = withdrawal.getFrom();
                    openWithdrawalEditDialog(withdrawal, account, () -> fillAccountTable(account));
                }
                if (type == OperationType.PURCHASE) {
                    Purchase purchase = (Purchase) operation;
                    openPurchaseEditDialog(purchase, () -> fillAccountTable(purchase.getFrom()));
                }
            });
            contextMenu.getItems().add(editItem);
        }

        if (type != OperationType.PURCHASE) {
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                new StandardDialogBuilder().openQuestionDialog("Delete?", answer -> {
                    if (answer) {
                        AssetService.deleteOperation(operation);
                        fillAccountTable(null);
                    }
                });
            });
            contextMenu.getItems().add(deleteItem);
        }

        return contextMenu;
    }
}
