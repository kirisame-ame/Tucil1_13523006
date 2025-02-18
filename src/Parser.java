import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Parser {
    public static String test = "a";

    public static void readInput(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            int lineNum =0;
            int prevId = -1;
            int pieceCtr =0;
            ArrayList<Integer> prevSize = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line != null){
                lineNum++;
                // Parameters Checking
                switch (lineNum) {
                    case 1 -> {
                        String[] params = line.split(" ");
                        if(params.length !=3){
                            System.err.println("3 parameters are needed");
                        }
                        else if (!Utils.isNumeric(params[0])
                        || !Utils.isNumeric(params[1])
                        || !Utils.isNumeric(params[2])
                        ) {
                            System.err.println("Non numeric values detected");
                        }
                        else{
                            Board.setRows(Integer.parseInt(params[0]));
                            Board.setCols(Integer.parseInt(params[1]));
                            Board.setPieceNums(Integer.parseInt(params[2]));                            
                        }
                    }
                    case 2 -> {
                        if(line.equals("DEFAULT")||
                                line.equals("PYRAMID")||
                                line.equals("CUSTOM")
                                ){
                            Board.setShape(line);
                        }
                        else{
                            System.err.println("Invalid shape type");
                            
                        }
                    }
                    default -> {
                        // Pieces Checking
                        char pieceChar = line.charAt(0);
                        int pieceId = pieceChar -'A'+1;
                        if(pieceId>=1){
                            if (pieceId==prevId || Board.isUniquePiece(pieceId)){
                                for(int i=0;i<line.length();i++){
                                    Character curr = line.charAt(i);
                                    if(!Character.isAlphabetic(curr)){
                                        System.err.println("Non-Capital-Letter Character Encountered");
                                    }
                                    else if(!curr.equals(pieceChar)){
                                        System.err.println("Piece letter inconsistent");
                                    }
                                }
                                // same letter
                                if(prevId == -1 || pieceId==prevId){
                                    prevSize.add(line.length()); 
                                }
                                // different letter
                                else{
                                    boolean[][] prevShape = parsePiece(prevSize);
                                    Board.setPieces(new Piece(prevId, prevSize.size(),Collections.max(prevSize), prevShape));
                                    prevSize.clear();
                                    prevSize.add(line.length());
                                    pieceCtr++;
                                }
                                prevId = pieceId;
                            }
                            else{
                                System.err.println("Piece letter not unique");
                                
                            }
                        }
                        else{
                            System.err.println("Invalid piece character (A-Z) required");
                            
                        }
                    }
                }
                
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            // Parse last line
            boolean[][] prevShape = parsePiece(prevSize);
            Board.setPieces(new Piece(prevId, prevSize.size(),Collections.max(prevSize), prevShape));
            pieceCtr++;

            if(pieceCtr!=Board.getPieceNums()){
                System.err.println("Piece number {"+pieceCtr+ "} not matching parameter {"+Board.getPieceNums()+"}");
            }
            else{
                System.out.println("File Successfully parsed");
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }

    public static boolean[][] parsePiece(ArrayList<Integer> size){
        int max_len = Collections.max(size);
        int rows = size.size();
        boolean[][] shape = new boolean[rows][max_len];
        for (int i = 0; i < rows ; i++) {
            for(int j=0;j<size.get(i);j++){
                shape[i][j] = true;
            }
        }
        return shape;
    }
}
