package httpRequest;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class MainHttpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        //httpPOSTRequest();
        httpGetPerson();
        //httpPutRequest();
        //httpDeleteRequest();
    }

        public static void httpGetPerson() throws IOException, InterruptedException {
            Scanner scanner = new Scanner(System.in);
            String search;
            System.out.println("Please enter first name.");
            System.out.println("Please enter last name.");
            System.out.println("Please enter address.");
            System.out.println("Please enter email.");
            System.out.println("Please enter phone number.");
            search = scanner.next();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/person"))
                    .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode()==200) {
                System.out.println("*****" + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
