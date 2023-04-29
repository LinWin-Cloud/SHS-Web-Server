package HttpService;

import main.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
        ServerSocket serverSocket = new ServerSocket(Main.port);

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
                                public Integer call() throws Exception {
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

            String httpUrl = bufferedReader.readLine();
            if (httpUrl == null){
                socket.close();
            }
            httpUrl = java.net.URLDecoder.decode(httpUrl,"UTF-8");

            String httpMethod = httpUrl.substring(0,httpUrl.indexOf(" "));
            httpUrl = httpUrl.substring(httpUrl.indexOf(" ")+1,httpUrl.lastIndexOf("HTTP/")-1);

            HttpService httpService = new HttpService();
            //WebServer webServer = new WebServer();
            //webServer.set(socket,outputStream,httpUrl,httpMethod);

            WebServer.FutureEXE(socket,printWriter,outputStream,bufferedReader,httpUrl);
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
        WebServiceServer.Service(socket,printWriter,outputStream,bufferedReader,httpUrl);
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