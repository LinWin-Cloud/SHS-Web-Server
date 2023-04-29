package HttpService;


import main.Main;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.concurrent.*;

class WebServiceServer {

    private String HttpMethod = "GET";
    private static String HttpUrl = "";
    private HttpService httpService;

    public String getHttpUrl()
    {
        return this.HttpUrl;
    }
    public String getHttpMethod()
    {
        return this.HttpMethod;
    }
    public HttpService getHttpService()
    {
        return this.httpService;
    }

    public static void sendFile(String path, int code, PrintWriter printWriter, Socket socket, OutputStream outputStream) throws Exception {

        printWriter.println("HTTP/1.1 "+code+" OK");
        printWriter.println("Content-Type: "+new HttpFileContentType().getType(path));
        printWriter.println("Server: "+ Main.ServerName);
        printWriter.println("Length: "+new File(path).length());
        printWriter.println();
        printWriter.flush();

        FileInputStream fileInputStream = new FileInputStream(path);

        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int length = 0;
        while ((length = channel.read(buffer)) != -1)
        {
            byte[] bytes = buffer.array();
            buffer.clear();
            outputStream.write(bytes);
            outputStream.flush();
        }

        channel.close();
        outputStream.close();
        socket.close();
        fileInputStream.close();
    }
    public static void sendDirectory(String path, int code, PrintWriter printWriter, Socket socket, OutputStream outputStream) throws IOException {
        try
        {
            File file = new File(path);

            /**
             * Index the Index.html or other Index HTML files in the config file.
             * Author : openLinwin
             * v2.3 Version Using the HashMap And Virtual Visit to read the Index File and do not
             * Read the disk.
             */

            int last1 = path.length() - 1 ;
            int end = path.length();
            String charStr = path.substring(last1,end);
            if (!charStr.equals("/"))
            {
                printWriter.println("HTTP/1.1 200 OK");
                printWriter.println("Content-Type: text/html");
                printWriter.println();
                printWriter.println("<script>window.location.href=window.location.href+'/';</script>");
            }

            for (String i : Main.IndexFile)
            {
                File file1 = new File(path+"/"+i);
                if (file1.exists() && file1.isFile())
                {
                    try{
                        sendFile(file1.getAbsolutePath(),200,printWriter,socket,outputStream);
                    }catch (Exception exception)
                    {
                        sendErrorPage(200,printWriter,socket,outputStream);
                    }
                    break;
                }
            }
            /**
             * If there do not have the Index file , then openLinwin will List all the files on this Dir
             */
            File[] ListFiles = file.listFiles();

            printWriter.println("HTTP/1.1 "+code+" OK");
            printWriter.println("Content-Type: text/html");
            printWriter.println("Server: "+Main.ServerName);
            printWriter.println("Length: "+new File(path).length());
            printWriter.println();
            printWriter.flush();

            printWriter.println("<h3>IndexOF: "+path.substring(path.indexOf(Main.HtmlPath)+Main.HtmlPath.length())+"</h3>");
            printWriter.println("<a href='../'>Back</a><br /><br />");
            for (int i = 0 ; i < ListFiles.length ; i ++)
            {
                if (ListFiles[i].isDirectory()) {
                    printWriter.println("<a href='./"+ListFiles[i].getName()+"/'>* Directory -- "+ListFiles[i].getName()+"</a><br />");
                    printWriter.println();
                    printWriter.flush();
                }
            }
            for (int i = 0 ; i < ListFiles.length ; i ++)
            {
                if (ListFiles[i].isFile()) {
                    printWriter.println("<a href='./"+ListFiles[i].getName()+"'>* File -- "+ListFiles[i].getName()+"</a><br />");
                    printWriter.println();
                    printWriter.flush();
                }
            }
            printWriter.println("<div style='width:90%;height:3px;background-color:black></div>'");
            printWriter.flush();
            printWriter.flush();
            socket.close();

        }catch (Exception exception) {
            socket.close();
        }
    }
    public static void sendErrorPage(int code, PrintWriter printWriter, Socket socket, OutputStream outputStream) {
        try
        {
            /**
             * Send Error page , as 400,404,405 and so on.
             * I was using the function: this.snedFile() , but there is a Bug in it.
             * So must write a New function restart.
             */
            printWriter.println("HTTP/1.1 "+code+" OK");
            printWriter.println("Content-Type: text/html");
            printWriter.println("Server: "+Main.ServerName);
            printWriter.println();
            printWriter.flush();

            FileInputStream fileInputStream = new FileInputStream(Main.ERROR_Page+"/"+code+".html");
            int length = 0 ;
            byte[] bytes = new byte[1024];
            while ((length = fileInputStream.read(bytes)) != -1)
            {
                outputStream.write(bytes,0,length);
                outputStream.flush();
            }
            outputStream.close();
            printWriter.close();
            socket.close();
        }
        catch (Exception exception){
            try{
                socket.close();
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
            }
        }
    }
    public static void Service(Socket socket, PrintWriter printWriter, OutputStream outputStream, BufferedReader bufferedReader, String httpUrl) throws Exception {

        HttpUrl = httpUrl;
        try
        {
            String Http = httpUrl;

            if (Http == null) {
                socket.close();
            }
            String path = Main.HtmlPath + Http;
            path = path.replace("//", "/");
            // System.out.println(path+";");

            File RequestsPath = new File(path);
            if (RequestsPath.exists() && RequestsPath.isFile()) {
                sendFile(path, 200, printWriter, socket, outputStream);
            } else if (RequestsPath.exists() && RequestsPath.isDirectory()) {
                sendDirectory(path, 200, printWriter, socket, outputStream);
            } else {
                sendErrorPage(404, printWriter, socket, outputStream);
            }
            bufferedReader.close();
        }
        catch (Exception exception){
        }
    }
}

