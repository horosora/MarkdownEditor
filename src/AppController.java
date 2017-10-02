//TODO: TextAreaとWebViewの表示位置を同期させる
//TODO: ファイル読み込みをドラッグ＆ドロップで出来るようにする
//TODO: 保存しないで終了しようとしたときに警告を出す


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
    private File tmpPath;

    //TextAreaの変化を検知してWevViewに表示
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

    //新しいファイルの作成
    @FXML
    public void newFile(Event e) {
        this.importFilePath = null;
        textArea.setText("");
    }

    //ファイルを開く
    @FXML
    public void openFile(Event e) {
        FileChooser fileSelect = new FileChooser();
        fileSelect.setTitle("File selection");
        fileSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                                new FileChooser.ExtensionFilter("All Files", "*.*"));
        this.importFilePath = fileSelect.showOpenDialog(null);

        if(this.importFilePath != null && tmpPath != importFilePath) {
            StringBuilder tmp = new StringBuilder();

            try{
                int readChar;
                FileReader filereader = new FileReader(this.importFilePath);
                while((readChar = filereader.read()) != -1){
                    tmp.append((char)readChar);
                }
                filereader.close();
            }catch(IOException er){
                System.exit(1);
            }

            textArea.setText(tmp.toString());
            this.tmpPath = this.importFilePath;
        }
    }

    //ファイルを保存
    @FXML
    public void saveFile(Event e) {
        if(this.importFilePath == null) {
            FileChooser saveSelect = new FileChooser();
            saveSelect.setTitle("Select storage location");
            saveSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            this.importFilePath = saveSelect.showSaveDialog(null);
        }
        writeToFile();
    }

    //ファイルを別名で保存
    @FXML
    public void saveAaFile(Event e){
        FileChooser saveSelect = new FileChooser();
        saveSelect.setTitle("Select storage location");
        saveSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                                new FileChooser.ExtensionFilter("All Files", "*.*"));
        this.importFilePath = saveSelect.showSaveDialog(null);
        writeToFile();
    }

    //アプリケーションを終了する
    @FXML
    public void appExit(Event e) {
        System.exit(0);
    }

    @FXML
    public void about(Event e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        Scene scene = new Scene(root, 300, 200);
        Stage Stage = new Stage();
        Stage.setScene(scene);
        Stage.setTitle("MarkdownEditor");
        Stage.initModality(Modality.APPLICATION_MODAL);
        Stage.setResizable(false);
        Stage.show();
    }

    public void writeToFile(){
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
}