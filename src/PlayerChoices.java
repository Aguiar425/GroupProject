import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class PlayerChoices {
    public static HashMap<String, String> playerChoices() {
        String fileName = "resources/chapters/playerChoices.txt";
        HashMap<String, String> playerChoicesMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String key = parts[0];
                String value = parts[1];
                playerChoicesMap.put(key, value);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return playerChoicesMap;
    }
}
