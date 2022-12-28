import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class CookieClientHandler implements Runnable {

    private Socket socket;
    private int countMessages;
    ArrayList<String> messages;
    
    public CookieClientHandler(Socket socket, ArrayList<String> messages, int countMessages) {
        this.socket = socket;
        this.countMessages = countMessages;
        this.messages = messages;
    }

    @Override
    public void run() {
        
        // Random number
        Random rnd = new SecureRandom();

        System.out.printf("New connection on port %d\n", socket.getPort());
        String command = "";
        String response;

        try {
            while (!(command = IOUtils.read(socket)).toLowerCase().equals("close")) {
                String payload = messages.get(rnd.nextInt(countMessages));

                if (command.toLowerCase().equals("get-cookie")) {
                    response = "cookie-text " + payload;
                    IOUtils.write(this.socket, response);
                }else{
                    response = "Invalid command: " + command;
                    IOUtils.write(this.socket, response);       
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response = "close Good Bye, Have a great Day!";
 
        try {
            IOUtils.write(socket, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
}
