package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.LabelBuilder;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.TableUtils;
import com.katyshevtseva.fx.dialogconstructor.*;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.sun.istack.internal.Nullable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.katyshevtseva.invest.core.AssetType;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Asset;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.service.AccountService;
import org.katyshevtseva.invest.core.service.AssetService;
import org.katyshevtseva.invest.core.service.LocationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;

public class AssetsController implements SectionController {
    @FXML
    private TableView<Asset> table;
    @FXML
    private TableColumn<Asset, Location> locationColumn;
    @FXML
    private TableColumn<Asset, String> typeColumn;
    @FXML
    private TableColumn<Asset, String> purchasePriceColumn;
    @FXML
    private TableColumn<Asset, String> purchaseDateColumn;
    @FXML
    private TableColumn<Asset, String> soldColumn;
    @FXML
    private TableColumn<Asset, String> commentColumn;
    @FXML
    private TableColumn<Asset, String> titleColumn;
    @FXML
    private Button addAssetButton;
    @FXML
    private VBox detailBox;
    @FXML
    private Button sellButton;
    @FXML
    private Button addPaymentButton;
    @FXML
    private Button editButton;

    @FXML
    private void initialize() {
        addAssetButton.setOnAction(event -> openAssetCreationDialog());
        tuneColumns();
        setupRows();
        updateContent();
    }

    @Override
    public void update() {
        updateContent();
    }

    private void openAssetCreationDialog() {
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
                    updateContent();
                }, 850, locationDcComboBox, typeCb, titleField, purchasePriceField, datePicker, commentField,
                accountCb, purchaseCommentField);
    }

    private void openAssetEditDialog(Asset asset) {
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
            updateContent();
            showDetailsUpdated(asset);
        }, locationDcComboBox, titleField, purchaseCommentField);
    }

    private void updateContent() {
        ObservableList<Asset> entries = FXCollections.observableArrayList();
        entries.addAll(AssetService.getAssets());
        table.getItems().clear();
        table.setItems(entries);
        showDetails(null);
    }

    private void tuneColumns() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        soldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
    }

    private void setupRows() {
        TableUtils.adjustRows(table, (asset, control) -> {
            control.setStyle(Styler.getColorfullStyle(BACKGROUND, asset.getType().getColor()));
            control.setOnMouseClicked(event -> showDetails(asset));
        });
    }

    private void showDetailsUpdated(Asset asset) {
        showDetails(AssetService.getUpdated(asset));
    }

    private void showDetails(@Nullable Asset asset) {
        detailBox.getChildren().clear();

        addPaymentButton.setVisible(asset != null && asset.getType() == AssetType.BOND);
        sellButton.setVisible(asset != null);
        editButton.setVisible(asset != null);

        if (asset != null) {
            detailBox.getChildren().add(new LabelBuilder().width(400).text(asset.getFullInfo()).build());

            boolean sold = AssetService.isSold(asset);
            sellButton.setDisable(sold);
            addPaymentButton.setDisable(sold);

            addPaymentButton.setOnMouseClicked(event -> openPaymentDialog(asset));
            sellButton.setOnMouseClicked(event -> openSellDialog(asset));
            editButton.setOnMouseClicked(event -> openAssetEditDialog(asset));
        }
    }

    private void openSellDialog(Asset asset) {
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
            updateContent();
            showDetailsUpdated(asset);
        }, accountCb, sellPriceField, datePicker, commentField);
    }

    private void openPaymentDialog(Asset asset) {
        List<Account> accounts = AccountService.getAccounts();
        Account firstAccount = accounts.isEmpty() ? null : accounts.get(0);
        DcComboBox<Account> accountCb = new DcComboBox<>(true, firstAccount, accounts);

        DcFloatNumField sellPriceField = new DcFloatNumField(true, null, "sell price");
        DcDatePicker datePicker = new DcDatePicker(true, new Date());
        DcTextArea commentField = new DcTextArea(false, "", "comment");

        DialogConstructor.constructDialog(() -> {
            AssetService.payment(
                    asset,
                    accountCb.getValue(),
                    sellPriceField.getValue(),
                    datePicker.getValue(),
                    commentField.getValue());
            updateContent();
            showDetailsUpdated(asset);
        }, accountCb, sellPriceField, datePicker, commentField);
    }
}
