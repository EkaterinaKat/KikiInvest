package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.LabelBuilder;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.TableUtils;
import com.katyshevtseva.fx.dialogconstructor.DcComboBox;
import com.katyshevtseva.fx.dialogconstructor.DcFloatNumField;
import com.katyshevtseva.fx.dialogconstructor.DcTextField;
import com.katyshevtseva.fx.dialogconstructor.DialogConstructor;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.katyshevtseva.invest.core.Asset;
import org.katyshevtseva.invest.core.entity.Bond;
import org.katyshevtseva.invest.core.entity.Deposit;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.entity.Share;
import org.katyshevtseva.invest.core.service.*;

import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;

public class AssetsController implements SectionController {
    @FXML
    private TableView<Asset> table;
    @FXML
    private TableColumn<Asset, Location> locationColumn;
    @FXML
    private TableColumn<Asset, String> typeColumn;
    @FXML
    private TableColumn<Asset, Float> aiColumn;
    @FXML
    private TableColumn<Asset, String> moreColumn;
    @FXML
    private TableColumn<Asset, String> titleColumn;
    @FXML
    private Button addBondButton;
    @FXML
    private Button addShareButton;
    @FXML
    private Button addDepositButton;
    @FXML
    private VBox detailBox;

    @FXML
    private void initialize() {
        addBondButton.setOnAction(event -> openBondEditDialog(null));
        addShareButton.setOnAction(event -> openShareEditDialog(null));
        addDepositButton.setOnAction(event -> openDepositEditDialog(null));
        tuneColumns();
        setupRows();
        updateContent();
    }

    @Override
    public void update() {
        updateContent();
    }

    private void openBondEditDialog(Bond bond) {
        boolean newBond = bond == null;
        DcTextField titleField = new DcTextField(true, newBond ? "" : bond.getTitle(), "title");
        DcComboBox<Location> locationDcComboBox = new DcComboBox<>(true, newBond ? null : bond.getLocation(),
                LocationService.getLocations());
        DcFloatNumField purchasePriceField = new DcFloatNumField(true, newBond ? null : bond.getPurchasePrice(),
                "purchase price");
        DcFloatNumField aiPriceField = new DcFloatNumField(true, newBond ? null : bond.getAnnualInterest(),
                "annual interest");

        DialogConstructor.constructDialog(() -> {
            BondService.save(
                    bond,
                    titleField.getValue(),
                    locationDcComboBox.getValue(),
                    purchasePriceField.getValue(),
                    aiPriceField.getValue());
            updateContent();
        }, locationDcComboBox, titleField, purchasePriceField, aiPriceField);
    }

    private void openDepositEditDialog(Deposit deposit) {
        boolean newDeposit = deposit == null;
        DcTextField titleField = new DcTextField(true, newDeposit ? "" : deposit.getTitle(), "title");
        DcComboBox<Location> locationDcComboBox = new DcComboBox<>(true, newDeposit ? null : deposit.getLocation(),
                LocationService.getLocations());
        DcFloatNumField amountField = new DcFloatNumField(true, newDeposit ? null : deposit.getAmount(),
                "amount");
        DcFloatNumField aiPriceField = new DcFloatNumField(true, newDeposit ? null : deposit.getAnnualInterest(),
                "annual interest");

        DialogConstructor.constructDialog(() -> {
            DepositService.save(
                    deposit,
                    titleField.getValue(),
                    amountField.getValue(),
                    locationDcComboBox.getValue(),
                    aiPriceField.getValue());
            updateContent();
        }, locationDcComboBox, titleField, amountField, aiPriceField);
    }

    private void openShareEditDialog(Share share) {
        boolean newShare = share == null;
        DcTextField titleField = new DcTextField(true, newShare ? "" : share.getTitle(), "title");
        DcComboBox<Location> locationDcComboBox = new DcComboBox<>(true, newShare ? null : share.getLocation(),
                LocationService.getLocations());
        DcFloatNumField purchasePriceField = new DcFloatNumField(true, newShare ? null : share.getPurchasePrice(),
                "purchase price");

        DialogConstructor.constructDialog(() -> {
            ShareService.save(
                    share,
                    titleField.getValue(),
                    locationDcComboBox.getValue(),
                    purchasePriceField.getValue());
            updateContent();
        }, locationDcComboBox, titleField, purchasePriceField);
    }


    private void updateContent() {
        ObservableList<Asset> entries = FXCollections.observableArrayList();
        entries.addAll(AssetService.getAssets());
        table.getItems().clear();
        table.setItems(entries);
    }

    private void tuneColumns() {
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        aiColumn.setCellValueFactory(new PropertyValueFactory<>("ai"));
        moreColumn.setCellValueFactory(new PropertyValueFactory<>("otherInfo"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    private void setupRows() {
        TableUtils.adjustRows(table, (asset, control) -> {
            control.setStyle(Styler.getColorfullStyle(BACKGROUND, asset.getType().getColor()));
            if (asset instanceof Bond)
                control.setContextMenu(getContextMenu((Bond) asset));
            if (asset instanceof Share)
                control.setContextMenu(getContextMenu((Share) asset));
            if (asset instanceof Deposit)
                control.setContextMenu(getContextMenu((Deposit) asset));
            control.setOnMouseClicked(event -> showDetails(asset));
        });
    }

    private void showDetails(Asset asset) {
        detailBox.getChildren().clear();
        detailBox.getChildren().add(new LabelBuilder().width(400).text(asset.toString()).build());
    }

    private ContextMenu getContextMenu(Bond bond) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> openBondEditDialog(bond));
        contextMenu.getItems().add(editItem);
        return contextMenu;
    }

    private ContextMenu getContextMenu(Share share) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> openShareEditDialog(share));
        contextMenu.getItems().add(editItem);
        return contextMenu;
    }

    private ContextMenu getContextMenu(Deposit deposit) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> openDepositEditDialog(deposit));
        contextMenu.getItems().add(editItem);
        return contextMenu;
    }
}
