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

public class HttpClientPerson {
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
                    httpPostPerson();
                    break;
                case 2:
                    httpGetPerson();
                    break;
                case 3:
                    httpPutPerson();
                    break;
                case 4:
                    httpDeletePerson();
                    break;
            }
        }
    }
    public static void httpPostPerson() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "Lenny");
        people.put("lastName", "Leonard");
        people.put("address", "123 Street");
        people.put("email", "LennyL@hotmail.com");
        people.put("phoneNumber", 1234567);

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted.writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/person"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Posted Person");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGetPerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Id # of the person you are looking for: ");
        input2 = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/person/" + input2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("The person you requested : " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void httpPutPerson() throws IOException, InterruptedException {
        System.out.println("Enter Id # to update person: ");
        update = scanner.nextInt();
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "Carl");
        people.put("lastName", "Carlson");
        people.put("address", "456 Street");
        people.put("email", "CarlC@gmail.com");
        people.put("phoneNumber", 7654321);

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/person/" + update))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                System.out.println("Updated person : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpDeletePerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Id # to delete person: ");
        deleted = scanner.nextInt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/person/" + deleted))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Deleted Person" + deleted + "deleted");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}