package HttpService;

import main.Main;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class WebServer {
    private static int EXE_Boot = 0;

    public static int Start_Test = 0;
    public String httpUrl = null;
    public String httpMethod = "GET";
    public HttpService httpService = null;

    public Socket socket = null;
    public OutputStream outputStream = null;
    public String getHttpUrl() {
        return this.httpUrl;
    }
    public String getHttpMethod() {
        return this.httpMethod;
    }
    public HttpService getHttpService() {
        return this.httpService;
    }
    public Socket getSocket()
    {
        return this.socket;
    }
    public OutputStream getOutputStream() {
        return this.outputStream;
    }


    public void set(Socket socket,OutputStream outputStream,String HttpUrl,String HttpMethod) {
        this.socket = socket;
        this.outputStream = outputStream;
        this.httpUrl = HttpUrl;
        this.httpMethod = HttpMethod;
    }

    public void mainWebServer() throws Exception
    {
        WebServer.getServerSocket(Main.port);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(Main.IP,Main.port));
        System.out.println(" [INFO] START WEB SERVER ["+Main.port+"]");

        for (int i = 0 ; i < 5 ; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                    {
                        try {
                            Socket socket = serverSocket.accept();
                            Future<Integer> future = Main.executorService.submit(new Callable<Integer>() {
                                @Override
                                public Integer call(){
                                    runEXE(socket);
                                    return 0;
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            thread.start();
        }
    }
    public static void runEXE(Socket socket) {
        try {

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            PrintWriter printWriter = new PrintWriter(outputStream);

            ArrayList<String> HttpTitle = new ArrayList<>();

            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    socket.close();
                    return;
                }
                if (line.equals("") || line.equals(" ")) {
                    break;
                }
                HttpTitle.add(line);
            }

            try {
                String httpUrl = HttpTitle.get(0);
                httpUrl = java.net.URLDecoder.decode(httpUrl,"UTF-8").trim();

                String httpMethod = httpUrl.substring(0,httpUrl.indexOf(" ")).toLowerCase();
                httpUrl = httpUrl.substring(httpUrl.indexOf(" ")+1,httpUrl.lastIndexOf("HTTP/")-1);

                if (httpMethod.equals("get")) {
                    WebServer.FutureEXE(socket,printWriter,outputStream,bufferedReader,httpUrl);
                }
            }catch (Exception exception) {
                WebServiceServer webServiceServer = new WebServiceServer();
                webServiceServer.sendFile(Main.ERROR_Page+"/400.html",400,printWriter,socket,outputStream);
            }
        }
        catch (Exception exception) {
        }
    }
    public static void FutureEXE(
            Socket socket,
            PrintWriter printWriter,
            OutputStream outputStream,
            BufferedReader bufferedReader,
            String httpUrl)
            throws Exception {
        WebServiceServer webServiceServer = new WebServiceServer();
        webServiceServer.Service(socket,printWriter,outputStream,bufferedReader,httpUrl);
    }
    public static boolean getServerSocket(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
        }
        catch (Exception exception) {
            WebServer.Start_Test = WebServer.Start_Test + 1;
            try {
                if (WebServer.Start_Test == 5) {
                    System.exit(0);
                }
                Thread.sleep(200);
                WebServer.getServerSocket(port);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}