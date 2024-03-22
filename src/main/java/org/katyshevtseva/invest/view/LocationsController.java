package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.dialogconstructor.DcTextField;
import com.katyshevtseva.fx.dialogconstructor.DialogConstructor;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.service.LocationService;

public class LocationsController implements SectionController {
    @FXML
    private VBox locationsBox;
    @FXML
    private Button addLocationButton;

    @FXML
    private void initialize() {
        updateContent();
        addLocationButton.setOnAction(event -> openLocationEditDialog(null));
    }

    private void updateContent() {
        locationsBox.getChildren().clear();

        for (Location location : LocationService.getLocations()) {
            Label label = new Label(location.getTitle());
            label.setContextMenu(getLocationContextMenu(location));
            locationsBox.getChildren().add(label);
        }
    }

    private void openLocationEditDialog(Location location) {
        boolean newLocation = location == null;
        DcTextField titleField = new DcTextField(true, newLocation ? "" : location.getTitle());

        DialogConstructor.constructDialog(() -> {
            LocationService.save(location, titleField.getValue());
            updateContent();
        }, titleField);
    }

    private ContextMenu getLocationContextMenu(Location location) {
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> openLocationEditDialog(location));

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(editItem);

        return menu;
    }
}
