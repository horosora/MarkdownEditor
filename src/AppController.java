//TODO: TextAreaとWebViewの表示位置を同期させる
//TODO: ファイル読み込みをドラッグ＆ドロップで出来るようにする


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import org.markdown4j.Markdown4jProcessor;


public class AppController implements Initializable {

    @FXML private WebView webView;
    @FXML private TextArea textArea;
    private File importFilePath;
    private String originalFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                webView.getEngine().loadContent(new Markdown4jProcessor().process(newValue));
            }
            catch (StringIndexOutOfBoundsException | IOException e) {
                webView.getEngine().loadContent(newValue);
            }
        });
    }

    @FXML
    public void newFile(Event e) {
        if(originalFile == null){
            this.importFilePath = null;
        } else {
            System.out.println("hoge");
        }
    }

    @FXML
    public void openFile(Event e) {
        FileChooser fileSelect = new FileChooser();
        fileSelect.setTitle("File selection");
        fileSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                                new FileChooser.ExtensionFilter("All Files", "*.*"));
        this.importFilePath = fileSelect.showOpenDialog(null);

        if(this.importFilePath != null) {
            try{
                FileReader filereader = new FileReader(this.importFilePath);

                int ch;
                String str = "";
                while((ch = filereader.read()) != -1){
                    str += (char)ch;
                }
                filereader.close();
                originalFile = str;
                textArea.setText(str);
            }catch(IOException er){
                System.exit(1);
            }
        }
    }

    @FXML
    public void saveFile(Event e) {
        if(this.importFilePath == null) {
            FileChooser saveSelect = new FileChooser();
            saveSelect.setTitle("Select storage location");
            saveSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            this.importFilePath = saveSelect.showSaveDialog(null);
        }
        if(this.importFilePath != null){
            try{
                FileWriter filewriter = new FileWriter(this.importFilePath);
                filewriter.write(textArea.getText());
                filewriter.close();
            }catch(IOException er){
                System.exit(1);
            }
        }
    }

    @FXML
    public void saveAaFile(Event e){
        FileChooser saveSelect = new FileChooser();
        saveSelect.setTitle("Select storage location");
        saveSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                                new FileChooser.ExtensionFilter("All Files", "*.*"));
        this.importFilePath = saveSelect.showSaveDialog(null);
        if(this.importFilePath != null){
            try{
                FileWriter filewriter = new FileWriter(this.importFilePath);
                filewriter.write(textArea.getText());
                filewriter.close();
            }catch(IOException er){
                System.exit(1);
            }
        }
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
        Stage.setTitle("MarkdownEditor");
        Stage.initModality(Modality.APPLICATION_MODAL);
        Stage.setResizable(false);
        Stage.show();
    }
}