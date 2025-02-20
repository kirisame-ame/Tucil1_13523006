

public class Board {
    private static int rows;
    private static int cols;
    private static int[][] board;
    private static int pieceNum;
    private static String shape;
    // Id naming convention, A-Z -> 1-26
    private static Piece[] pieces = new Piece[26];

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
        return pieces[id-1];
    }
    public static void setPieces(Piece input){
        pieces[input.id-1]= input;
    }
    public static boolean isUniquePiece(int id){
        if(id >= 1 && id <=26){
            return pieces[id-1] ==null;
        }
        else{
            System.err.println("Piece Id should be from 1-26");
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
}
