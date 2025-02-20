
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
    private static boolean dfs_IsValid(boolean[][] shape, int curr_row, int curr_col, boolean[][] visited){
        int row = shape.length;
        int col = shape[0].length;
        // Point inside matrix and hasn't been visited
        return (curr_row>=0)&&(curr_row<row)&&(curr_col>=0)&&(curr_col<col)
        &&(shape[curr_row][curr_col] && !visited[curr_row][curr_col]);
    }
    private static void dfs(boolean[][] shape, int curr_row, int curr_col, boolean[][] visited){
        // Relative position of 8 neighbours, top to bottom
        int[] rel_row = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] rel_col = { -1, 0, 1, -1, 1, -1, 0, 1 };

        visited[curr_row][curr_col] = true;

        for (int i = 0; i < 8; i++) {
            int newR = curr_row + rel_row[i];
            int newC = curr_col + rel_col[i];
            if (dfs_IsValid(shape, newR, newC, visited)) {
                dfs(shape, newR, newC, visited);
            }
        }
    }
    /**
     * Uses DFS to check if piece is a continent, i.e. single island :)
     */
    public static boolean isContinent(boolean[][] shape){
        int row = shape.length;
        int col = shape[0].length;
        boolean[][] visited = new boolean[row][col];
        int count = 0;

        for (int r = 0; r < row; ++r) {
            for (int c = 0; c < col; ++c) {
                if (shape[r][c] && !visited[r][c]) {
                    if(count==1){
                        return false;
                    }
                    dfs(shape, r, c, visited);
                    ++count;
                }
            }
        }
        return true;
    }
    
}
