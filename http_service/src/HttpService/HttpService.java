package HttpService;

import main.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpService {
    public void run() {
        ServerSocket serverSocket = Main.GetServerSocket();

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();

            } catch (IOException e) {
                try
                {
                    socket.close();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
