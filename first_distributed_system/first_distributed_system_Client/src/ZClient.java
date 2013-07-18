import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ZClient {
    private String hostname;
    private int port;
    Socket socketClient;

    public ZClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() throws UnknownHostException, IOException {
        System.out.println("Attempting to connect to " + hostname + ":" + port);
        socketClient = new Socket(hostname, port);
        System.out.println("Connection Established");
    }

    public void disconnect() throws IOException {
        socketClient.close();
    }

    public void interact() throws IOException {

        BufferedReader connectionIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        PrintWriter connectionOut = new PrintWriter(socketClient.getOutputStream(), true);

        boolean go = true;
        go = readResponse(connectionIn);
        while(go){
            sendResponse(connectionOut);
            go = readResponse(connectionIn);
        }

    }

    private boolean readResponse(BufferedReader connectionIn) throws IOException {
        String serverResponse;
        serverResponse = connectionIn.readLine();
        if (serverResponse != null){
            System.out.println(serverResponse);
            return true;
        }
        else{
            return false;
        }
    }

    private void sendResponse(PrintWriter connectionOut) throws IOException {
        Scanner scan = new Scanner(System.in);
        String userInput;
        userInput = scan.nextLine();
        connectionOut.println(userInput);
    }

    public static void main(String arg[]) {
        ZClient client = new ZClient("localhost", 9990);
        try {
            client.connect();
            client.interact();
            client.disconnect();
        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Problem with connection. Message: " + e.getMessage());
        }
    }
}