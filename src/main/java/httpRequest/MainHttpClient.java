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
                    httpPostMembership();
                    break;
                case 2:
                    httpGetMembership();
                    break;
                case 3:
                    httpPutMembership();
                    break;
                case 4:
                    httpDeleteMembership();
                    break;
            }
        }
    }
    public static void httpPostMembership() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "Lenny");
        people.put("lastName", "Leonard");
        people.put("address", "123 Street");
        people.put("email", "LennyL@hotmail.com");
        people.put("phoneNumber", 1234567);
        people.put("startDate", "10/24/2010");
        people.put("duration", "10/24/2011");
        people.put("type", "normal");
        people.put("currentId", "8");
        people.put("pastId", "5");
        people.put("upcomingId", "4");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/person"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Posted Membership : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGetMembership() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String peoples;
        System.out.println("Please enter the id number of the person you are looking for: ");
        peoples = scanner.next();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership"))
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

    public static void httpPutMembership() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "Pete");
        people.put("lastName", "Peterson");
        people.put("address", "321 Street");
        people.put("email", "Peteyp@gmail.com");
        people.put("phoneNumber", "3214567");
        people.put("startDate", "10/11/2009");
        people.put("duration", "10/11/2012");
        people.put("type", "trial");
        people.put("currentId", "4");
        people.put("pastId", "2");
        people.put("upcomingId", "3");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership"))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Updated Membership : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void httpDeleteMembership() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String deleted;
        System.out.println("Type Id of the membership to delete: ");
        deleted = scanner.next();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/search/"))
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Deleted Membership : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}