import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private ServerSocket gameSocket;
    private ExecutorService service;
    //private Character[] party;
    private Party party;
    private Party party2;

    private BufferedReader input;
    private BufferedWriter output;

    public Game() throws IOException {
        /*gameSocket = new ServerSocket(8082);
        this.input = new BufferedReader(new InputStreamReader(System.in));
        this.output = new BufferedWriter(new OutputStreamWriter(gameSocket.getOutputStream()));*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players?");
        //this.party = new Character[scanner.nextInt()];
        this.gameSocket = gameSocket;
    }


    public void startGame() throws IOException {
        gameSocket = new ServerSocket(8082);
        service = Executors.newCachedThreadPool();

        while (true) {
            acceptConnection();
        }
    }

    public void acceptConnection() throws IOException {
        Socket clientSocket = gameSocket.accept();
        CharacterConnectionHandler characterConnectionHandler =
                new CharacterConnectionHandler(clientSocket,
                        "temporary" );
        service.submit(characterConnectionHandler);
        //addClient(characterConnectionHandler);
    }

    public class CharacterConnectionHandler implements Runnable {

        private String name;
        private Socket clientSocket;
        private BufferedWriter out;
        private String message;

        public CharacterConnectionHandler(Socket clientSocket, String name) throws IOException {
            this.clientSocket = clientSocket;
            this.name = name;
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        }

        @Override
        public void run() {

        }

       /* @Override
        public void run() {
            addClient(this);
            try {
                // BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Scanner in = new Scanner(clientSocket.getInputStream());
                while (in.hasNext()) {
                    message = in.nextLine();
                    if (isCommand(message)) {
                        dealWithCommand(message);
                        continue;
                    }
                    if (message.equals("")) {
                        continue;
                    }

                    broadcast(name, message);
                }
            } catch (IOException e) {
                System.err.println(messages.CLIENT_ERROR + e.getMessage());
            } finally {
                removeClient(this);
            }
        }*/
    }
}
