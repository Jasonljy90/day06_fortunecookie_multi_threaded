import java.io.File;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws Exception {
        //java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt
        Integer port = Integer.parseInt(args[0]);
        String fileName = args[1];
        
        // Read from file and load to arraylist
        ArrayList<String> messages = new ArrayList<>();
        int countMessages = 0;

        File myObj = new File(fileName);
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                messages.add(data);
                countMessages++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }     
        
        // Create a thread pool
        ExecutorService thrPool = Executors.newFixedThreadPool(2);

        // Create a ServerSocket and bind to the port
        ServerSocket server = new ServerSocket(port) ;
        System.out.printf("Listening on port %d\n", port);

        // Server loop
        while (true) {
            // Wait for a connection
            System.out.println("Waiting for connections...");
            Socket socket = server.accept();

            // Create a HandleClient to handle the client socket
            CookieClientHandler client = new CookieClientHandler(socket, messages, countMessages);
            // Do not do this. THIS IS NOT A THREAD
            // client.run();
            
            // Submit the Runnable to the threadpool
            thrPool.submit(client);
        }
    }
}
