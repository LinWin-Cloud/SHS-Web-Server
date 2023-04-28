package HttpService;

import main.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.net.URLDecoder;

public class HttpService {
    private PrintWriter printWriter;
    private InputStream inputStream;

    public void run() {
        ServerSocket serverSocket = Main.GetServerSocket();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Main.executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() {
                        http_client_service(socket);
                        return 0;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                continue;
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
            ArrayList<String> client_title = new ArrayList<>();
            while (true) {
                String message = bufferedReader.readLine();
                if (message == null || message.equals(" ") || message.equals("")) {
                    break;
                }
                client_title.add(URLDecoder.decode(message,"UTF-8"));
            }
            String requests = client_title.get(0);
            String Method = requests.substring(0,requests.indexOf(" "));
            String Url = requests.substring(requests.indexOf(" ")+1,requests.lastIndexOf("HTTP/")-1);
            printWriter.println("hello world");
            printWriter.flush();

            socket.close();
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
