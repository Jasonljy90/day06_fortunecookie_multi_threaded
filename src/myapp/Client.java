import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {

        // Get the host
        String input = args[0];
        String[] hostPort = input.split(":");
        String host = hostPort[0];
        Integer port = Integer.parseInt(hostPort[1]);
        
        // Create the socket to the server
        Socket socket = new Socket(host, port);

        System.out.printf("Connected to %s:%d\n", host, port);
        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.println("Please enter command: ");
            String command = scan.nextLine();
            IOUtils.write(socket, command);
            String message = IOUtils.read(socket);

            if (message.contains("close")) {
                message = message.replace("close", "");
                System.out.println(message);
                break;
            }else if (message.contains("cookie-text")) {
                message = message.replace("cookie-text", "");
                System.out.println("Fortune cookie message: " + message);
            } else{
                System.out.println(message);
            }
        }
        scan.close();
    }
}
