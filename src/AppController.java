import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.File;


public class AppController {

    @FXML
    private WebView webView;

    @FXML
    public void fileOpen(Event e){
        FileChooser fc = new FileChooser();
        fc.setTitle("File selection");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("markdown file", "*.md"));

        File importFile = fc.showOpenDialog(null);

    }

    @FXML
    public void appExit(Event e) {
        System.exit(0);
    }
}