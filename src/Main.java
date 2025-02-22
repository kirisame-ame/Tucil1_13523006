
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        String def_path ="C://Users/Warge/Documents/Kuliah/sem_4/stima/Tucil1_13523006/test/";
        if(Parser.startInput(def_path.concat("test.txt"))){
            Instant start = Instant.now();
            if(Board.solve(0)){
                Instant end = Instant.now();
                Board.printBoard();
                long time = Duration.between(start, end).toMillis();
                System.out.println("Execution time: "+time+"ms");
            }
            else{
                System.out.println("No solution");
            }
        }
        
    }
}