
public class Piece {
    public int id;
    private int rows;
    private int cols;
    private boolean[][] shape;

    /**
     * Constructs an instance of Piece
     */
    public Piece(int id,int rows,int cols,boolean[][]shape){
        if(id<1 || id>26){
            System.err.println("Invalid Id");
        }
        else if(rows<1){
            System.err.println("Invalid rows");
        }
        else if(cols<1){
            System.err.println("Invalid cols");
        }
        else{
            this.id = id;
            this.rows = rows;
            this.cols = cols;
            this.shape = shape;
        }
    }
    public int getRows(){
        return this.rows; 
    }
    public int getCols(){
        return this.cols;
    }
    public boolean[][] getShape(){
        return this.shape;
    }
}
