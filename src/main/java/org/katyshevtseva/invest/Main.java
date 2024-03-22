package org.katyshevtseva.invest;

import com.katyshevtseva.fx.WindowBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import org.katyshevtseva.invest.view.MainController;

import static org.katyshevtseva.invest.view.ViewConstants.InvestDialogInfo.MAIN;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WindowBuilder.openDialog(MAIN, new MainController());
    }
}