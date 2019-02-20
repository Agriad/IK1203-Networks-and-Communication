import java.net.*;
import java.io.*;

import tcpclient.TCPClient;

public class HTTPAsk {
    public static void main(String[] args) throws java.io.IOException {
        // Your code here
        String clientSentence = "";
        boolean on = true;
        int portNum = Integer.valueOf(args[0]);

        ServerSocket welcomeSocket = new ServerSocket(portNum);

        while (true) {
            on = true;
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            StringBuilder br = new StringBuilder();

            while (on) {
                clientSentence = inFromClient.readLine();
                br.append(clientSentence + "\r\n");
                if (clientSentence.equals("")) {
                    on = false;
                }
            }

            clientSentence = br.toString();
            String[] request = clientSentence.split("\n");
            String firstLine = request[0];
            String secondLine = request[1];
            String[] firstLineSplit = firstLine.split(" ");
            String[] secondLineSplit = secondLine.split(" ");
            //outToClient.writeBytes(firstLineSplit[1] + secondLineSplit[1] + "\r\n");
            String serverOutput = TCPClient.askServer(secondLineSplit[1], portNum, firstLineSplit[1]);
            //clientSentence = br.toString();
            //outToClient.writeBytes(clientSentence + "\r\n");
            connectionSocket.close();
        }
    }
}
