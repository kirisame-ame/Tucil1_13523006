package kirisame.iq_puzzle;
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
    /**
     * transform a piece
     * @param rot : rotation number
     * @param mirror : mirror number
     * @return transformed boolean matrix
     */
    public boolean[][] transform(int rot,int mirror){
        if(mirror==0){
            return rotate(this.shape, rot);
        }
        return rotate(reverse(this.shape, mirror), rot);
    }
    /**
     * @param r : row index
     * @param c : col index
     * @param id : piece id
     * @param p : piece shape
     * @return
     */
    public static boolean insertPiece(int r,int c,int id,boolean[][] p){
        int[][] n_board = Board.getBoard();
        for(int i=0;i<p.length;i++){
            for(int j=0;j<p[0].length;j++){
                if(r+i>=n_board.length || c+j>=n_board[0].length){
                    return false;
                }
                else if(p[i][j]){
                    if(n_board[r+i][c+j]==0){
                        n_board[r+i][c+j] = id;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        Board.setBoard(n_board);
        return true;
    }
    public static void removePiece(int r,int c,boolean[][] p){
        int[][] n_board = Board.getBoard();
        for(int i=0;i<p.length;i++){
            for(int j=0;j<p[0].length;j++){
                if(r+i<n_board.length && c+j<n_board[0].length && p[i][j]){
                    n_board[r+i][c+j] = 0;
                }
            }
        }
        Board.setBoard(n_board);
    }
    /**
     * @param quad rotation index
     * <li> 1 : 90 degrees CCW
     * <li> 2 : 180 degrees
     * <li> 3 : 270 degrees CCW
     * <li> returns original shape otherwise
     */
    public boolean[][] rotate(boolean[][] m,int quad){
        switch (quad) {
            case 1->{
                boolean[][] t = transpose(m);
                return reverse(t, 0);
            }
            case 2->{
                return reverse(reverse(m, 0),1);
            }
            case 3->{
                boolean[][] t = transpose(m);
                return reverse(t, 1);
            }
            default ->{
                return m;
            }
        }
    }
    public boolean[][] transpose(boolean[][] m){
        boolean[][] t = new boolean[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for(int j=0;j<m[0].length;j++){
                t[j][i] = m[i][j];
            }
        }
        return t;
    }
    /**
     * 
     * @param axis
     * <li> 0: reverse by rows
     * <li> 1: reverse by columns
     */
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public boolean[][] reverse(boolean[][] m,int axis){
        if(axis==0){
            boolean[][] res = new boolean[m.length][m[0].length];
            for(int i=m.length-1;i>=0;i--){
                for(int j=0;j<m[0].length;j++){
                    res[m.length-1-i][j] = m[i][j];
                }
            }
            return res;
        }
        if(axis==1){
            boolean[][] res = new boolean[m.length][m[0].length];
            // Access entire rows
            for (int i=0;i<m.length;i++) {
                for (int j = m[0].length-1; j>=0; j--) {
                    res[i][m[0].length-1-j] = m[i][j];
                }
            }
            return res; 
        }
        return m;
    }
}
