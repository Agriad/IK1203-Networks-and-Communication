import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        // Your code here
        String clientSentence = "";
        boolean on = true;

        ServerSocket welcomeSocket = new ServerSocket(Integer.valueOf(args[0]));

        while (true) {
            on = true;
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            StringBuilder br = new StringBuilder();

            while (on) {
                System.out.println("test 1");
                clientSentence = inFromClient.readLine();
                br.append(clientSentence + "\r\n");
                if (clientSentence.equals("")) {
                    on = false;
                    System.out.println("test 2");
                }
            }

            System.out.println("test 3");
            clientSentence = br.toString();
            outToClient.writeBytes(clientSentence + "\r\n");
            connectionSocket.close();
        }
    }
}
