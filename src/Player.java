import messages.Colors;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Player {

    private Character[] party;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8080);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String insertCharName = consoleInput.readLine();
            System.out.println(insertCharName);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (!socket.isClosed()) {
                String msgToSend = inputReader.readLine();
                if (msgToSend.compareTo("EXIT") == 0) {
                    break;
                }
                output.write(msgToSend);
                output.newLine();
                output.flush();

                receiveBroadcast(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                System.out.println(Colors.RED + "You have been disconnected from the server" + Colors.RESET);
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void receiveBroadcast(Socket socket) {
        ExecutorService viewMessage = Executors.newCachedThreadPool();
        Socket finalSocket = socket;

        viewMessage.submit(new Thread(() -> {
            String msgReceived = null;
            try {
                BufferedReader consoleRead = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                while (!viewMessage.isShutdown() && (msgReceived = consoleRead.readLine()) != null) {
                    System.out.println(msgReceived);
                }
            } catch (EOFException e) {
                viewMessage.shutdownNow();
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                viewMessage.shutdownNow();
            }
        }));
    }
}