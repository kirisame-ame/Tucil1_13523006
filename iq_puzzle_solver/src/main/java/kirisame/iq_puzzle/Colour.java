package kirisame.iq_puzzle;
import java.util.HashMap;
import java.util.Map;

public class Colour {
    private static final Map<Character, String> colorMap = new HashMap<>();

    static {
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('A' + i);
            int r = ((i+1) * 50) % 256;
            int g = ((i+1) * 200) % 256;
            int b = ((i+1) * 120) % 256;
            String colorCode = String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
            colorMap.put(letter, colorCode);
        }
    }

    public static void colourPrint(char letter) {
        String reset = "\u001B[0m";
        System.out.print(colorMap.get(letter) + letter + reset);
    }
}
