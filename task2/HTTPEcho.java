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
                clientSentence = inFromClient.readLine();
                br.append(clientSentence + "\n");
                if (clientSentence.equals("")) {
                    on = false;
                }
            }

            clientSentence = br.toString();
            outToClient.writeBytes(clientSentence);
            connectionSocket.close();
        }
    }
}
