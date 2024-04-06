package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.switchcontroller.SectionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AccountsController implements SectionController {
    @FXML
    private Button addAccountButton;
    @FXML
    private VBox accountsBox;
    @FXML
    private Label balanceLabel;
    @FXML
    private GridPane operationsPane;

    @FXML
    private void initialize() {

    }

}
