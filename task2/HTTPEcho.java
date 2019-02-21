import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        // Your code here
        String clientSentence = "";
        boolean on = true;

        ServerSocket welcomeSocket = new ServerSocket(Integer.valueOf(args[0]));
        //PrintWriter pwIn = new PrintWriter("in.txt", StandardCharsets.UTF_8);
        //PrintWriter pwOut = new PrintWriter("out.txt", StandardCharsets.UTF_8);

        while (true) {
            on = true;
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            StringBuilder br = new StringBuilder();

            br.append("HTTP/1.1 200 OK\r\n").append("\r\n");

            while (on) {
                clientSentence = inFromClient.readLine();
                br.append(clientSentence + "\r\n");
                //pwIn.print(clientSentence + "\r\n");
                if (clientSentence.equals("")) {
                    on = false;
                }
            }

            clientSentence = br.toString();

            /*
            pwOut.print(clientSentence);

            pwIn.flush();
            pwOut.flush();

            pwIn.close();
            pwOut.close();
            */

            //System.out.println(clientSentence);
            outToClient.writeBytes(clientSentence);
            connectionSocket.close();
        }
    }
}
