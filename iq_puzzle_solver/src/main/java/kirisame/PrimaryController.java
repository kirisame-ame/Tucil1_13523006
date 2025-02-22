package kirisame;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import kirisame.iq_puzzle.Board;
import kirisame.iq_puzzle.Parser;

public class PrimaryController extends App {

    @FXML
    private Hyperlink profile;
    @FXML
    private GridPane puzzleGrid;
    @FXML
    private TextArea textArea;
    @FXML
    private Button loadButton;
    @FXML
    private Button solveButton;
    @FXML
    private Button imgButton;
    @FXML
    private Button txtButton;

    protected String readFile(){
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showOpenDialog(null);
        if(file!=null){
            return file.getAbsolutePath();
        }
        return null;
    }
    @FXML
    protected void loadPuzzle(){
        Board.resetBoard();
        String path = readFile();
        if(path!=null){
            if(Parser.startInput(path)){
                textArea.setText("File read successfully!!");
                createGrid();
            }
            else{
                textArea.setText(Parser.err);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("File failed to open");
            alert.setContentText("Recheck that file is not corrupted");
            alert.showAndWait();
        }
    }
    protected void createGrid(){
        int rows = Board.getRows();
        int cols = Board.getCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Label cell = new Label("-");
                cell.setMinSize(100, 100);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                        "-fx-border-color: black;");
                puzzleGrid.add(cell, c, r);
            }
        }
        puzzleGrid.setVisible(true);
    }
    @FXML
    protected void openProfile(ActionEvent event)throws IOException{
        this.openUrl("https://github.com/kirisame-ame");
    }
}
