package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class PagedJson {

    private final int ENTRIES_PER_PAGE = Util.entriesPerPage;
    private final JsonArray JSON_ARRAY;
    public final int TOTAL_PAGES;
    private int currentPage;
    public final RequestType REQUEST_TYPE;

    public PagedJson(JsonArray jsonArray, RequestType requestType) {
        JSON_ARRAY = jsonArray;
        REQUEST_TYPE = requestType;
        TOTAL_PAGES = (int) Math.ceil((double) jsonArray.size() / ENTRIES_PER_PAGE);
        currentPage = 1;
    }

    public ArrayList<String> previousPage() {
        if (currentPage == 1) {
            return null;
        } else {
            currentPage--;
            return requestCurrentPage();
        }
    }

    public ArrayList<String> nextPage() {
        if (currentPage == TOTAL_PAGES) {
            return null;
        } else {
            currentPage++;
            return requestCurrentPage();
        }
    }

    public ArrayList<String> requestCurrentPage() {
        ArrayList<String> result = new ArrayList<>();

        for (int i = ENTRIES_PER_PAGE * (currentPage - 1);
             i < Math.min(ENTRIES_PER_PAGE * currentPage, JSON_ARRAY.size()); i++) {
            JsonObject jo = JSON_ARRAY.get(i).getAsJsonObject();

            StringBuilder sb = new StringBuilder();
            sb.append(jo.get("name").getAsString());
            if (REQUEST_TYPE != RequestType.CATEGORY) {
                sb.append("\n");
            }

            if (REQUEST_TYPE == RequestType.NEW) {
                ArrayList<String> artists = new ArrayList<>();
                for (JsonElement artistJe : jo.getAsJsonArray("artists")) {
                    artists.add(artistJe.getAsJsonObject().get("name").getAsString());
                }
                sb.append(artists.toString()).append("\n");
            }

            if (REQUEST_TYPE != RequestType.CATEGORY) {
                sb.append(jo.get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString());
            }

            result.add(sb.toString());
        }

        return result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

}
