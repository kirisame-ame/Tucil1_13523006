package kirisame.iq_puzzle;
public class Utils {
    public static boolean isNumeric(String str){
        for (char c : str.toCharArray()){
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    public static boolean isCapital(char c){
        return c >= 'A' && c <= 'Z';
    }
    public static char idToChar(int id){
        if(id>0){
            return (char) (id-1 +'A');
        }
        return '-';
    }
    public static int charToId(char c){
        return c -'A'+1;
    }
    public static char getFirstLetter(String str)
    {
        for (char c : str.toCharArray())
        {
            if(isCapital(c)) return c;
        }
        System.err.println("No character in string: " + str);
        return '-';
    }
}
