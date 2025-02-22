
import java.util.ArrayList;
import java.util.Locale;

public class Board {
    private static int rows;
    private static int cols;
    private static int[][] board;
    private static int pieceNum;
    private static String shape;
    public static int iter;
    // Id naming convention, A-Z -> 1-26
    private static ArrayList<Piece> pieces = new ArrayList<>();

    public static int getRows(){
        return rows;
    }
    public static void setRows(int input){
        if(input<1){
            System.err.println("Rows cannot be smaller than 1");
        }
        else{
            rows = input;
        }
    }
    public static int getCols(){
        return cols;
    }
    public static void setCols(int input){
        if(input<1){
            System.err.println("Columns cannot be smaller than 1");
        }
        else{
            cols = input;
        }
    }
    public static String getShape(){
        return shape;
    }
    public static void setShape(String input){
        shape = input;
    }
    public static int[][] getBoard(){
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }
    public static void setBoard(int[][] b){
        if(b.length==rows && b[0].length==cols){
            board = b;
        }
        else{
            System.err.println("Err: Board shape not appropriate");
        }
    }
    public static void printBoard(){
        for(int[] row:board){
            for(int j=0;j<row.length;j++){
                Colour.colourPrint(Utils.idToChar(row[j]));
            }
            System.out.println("");
        }
        System.out.println("Combinations tried: "+  String.format(Locale.US,"%,d", iter));
    }
    public static int getPieceNums(){
        return pieceNum;
    }
    public static void setPieceNums(int input){
        
        if(input<1){
            System.err.println("Piece number cannot be smaller than 1");
        }
        else{
            pieceNum = input;
        }
    }
    public static Piece getPiece(int id){
        for(Piece p : pieces){
            if(p.id==id) return p;
        }
        return null;
    }
    public static void setPieces(Piece input){
        pieces.add(input);
    }
    public static boolean isUniquePiece(int id){
        if(id >= 1 && id <=26){
            return getPiece(id)==null;
        }
        else{
            System.err.println("Err: Piece Id should be from 1-26");
            return false;
        }
    }
    public static void printPiece(Piece p){
        System.out.println("id:"+p.id);
        char c = (char) (p.id+'A'-1);
        boolean[][] pieceShape = p.getShape();
        int pieceRows = p.getRows();
        int pieceCols = p.getCols();
        for(int i=0;i<pieceRows;i++){
            for(int j=0;j<pieceCols;j++){
                if(pieceShape[i][j]){
                    System.out.print(c);
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
        
    }
    public static void resetBoard(){
        rows = 0;
        cols = 0;
        board = null;
        pieceNum = 0;
        shape = "";
        pieces = null;
    }
    public static void printAllPieces(){
        if(pieces==null){
            System.err.println("Err: Board has not been set properly");
            return;
        }
        System.out.println("");
        System.out.println("Pieces Count: "+pieceNum);
        for (Piece p : pieces) {
            if(p != null){
                printPiece(p);
            }
        }
    }

    public static boolean solve(int idx){
        if(idx==pieceNum){
            for (int i = 0; i < rows; i++) {
                for(int j=0;j<cols;j++){
                    if(board[i][j]==0){
                        return false;
                    }
                }
            }
            return true;
        }
        Piece currPiece = pieces.get(idx);
        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++){
                // All transformations
                for(int i=0;i<2;i++){
                    for(int j=0;j<4;j++){
                        iter++;
                        boolean[][] currShape = currPiece.transform(j, i);
                        if(Piece.insertPiece(r, c, currPiece.id, currShape)){
                            if(solve(idx+1)){
                                return true;
                            }
                            else{
                                Piece.removePiece(r,c,currShape);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
