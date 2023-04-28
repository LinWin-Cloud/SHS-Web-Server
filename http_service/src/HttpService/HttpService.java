package HttpService;

import main.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.net.URLDecoder;

public class HttpService {
    private PrintWriter printWriter;
    private InputStream inputStream;
    private BufferedOutputStream bufferedOutputStream;

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
            this.bufferedOutputStream = new BufferedOutputStream(outputStream);

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
            String Method =
                    requests.substring(0,requests.indexOf(" ")).toLowerCase();
            String Url =
                    requests.substring(requests.indexOf(" ")+1,requests.lastIndexOf("HTTP/")-1);
            if (Method.equals("get"))
            {
                if (Url.equals("/"))
                {
                    boolean isIndex = false;
                    for (String index : Main.IndexFile)
                    {
                        File index_file = new File(Main.HtmlPath+"/"+index);
                        if (index_file.exists() && index_file.isFile())
                        {
                            isIndex = true;

                        }
                    }
                    if (!isIndex) {

                    }
                }
            }
            if (Method.equals("post"))
            {

            }
            else {
                this.SendErrTitle(printWriter,socket);
                return;
            }
            socket.close();
        }
        catch (Exception exception)
        {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void SendErrTitle(PrintWriter printWriter,Socket socket) throws Exception {
        printWriter.println("HTTP/1.1 400 OK");
        printWriter.println("Content-Type: text/html");
        printWriter.println("Server: "+Main.ServerName);
        printWriter.println();
        printWriter.flush();
        this.sendFile("../default/error/400.html",socket);
    }
    private void sendFile(String url, Socket socket) throws Exception {
        FileChannel channel = FileChannel.open(Paths.get(url), StandardOpenOption.READ);
        ByteBuffer buf = ByteBuffer.allocate(5);
        while(channel.read(buf)!=-1){
            buf.flip();
            this.bufferedOutputStream.write(buf.array());
            this.bufferedOutputStream.flush();
            buf.clear();
        }
        channel.close();
        socket.close();
    }
}
