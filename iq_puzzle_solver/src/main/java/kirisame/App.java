package kirisame;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public void openUrl(String url){
        getHostServices().showDocument(url);
    }
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 900, 700);
        stage.setScene(scene);
        stage.setTitle("IQ Puzzle Solver by Kirisame-ame");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("logo.png")));
        stage.show();
        unblurTextArea();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    private void unblurTextArea(){
        TextArea textArea = (TextArea)scene.lookup("#textArea");
        textArea.setCache(false);
        ScrollPane sp = (ScrollPane)textArea.getChildrenUnmodifiable().get(0);
        sp.setCache(false);
        for (Node node : sp.getChildrenUnmodifiable()) {
            node.setCache(false);
        } 
    }
    public static void main(String[] args) {
        launch();
    }

}