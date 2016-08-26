import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jessicatracy on 8/26/16.
 */
public class ConnectionHandler implements Runnable {
    Socket clientSocket;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            handleIncomingConnection(clientSocket);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void handleIncomingConnection(Socket clientSocket) throws IOException {


        System.out.println("Now displaying info about who has connected to our server: ");
        System.out.println("Connection from " + clientSocket.getInetAddress().getHostAddress());

        //Read in info from client
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //Print output to client
        PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine;
        String userNameFromClient;
        while((inputLine = inputFromClient.readLine()) != null) {
//            System.out.println("Received message \"" + inputLine + "\" from " + clientSocket.toString());
            if (!inputLine.startsWith("name=")) {
                outputToClient.println("You did not send input in the form of \"name=name-of-client\". Reconnect and try again.");
            } else {
//                outputToClient.println("Message received! :-)");
                userNameFromClient = inputLine.split("=")[1];
//                System.out.println("ON SERVER SIDE-> userName is " + userNameFromClient);
                String myOutput = userNameFromClient + " said: ";
                inputLine = inputFromClient.readLine();
                myOutput += inputLine;
                System.out.println("* " + myOutput);
                outputToClient.println("Your message was received! " + myOutput);
//                while((inputLine = inputFromClient.readLine()) != null) {
//                    outputToClient.println(userNameFromClient + " said: " + inputLine);
//                }
            }
        }

    }
}