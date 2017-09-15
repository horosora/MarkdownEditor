import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;


public class AppController {

    @FXML
    private WebView webView;

    @FXML
    public void fileOpen(Event e){
        FileChooser fc = new FileChooser();
        fc.setTitle("File selection");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                        new FileChooser.ExtensionFilter("All Files", "*.*"));

        File importFile = fc.showOpenDialog(null);

    }

    @FXML
    public void appExit(Event e) {
        System.exit(0);
    }

    @FXML
    public void about(Event e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        Scene scene = new Scene(root, 330, 220);
        Stage Stage = new Stage();
        Stage.setScene(scene);
        Stage.setTitle("USME");
        Stage.initModality(Modality.APPLICATION_MODAL);   //閉じるまで操作禁止
        Stage.setResizable(false);   //リサイズ禁止
        Stage.show();
    }
}