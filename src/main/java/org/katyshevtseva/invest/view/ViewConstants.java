package org.katyshevtseva.invest.view;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import lombok.Getter;
import org.katyshevtseva.invest.core.CoreConstants;

public class ViewConstants {

    @Getter
    public enum InvestDialogInfo implements WindowBuilder.DialogInfo {
        MAIN("/fxml/main.fxml", new Size(1000, 1700), CoreConstants.APP_NAME);

        private final String fullFileName;
        private final Size size;
        private final String title;

        InvestDialogInfo(String fullFileName, Size size, String title) {
            this.fullFileName = fullFileName;
            this.size = size;
            this.title = title;
        }
    }

    @Getter
    public enum InvestNodeInfo implements WindowBuilder.NodeInfo {
        ASSETS("/fxml/assets.fxml"),
        LOCATIONS("/fxml/locations.fxml"),
        ACCOUNTS("/fxml/accounts.fxml");

        private final String fullFileName;

        InvestNodeInfo(String fullFileName) {
            this.fullFileName = fullFileName;
        }
    }
}
