package httpRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class HttpClientTournament {
    private static Scanner scanner;
    private static int input;
    private static int input2;
    private static int update;
    private static int deleted;
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        while (true) {
            System.out.println("Press 1 to Add, Press 2 to Get, Press 3 to Update, " +
                    "Press 4 to Delete, Press 0 to Cancel");
            scanner = new Scanner(System.in);
            input = scanner.nextInt();

            switch(input){
                case 0:
                    System.out.println("Exiting....");
                    System.exit(0);
                case 1:
                    httpPostTournament();
                    break;
                case 2:
                    httpGetTournament();
                    break;
                case 3:
                    httpPutTournament();
                    break;
                case 4:
                    httpDeleteTournament();
                    break;
            }
        }
    }
    public static void httpPostTournament() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("start", "11/10/2004");
        people.put("end", "11/12/2004");
        people.put("location", "Arizona");
        people.put("fee", 10.0);
        people.put("prize", 55.0);
        people.put("members", "Steve, Bob, James");
        people.put("standings", "1.Bob, 2.James, 3.Steve");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted.writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Posted Tournament : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGetTournament() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id # for the tournament you are looking for: ");
        input2 = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament/" + input2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("The tournament you requested : " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void httpPutTournament() throws IOException, InterruptedException {
        System.out.println("Enter Id # of the tournament to update: ");
        update = scanner.nextInt();
        Map<Object, Object> people = new HashMap<>();
        people.put("start", "12/12/2000");
        people.put("end", "12/12/2001");
        people.put("location", "Colorado");
        people.put("fee", 18.0);
        people.put("prize", 95.0);
        people.put("members", "Steve, James, Bob, Jimmy");
        people.put("standings", "1.Jimmy, 2.James, 3.Bob, 4.Steve");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament/" + update))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                System.out.println("Updated Tournament : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpDeleteTournament() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Id # to delete tournament: ");
        deleted = scanner.nextInt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament/" + deleted))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Deleted Tournament" + deleted + " deleted");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}