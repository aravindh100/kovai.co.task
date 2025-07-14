import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class FolderApiClient {
    private static final String API_TOKEN = "M1XbCrQnV47mHjZcYnpsUBOFPvDFo/PJhrSaPRNlJb4MYO0gWTQamNFIw6zn7KLCo5e4xx7aTm5dbDhSxFBJS64Qd34M8gn0/78uGxilK4Rg4MVNLVO62u18ElX59BSJJ1Pcfyar2N5TqrQMEypEOQ==";
    private static final String BASE_URL = "https://apihub.document360.io/v2/Drive/Folder";
    private static String folderId;

    public static void fetchFolders() throws IOException {
        System.out.println("\nGET - Fetching folders");
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        printResponse(conn);
    }

    public static void createFolder(String folderName) throws IOException {
        System.out.println("\nPOST - Creating folder");
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JSONObject body = new JSONObject();
        body.put("name", folderName);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes());
        }

        String response = getResponse(conn);
        JSONObject json = new JSONObject(response);
        folderId = json.getString("id");
        System.out.println("Created folder ID: " + folderId);
    }

    public static void updateFolder(String newName) throws IOException {
        System.out.println("\nPUT - Updating folder");
        URL url = new URL(BASE_URL + "/" + folderId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JSONObject body = new JSONObject();
        body.put("name", newName);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes());
        }

        printResponse(conn);
    }

    public static void deleteFolder() throws IOException {
        System.out.println("\nDELETE - Deleting folder");
        URL url = new URL(BASE_URL + "/" + folderId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        printResponse(conn);
    }

    private static void printResponse(HttpURLConnection conn) throws IOException {
        int status = conn.getResponseCode();
        String response = getResponse(conn);
        System.out.println("Status: " + status);
        System.out.println("Response: " + response);
    }

    private static String getResponse(HttpURLConnection conn) throws IOException {
        InputStream stream = conn.getResponseCode() >= 400 ? conn.getErrorStream() : conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) response.append(line);
        return response.toString();
    }
}
