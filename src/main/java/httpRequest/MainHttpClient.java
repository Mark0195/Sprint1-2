package httpRequest;

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
            System.out.println("Press 1 to add, Press 2 to get , Press 3 to update , " +
                    "Press 4 to delete , Press 0 to exit");
            scanner = new Scanner(System.in);
            input = scanner.nextInt();

            switch(input){
                case 0:
                    System.out.println("exiting!");
                    System.exit(0);
                case 1:
                    //httpPostPerson();
                    break;
                case 2:
                    httpGetPerson();
                    break;
                case 3:
                    //httpPutPerson();
                    break;
                case 4:
                    httpDeletePerson();
                    break;
            }
        }
    }

    public static void httpGetPerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String peoples;
        //System.out.println("Enter first name: ");
        System.out.println("Enter last name: ");
        //System.out.println("Enter address: ");
        //System.out.println("Enter email: ");
        //System.out.println("Enter phone number: ");
        peoples = scanner.next();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/person/search/findByLastName?lastName="))
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
    public static void httpPostPerson() throws IOException, InterruptedException {
        Map<Object, Object> person = new HashMap<>();
        person.put("firstName", "");
        person.put("lastName", "");
        person.put("address", "");
        person.put("email", "");
        person.put("phoneNum", "");
    }

    public static void httpDeletePerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String deleted;
        System.out.println("Type Id of the person to delete: ");
        deleted = scanner.next();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/person/search/"))
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("File Deleted : " + responseBody);
    }
}