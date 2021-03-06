package httpRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.Scanner;

public class MainHttpClient {
    private static Scanner scanner;
    private static int input;
    private static int input2;
    private static int deleted;
    private static int update;

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
                    httpPost();
                    break;
                case 2:
                    httpGet();
                    break;
                case 3:
                    httpPut();
                    break;
                case 4:
                    httpDelete();
                    break;
            }
        }
    }
    public static void httpPost() throws IOException, InterruptedException {
        Map<Object, Object> person = new HashMap<>();
        person.put("personId", 8);
        person.put("startDate", "24/10/2010");
        person.put("duration", "24/10/2011");
        person.put("membershipTypeId", "2");
        person.put("currentId", "8");
        person.put("pastId", "5");
        person.put("upcomingId", "4");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted.writeValueAsString(person);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println(response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGet() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id number of the member you are looking for: ");
        input2 = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/" + input2))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void httpPut() throws IOException, InterruptedException {
        System.out.println("Enter the id number of the member you are looking for: ");
        Map<Object, Object> person = new HashMap<>();
        update = scanner.nextInt();
        person.put("personId", 1);
        person.put("startDate", "10/11/2009");
        person.put("duration", "10/11/2012");
        person.put("membershipTypeId", "1");
        person.put("currentId", "4");
        person.put("pastId", "2");
        person.put("upcomingId", "3");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(person);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/" + update))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                System.out.println("Updated Membership : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void httpDelete() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Id # to delete Membership: ");
        deleted = scanner.nextInt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/" + deleted))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Deleted Membership" + deleted + "Deleted");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}