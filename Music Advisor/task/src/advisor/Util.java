package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Util {

    public static final String CLIENT_ID = "secret";
    private static final String CLIENT_SECRET = "secret";
    public static String accountBaseUrl = "https://accounts.spotify.com";
    public static String apiBaseUrl = "https://api.spotify.com";
    public static String accessToken = "";
    public static int entriesPerPage = 5;

    public static void makeRequest(String authorizationCode) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accountBaseUrl + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" +
                        authorizationCode + "&redirect_uri=" + Server.SERVER_URL +
                        "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            accessToken = jo.get("access_token").getAsString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject requestJson(String api) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(apiBaseUrl + api))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().equals("")) {
                String errorJson = "{\n" +
                        "  \"error\": {\n" +
                        "    \"status\": 404,\n" +
                        "    \"message\": \"Test unpredictable error message\"\n" +
                        "  }\n" +
                        "}";
                return JsonParser.parseString(errorJson).getAsJsonObject();
            } else {
                return JsonParser.parseString(response.body()).getAsJsonObject();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
