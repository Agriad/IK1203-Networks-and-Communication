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

            br.append("HTTP/1.1 200 OK\r\n").append("\r\n");

            while (on) {
                clientSentence = inFromClient.readLine();
                br.append(clientSentence + "\r\n");
                if (clientSentence.equals("")) {
                    on = false;
                }
            }

            clientSentence = br.toString();

            System.out.println(clientSentence);

            String[] request = clientSentence.split("\n");

            String firstLine = request[2];

            String[] firstLineSplit = firstLine.split(" ");
            String check = firstLineSplit[1];
            String isItAsk = "";
            String serverOutput = null;

            if (!check.isEmpty() && check.length() >= 4) {
                isItAsk = check.substring(1, 4);
            }

            if (isItAsk.equals("ask")) {
                try {
                    String[] infoSplit = firstLineSplit[1].split("\\?");
                    String[] typeSplit = infoSplit[1].split("&");
                    String[] Input = new String[3];
                    int count = 0;

                    for (String string : typeSplit) {
                        String[] input = string.split("=");
                        Input[count] = input[1];
                        count++;
                    }

                    //serverOutput = "HTTP/1.1 200 OK\r\n\r\n";

                    if (Input[2] == null) {
                        //serverOutput = serverOutput + TCPClient.askServer(Input[0], Integer.parseInt(Input[1]));
                        serverOutput = TCPClient.askServer(Input[0], Integer.parseInt(Input[1]));
                    }
                    else {
                        //serverOutput =
                        //        serverOutput + TCPClient.askServer(Input[0], Integer.parseInt(Input[1]), Input[2]);
                        serverOutput = TCPClient.askServer(Input[0], Integer.parseInt(Input[1]), Input[2]);
                    }

                    outToClient.writeBytes("HTTP/1.1 200 OK\r\n\r\n" + serverOutput);
                    connectionSocket.close();
                }
                catch (UnknownHostException exception) {
                    System.out.println(exception.fillInStackTrace());
                    outToClient.writeBytes("HTTP/1.1 404 Not Found\r\n\r\n");
                    System.out.println("HTTP/1.1 404 Not Found\r\n\r\n");
                    connectionSocket.close();
                }
            }
            else {
                serverOutput = "HTTP/1.1 400 Bad Request\r\n\r\n";
                outToClient.writeBytes(serverOutput);
                connectionSocket.close();
            }
        }
    }
}
