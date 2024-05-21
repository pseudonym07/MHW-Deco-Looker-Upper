import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class MyFrame {
    public MyFrame() throws IOException, FontFormatException {


        File fontFile = new File("src/assets/fonts/Monster hunter.ttf");
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setTitle("Monster Hunter World Decorations Searcher");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField searchField = new JTextField(20);
        searchField.setEditable(true);

        JButton searchButton = new JButton("Search");

        frame.add(searchField);
        frame.add(searchButton);

        frame.setVisible(true);
    }


    public static JsonObject getDecoInfo(String decoName){
        // Constructing the URL for the API request
        String url = "https://mhw-db.com/decorations/" + decoName;

        // Create an HTTP client object
        HttpClient client = HttpClient.newHttpClient();

        // Build an HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            // Send the request to the API, and get a response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // A 200 means success!
            if (response.statusCode() == 200) {
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                // to print out the response in a legible way
                // in a production environment, you would want to use the condensed version, as it takes up less space.
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonOutput = gson.toJson(jsonResponse);

                //System.out.println((jsonOutput));

                // returning it, but we aren't doing anything with it in this example.
                return jsonResponse;
            } else {
                System.out.println("Failed to retrieve data. HTTP status: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            return null;
        }


    }

    private String returnSearch(String searchTerm) {
        return "null";


    }



}
