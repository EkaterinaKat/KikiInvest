package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.LabelBuilder;
import com.katyshevtseva.fx.dialogconstructor.DcComboBox;
import com.katyshevtseva.fx.dialogconstructor.DcTextField;
import com.katyshevtseva.fx.dialogconstructor.DialogConstructor;
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
import org.katyshevtseva.invest.core.service.AccountService;
import org.katyshevtseva.invest.core.service.LocationService;
import org.katyshevtseva.invest.core.service.Operation;
import org.katyshevtseva.invest.core.service.OperationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            label.setContextMenu(getContextMenu(account));
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
    }

    private ContextMenu getContextMenu(Account account) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> openAccountEditDialog(account));
        contextMenu.getItems().add(editItem);

        return contextMenu;
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
    }
}
