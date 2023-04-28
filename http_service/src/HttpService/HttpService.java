package HttpService;

import main.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

public class HttpService {
    private PrintWriter printWriter;
    private InputStream inputStream;

    public void run() {
        ServerSocket serverSocket = Main.GetServerSocket();

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                Socket finalSocket = socket;
                Main.executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        http_client_service(finalSocket);
                        return 0;
                    }
                });
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
    private void http_client_service(Socket socket) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            this.printWriter = new PrintWriter(outputStream);
            this.inputStream = socket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            String url = bufferedReader.readLine();


        }
        catch (Exception exception) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
