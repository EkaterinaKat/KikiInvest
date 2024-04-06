package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.switchcontroller.AbstractSwitchController;
import com.katyshevtseva.fx.switchcontroller.Section;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static org.katyshevtseva.invest.view.ViewConstants.InvestNodeInfo.*;

public class MainController extends AbstractSwitchController implements FxController {
    @FXML
    private Pane mainPane;
    @FXML
    private VBox buttonBox;

    @FXML
    private void initialize() {
        init(getSections(), mainPane, this::placeButton);
    }

    private List<Section> getSections() {
        return Arrays.asList(
                new Section("Assets", new AssetsController(),
                        controller -> WindowBuilder.getNode(ASSETS, controller)),
                new Section("Locations", new LocationsController(),
                        controller -> WindowBuilder.getNode(LOCATIONS, controller)),
                new Section("Accounts", new AccountsController(),
                        controller -> WindowBuilder.getNode(ACCOUNTS, controller)));
    }

    private void placeButton(Button button) {
        FxUtils.setWidth(button, 150);
        button.setWrapText(true);
        buttonBox.getChildren().addAll(FxUtils.getPaneWithHeight(40), button);
    }
}