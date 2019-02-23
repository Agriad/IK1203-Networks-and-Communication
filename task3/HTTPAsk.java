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
            String[] infoSplit = firstLineSplit[1].split("\\?");
            String[] typeSplit = infoSplit[1].split("&");
            String[] Input = new String[3];

            //System.out.println(firstLineSplit[1]);
            //System.out.println(infoSplit[1]);

            int count = 0;

            for (String string : typeSplit) {
                String[] input = string.split("=");
                System.out.println("loop " + input[1]);
                Input[count] = input[1];
                count++;
            }

            String serverOutput = null;

            if (Input[2] == null) {
                serverOutput = TCPClient.askServer(Input[0], Integer.parseInt(Input[1]));
            }
            else {
                serverOutput = TCPClient.askServer(Input[0], Integer.parseInt(Input[1]), Input[2]);
            }

            outToClient.writeBytes(serverOutput);
            connectionSocket.close();
        }
    }
}

//http://localhost:8888/ask?hostname=whois.iis.se&port=43&string=kth.se
//http://localhost:8888/ask?hostname=time.nist.gov&port=13
