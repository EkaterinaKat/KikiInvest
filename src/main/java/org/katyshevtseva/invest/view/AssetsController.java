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

    private void updateContent() {
        ObservableList<Asset> entries = FXCollections.observableArrayList();
        entries.addAll(AssetService.getAssets());
        table.getItems().clear();
        table.setItems(entries);
        detailBox.getChildren().clear();
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
            control.setContextMenu(getContextMenu(asset));
            control.setOnMouseClicked(event -> showDetails(asset));
        });
    }

    private void showDetails(Asset asset) {
        detailBox.getChildren().clear();
        detailBox.getChildren().add(new LabelBuilder().width(400).text(asset.getFullInfo()).build());
    }

    private ContextMenu getContextMenu(Asset asset) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> {
            //todo
        });
        contextMenu.getItems().add(editItem);

        MenuItem closeItem = new MenuItem("Close");
        closeItem.setOnAction(event -> {
            //todo
        });
        contextMenu.getItems().add(closeItem);

        return contextMenu;
    }
}
