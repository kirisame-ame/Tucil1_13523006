package kirisame.iq_puzzle;
import java.util.HashMap;
import java.util.Map;

public class Color {
    public static final Map<Character, String> colorMap = new HashMap<>();

    static {
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('A' + i);
            int r = ((i+1) * 50) % 256;
            int g = ((i+1) * 200) % 256;
            int b = ((i+1) * 120) % 256;
            String colorCode = String.format("rgb(%d,%d,%d)", r, g, b);
            colorMap.put(letter, colorCode);
        }
        colorMap.put('-', "rgb(255,255,255)");
    }
    public static void colorPrint(char letter) {
        String reset = "\u001B[0m";
        System.out.print(colorMap.get(letter) + letter + reset);
    }
    public static String colorString(char letter) {
        String reset = "\u001B[0m";
        return (colorMap.get(letter) + letter + reset);
    }
}
