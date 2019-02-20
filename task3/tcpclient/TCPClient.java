package tcpclient;
import java.net.*;
import java.io.*;


public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        boolean end = true;
        boolean check = true;
        String modifiedSentence = "";
        String reply;
        StringBuilder sb = new StringBuilder();

        try {
            Socket clientSocket = new Socket(hostname, port);
            clientSocket.setSoTimeout(4000);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(ToServer + '\n');

            while (end) {
                reply = inFromServer.readLine();

                if (reply == null) {
                    end = false;
                }
                else {
                    sb.append(reply + '\n');
                }
            }

            modifiedSentence = sb.toString();
            clientSocket.close();
            return modifiedSentence;
        }
        catch (SocketTimeoutException ex) {
            modifiedSentence = sb.toString();
            return modifiedSentence;
        }
    }

    public static String askServer(String hostname, int port) throws IOException {
        boolean end = true;
        boolean check = true;
        String modifiedSentence = "";
        String reply;
        StringBuilder sb = new StringBuilder();

        try {
            Socket clientSocket = new Socket(hostname, port);
            clientSocket.setSoTimeout(4000);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes("" + '\n');


            while (end) {
                reply = inFromServer.readLine();

                if (reply == null) {
                    end = false;
                }
                else {
                    sb.append(reply + '\n');
                }
            }
            modifiedSentence = sb.toString();
            clientSocket.close();
            return modifiedSentence;
        }
        catch (SocketTimeoutException ex) {
            modifiedSentence = sb.toString();
            return modifiedSentence;
        }
    }

}
