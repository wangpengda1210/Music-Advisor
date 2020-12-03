package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static String authorizationCode = "";
    static PagedJson currentPagedJson;
    static boolean paging = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (args.length >= 2) {
            if ("-access".equals(args[0])) {
                Util.accountBaseUrl = args[1];
            } else if ("-resource".equals(args[0])) {
                Util.apiBaseUrl = args[1];
            }
        }

        if (args.length >= 4) {
            if ("-access".equals(args[2])) {
                Util.accountBaseUrl = args[3];
            } else if ("-resource".equals(args[2])) {
                Util.apiBaseUrl = args[3];
            }
        }

        if (args.length >= 6 && "-page".equals(args[4])) {
            Util.entriesPerPage = Integer.parseInt(args[5]);
        }

        boolean authenticated = false;
        while (true) {
            String option = scanner.nextLine().toLowerCase();
            if (option.equals("auth") && !authenticated) {
                authentication();
                System.out.println("making http request for access_token...");
                Util.makeRequest(authorizationCode);
                authenticated = true;
                System.out.println("Success!");
            } else if (option.equals("exit")) {
                System.out.println("---GOODBYE!---");
                break;
            } else {
                if (!authenticated) {
                    System.out.println("Please, provide access for application.");
                } else {
                    if (option.equals("new")) {
                        getNew();
                    } else if (option.equals("featured")) {
                        getFeatured();
                    } else if (option.equals("categories")) {
                        getCategories();
                    } else if (option.contains("playlists")) {
                        String genre = option.split(" ")[1];
                        getPlaylistsByGenre(genre);
                    } else if (option.equals("next") && paging) {
                        ArrayList<String> nextPage = currentPagedJson.nextPage();
                        if (nextPage == null) {
                            System.out.println("No more pages.");
                        } else {
                            printPage(nextPage);
                        }
                    } else if (option.equals("prev")) {
                        ArrayList<String> prevPage = currentPagedJson.previousPage();
                        if (prevPage == null) {
                            System.out.println("No more pages.");
                        } else {
                            printPage(prevPage);
                        }
                    } else {
                        System.out.println("Not a correct option");
                        paging = false;
                    }
                }
            }
        }
    }

    private static void getPlaylistsByGenre(String genre) {
        JsonObject jo = Util.requestJson("/v1/browse/categories/" +
                genre.toLowerCase() + "/playlists");
        if (jo == null) {
            System.out.println("error");
            paging = false;
        } else if (jo.get("error") != null) {
            System.out.println(jo.get("error").getAsJsonObject()
                    .get("message").getAsString());
            paging = false;
        } else {

            JsonArray genrePlaylistJa = jo.getAsJsonObject("playlists")
                    .getAsJsonArray("items");

            currentPagedJson = new PagedJson(genrePlaylistJa, RequestType.PLAYLIST);

            printPage(currentPagedJson.requestCurrentPage());

            paging = true;

        }
    }

    private static void getCategories() {
        JsonObject jo = Util.requestJson("/v1/browse/categories");
        if (jo == null) {
            System.out.println("Error");
            paging = false;
        } else {

            JsonArray categoryJa = jo.getAsJsonObject("categories")
                    .getAsJsonArray("items");

            currentPagedJson = new PagedJson(categoryJa, RequestType.CATEGORY);

            printPage(currentPagedJson.requestCurrentPage());

            paging = true;

        }
    }

    private static void getFeatured() {
        JsonObject jo = Util.requestJson("/v1/browse/featured-playlists");
        if (jo == null) {
            System.out.println("Error");
            paging = false;
        } else {

            JsonArray featuredPlaylistJa = jo.getAsJsonObject("playlists")
                    .getAsJsonArray("items");

            currentPagedJson = new PagedJson(featuredPlaylistJa, RequestType.FEATURED);

            printPage(currentPagedJson.requestCurrentPage());

            paging = true;

        }
    }

    private static void getNew() {
        JsonObject jo = Util.requestJson("/v1/browse/new-releases");
        if (jo == null) {
            System.out.println("Error");
            paging = false;
        } else {

            JsonArray albumJa = jo.getAsJsonObject("albums")
                    .getAsJsonArray("items");

            currentPagedJson = new PagedJson(albumJa, RequestType.NEW);

           printPage(currentPagedJson.requestCurrentPage());

           paging = true;

        }
    }

    private static void printPage(ArrayList<String> page) {
        for (String item : page) {
            System.out.println(item);
            if (currentPagedJson.REQUEST_TYPE != RequestType.CATEGORY) {
                System.out.println();
            }
        }
        System.out.printf("---PAGE %d OF %d---%n", currentPagedJson.getCurrentPage(),
                currentPagedJson.TOTAL_PAGES);
    }

    private static void authentication() {
        Server.createServer();
        System.out.println("use this link to request the access code:\n" +
                Util.accountBaseUrl + "/authorize?" +
                "client_id=" + Util.CLIENT_ID + "&redirect_uri=" +
                Server.SERVER_URL + "&response_type=code");
        System.out.println("waiting for code...");

        while (true) {
            authorizationCode = Server.getCode();
            if (!"".equals(authorizationCode)) {
                Server.stopServer();
                break;
            }
        }

        System.out.println("code received");
    }
}
