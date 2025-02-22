package kirisame.iq_puzzle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Parser {
    public static String err;

    /**
     * Main wrapper for file input
     * @param filename
     */
    public static boolean startInput(String filename){
        if(!readInput(filename)){
            Board.resetBoard();
            return false;
        } 
        return true;
    }

    private static boolean readInput(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            int lineNum =0;
            int prevId = -1;
            int pieceCtr =0;
            ArrayList<ArrayList<Integer>> prevSize = new ArrayList<>();
            String line = br.readLine();

            while(line != null){
                lineNum++;
                switch (lineNum) {
                    // Parameters Checking
                    case 1 -> {
                        if(!readParams(line)) return false;
                    }
                    case 2 -> {
                        if(!readShape(line)) return false;
                    }
                    default -> {
                        // Pieces Checking
                        ArrayList<Integer> currShape = new ArrayList<>();
                        char pieceChar = Utils.getFirstLetter(line);
                        int pieceId = Utils.charToId(pieceChar);
                        if(pieceChar!='-'){
                            if (pieceId==prevId || Board.isUniquePiece(pieceId)){
                                for(int i=0;i<line.length();i++){
                                    Character curr = line.charAt(i);
                                    if(curr.equals(pieceChar)) currShape.add(i);
                                    else if(!curr.equals(pieceChar) && !curr.equals(' ')){
                                        err = "Err: Character {"+curr+"} not consistent with {"+pieceChar+"}";
                                        System.err.println(err);
                                        return false;
                                    }
                                }
                                // same letter
                                if(prevId == -1 || pieceId==prevId){
                                    prevSize.add(currShape); 
                                }
                                // different letter
                                else{
                                    boolean[][] prevShape = parsePiece(prevSize);
                                    if(Piece.isContinent(prevShape)){
                                        Board.setPieces(new Piece(prevId, prevShape.length,prevShape[0].length, prevShape));
                                        prevSize.clear();
                                        prevSize.add(currShape);
                                        pieceCtr++;
                                    }
                                    else{
                                        err = "Err: Piece {"+ Utils.idToChar(prevId)+"} not a continent i.e. single island";
                                        System.err.println(err);
                                        return false;
                                    }
                                }
                                prevId = pieceId;
                            }
                            else{
                                err= "Err: Piece letter {"+Utils.idToChar(pieceId)+ "} not unique";
                                System.err.println(err);
                                return false;
                            }
                        }
                        else{
                            err = "Err: Invalid piece character (A-Z) required";
                            System.err.println(err);
                            return false;
                        }
                    }
                }               
                line = br.readLine();
            }
            // Parse last line
            boolean[][] prevShape = parsePiece(prevSize);
            if(Piece.isContinent(prevShape)){
                Board.setPieces(new Piece(prevId, prevShape.length,prevShape[0].length, prevShape));
                pieceCtr++;
            }
            else{
                err = "Err: Piece {"+ Utils.idToChar(prevId)+"} not a continent i.e. single island";
                System.err.println(err);
                return false;
            }

            if(pieceCtr!=Board.getPieceNums()){
                err = "Err: Piece count {"+pieceCtr+ "} not matching parameter {"+Board.getPieceNums()+"}";
                System.err.println(err);
                return false;
            }
            else{
                System.out.println("File Successfully parsed");
                return true;
            }
        }catch(Exception e){
            err = e.toString();
            System.err.println(e);
            return false;
        }
    }

    private static boolean readParams(String line){
        String[] params = line.split(" ");
        if(params.length !=3){
            err = "Err: 3 parameters are needed";
            System.err.println(err);
        }
        else if (!Utils.isNumeric(params[0])
        || !Utils.isNumeric(params[1])
        || !Utils.isNumeric(params[2])
        ) {
            err ="Err: Non numeric values detected";
            System.err.println(err);
        }
        else{
            Board.setRows(Integer.parseInt(params[0]));
            Board.setCols(Integer.parseInt(params[1]));
            Board.setBoard(new int[Board.getRows()][Board.getCols()]);
            Board.setPieceNums(Integer.parseInt(params[2]));   
            return true;                         
        }
        return false;
    }
    private static boolean readShape(String line){
        if(line.equals("DEFAULT")||
        line.equals("PYRAMID")||
        line.equals("CUSTOM")
        ){
            Board.setShape(line);
            return true;
        }
        else{
            err = "Err: Invalid shape type";
            System.err.println(err);
            return false;
        }
    }
    public static boolean[][] parsePiece(ArrayList<ArrayList<Integer>> size){
        int max_len = 0;
        for(ArrayList<Integer> r : size){
            int len =Collections.max(r)+1;
            if(len>max_len) max_len=len;
        }
        int rows = size.size();
        boolean[][] shape = new boolean[rows][max_len];
        for (int i = 0; i < rows ; i++) {
            for(int j:size.get(i)){
                shape[i][j] = true;
            }
        }
        return shape;
    }
    
}
