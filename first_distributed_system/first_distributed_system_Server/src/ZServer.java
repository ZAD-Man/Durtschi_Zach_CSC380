import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ZServer extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private MathLogic mathLogic = new MathLogic();

    public ZServer(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        System.out.println("Starting the server at port:" + port);
        serverSocket = new ServerSocket(port);

        System.out.println("Waiting for clients...");
        Socket client = serverSocket.accept();
        System.out.println("Client accepted");

        sendMathPrompt(client);
    }

    private void sendMathPrompt(Socket client) throws IOException {
        PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String inputOperation = "";
        String firstNumber = "";
        String secondNumber = "";

        writer.println("Hello. Would you like to Add(1) or Subtract(2)?");

        inputOperation = reader.readLine();
        while (!inputOperation.matches("[1-2]")) {
            writer.println("Please input 1 to Add or 2 to Subtract");
            inputOperation = reader.readLine();
        }

        System.out.println("Operation response received: " + inputOperation);

        writer.println("Please input the first number");

        firstNumber = reader.readLine();
        while (!firstNumber.matches("^[0-9]+$")) {
            writer.println("Please input a number");
            firstNumber = reader.readLine();
        }

        System.out.println("First number response received: " + firstNumber);

        writer.println("Please input the second number");

        secondNumber = reader.readLine();
        while (!secondNumber.matches("^[0-9]+$")) {
            writer.println("Please input a number");
            
            secondNumber = reader.readLine();
        }

        System.out.println("Second number response received: " + secondNumber);

        int operationNumber = Integer.parseInt(inputOperation);
        int numberOne = Integer.parseInt(firstNumber);
        int numberTwo = Integer.parseInt(secondNumber);
        int result;
        boolean errored = false;

        switch (operationNumber) {
            case 1:
                result = mathLogic.add(numberOne, numberTwo);
                break;
            case 2:
                result = mathLogic.subtract(numberOne, numberTwo);
                break;
            default:
                result = 0;
                errored = true;
                break;
        }

        writer.println("Result: " + result);
        if (errored) {
            writer.println("Warning: There was an error in the operation");
        }
        
        writer.close();
    }

    public static void main(String[] args) {
        int portNumber = 9990;

        try {
            ZServer socketServer = new ZServer(portNumber);
            socketServer.startServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}