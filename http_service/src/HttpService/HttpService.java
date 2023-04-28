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
import java.util.Objects;
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
            Url = Url.replaceAll("//","/");

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
                            this.sendFile(Main.HtmlPath+"/"+index_file.getName(),socket,200);
                            break;
                        }
                    }
                    if (!isIndex) {
                        this.SendListOfDir(printWriter,socket,"/");
                        return;
                    }
                    return;
                }
                else {
                    this.SendListOfDir(printWriter,socket,Url);
                    return;
                }
            }
            if (Method.equals("post"))
            {
                System.out.println(2);
            }
            else {
                System.out.println(1);
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
    public void SendListOfDir(PrintWriter printWriter,Socket socket,String url)
    throws Exception{
        File file = new File(Main.HtmlPath+"/"+url);
        if (file.isDirectory()) {
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type: text/html");
            printWriter.println("Server: "+Main.ServerName);
            printWriter.println();
            printWriter.flush();
            printWriter.println("<h1>IndexOf: "+url+"</h1>");
            printWriter.println("<a href='../'>Back</a><br />");
            for (File file_project: Objects.requireNonNull(file.listFiles())) {
                if (file_project.isDirectory()) {
                    printWriter.println("<a href='"+file_project.getName()+"/'><f style='color: black'>Directory: <f>"+file_project.getName()+"</a><br />");
                    printWriter.flush();
                }
                else {
                    printWriter.println("<a href='"+file_project.getName()+"'><f style='color: black'>File: <f>"+file_project.getName()+"</a><br />");
                }
            }
            printWriter.flush();
            socket.close();
        }
        else {
            this.sendFile("../default/error/404.html",socket,404);
        }
    }
    public void SendErrTitle(PrintWriter printWriter,Socket socket) throws Exception {
        printWriter.println("HTTP/1.1 400 OK");
        printWriter.println("Content-Type: text/html");
        printWriter.println("Server: "+Main.ServerName);
        printWriter.println();
        printWriter.flush();
        this.sendFile("../default/error/400.html",socket,400);
    }
    private void sendFile(String url, Socket socket,int code) throws Exception {
        printWriter.println("HTTP/1.1 "+String.valueOf(code)+" OK");
        printWriter.println("Content-Type: text/html");
        printWriter.println("Server: "+Main.ServerName);
        printWriter.println();
        printWriter.flush();

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
