package kirisame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import kirisame.iq_puzzle.Board;
import kirisame.iq_puzzle.Color;
import kirisame.iq_puzzle.Parser;
import kirisame.iq_puzzle.Utils;

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

    private final int gridHeight= 250;
    
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
                textArea.setText("File read successfully!!\n"
                +"Board size: "+Board.getRows()+"x"+Board.getCols()+"\n"
                +"Piece number: "+Board.getPieceNums());
                resetGrid();
                setGrid();
            }
            else{
                textArea.setText(Parser.err);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Parse Error");
                alert.setHeaderText("File failed to parse correctly");
                alert.setContentText(Parser.err);
                alert.showAndWait();
            }
        }else{
            textArea.setText("File failed to open");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File failed to open");
            alert.setContentText("Recheck that file is not corrupted");
            alert.showAndWait();
        }
    }
    @FXML
    protected void solvePuzzle() {
        solveButton.setDisable(true);
        Board.zeroBoard();
        Board.iter = 0;
        textArea.setText("Solving...\n");
        final int updateRate = 10000;
        // AtomicInteger to be mutable inside timeline
        AtomicInteger lastIter = new AtomicInteger(0);
        // iter check
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (Board.iter - lastIter.get() >= updateRate) {
                lastIter.set(Board.iter);
                Platform.runLater(() -> setGrid());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    
        // Solver on diff thread
        new Thread(() -> {
            Instant start = Instant.now();
            boolean solved = Board.solve(0);
            Instant end = Instant.now();
    
            Platform.runLater(() -> {
                timeline.stop();
                setGrid();
                solveButton.setDisable(false);
                if (solved) {
                    System.out.println("Solution Found");
                    textArea.setText("Solution Found\n");
                } else {
                    System.out.println("No solution");
                    textArea.setText("No Solution Found\n");
                }
                long time = java.time.Duration.between(start, end).toMillis();
                Board.printBoard();
                imgButton.setDisable(false);
                txtButton.setDisable(false);
                textArea.appendText("Execution time: " + time + "ms\n");
                textArea.appendText("Permutations tried: " + String.format(Locale.US, "%,d", Board.iter));
            });
        }).start();
    }

    private void resetGrid() {
        puzzleGrid.getChildren().clear();
    }
    public void setGrid(){
        int rows = Board.getRows();
        int cols = Board.getCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char let = Utils.idToChar(Board.getBoard()[r][c]);
                String color =Color.colorMap.get(let);
                Label cell = new Label(Character.toString(let));
                cell.setMinSize(gridHeight/rows, gridHeight/rows);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                        "-fx-border-color: black; -fx-background-color: "+color+";");
                puzzleGrid.add(cell, c, r);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), cell);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            }
        }
        puzzleGrid.setVisible(true);
    }
    @FXML
    protected void saveImage(){
        WritableImage img = puzzleGrid.snapshot(null, null);
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("png files (*.png)","*.png");
        chooser.getExtensionFilters().add(extensionFilter);
        File file = chooser.showSaveDialog(null);
        if(file!=null){
            try{
                ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Saved");
                alert.setHeaderText("Image successfully saved");
                alert.setContentText("File saved at "+file.getAbsolutePath());
                alert.showAndWait();
            }catch(IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("File failed to save");
                alert.setContentText(e.toString());
                alert.showAndWait();
            }
        }
    }
    @FXML
    protected void saveText(){
        StringBuilder sb = Board.sb;
        sb.append(textArea.getText());
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extensionFilter);
        File file = chooser.showSaveDialog(null);
        if(file!=null){
            try {
                Files.writeString(file.toPath(), sb);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Saved");
                alert.setHeaderText("Text successfully saved");
                alert.setContentText("File saved at "+file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("File failed to save");
                alert.setContentText(e.toString());
                alert.showAndWait();
            }
        }
    }
    @FXML
    protected void openProfile(ActionEvent event)throws IOException{
        this.openUrl("https://github.com/kirisame-ame");
    }
}
