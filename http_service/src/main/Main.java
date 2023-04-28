package main;


import HttpService.HttpService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class Main {
    public static int boot_number = 0;
    public static int port = 0;
    public static String IP;
    public static SimpleJson Http_Service_Config = new SimpleJson();
    public static HttpService httpService = new HttpService()

    public static void main(String[] args) {
        LoadConfig(); // load all the config file and project to the jvm
        httpService.run();
    }
    public static void LoadConfig() {
        Http_Service_Config.setFile("../config/http_service.json");
        port = Integer.parseInt(Http_Service_Config.get("port"));
        IP = Http_Service_Config.get("IP");
    }
    public static ServerSocket GetServerSocket() {
        try {
            System.out.println(" [*] LINWINSOFT START HTTP SERVICE: [ "+port+" ]");
            if (boot_number >= 10) {
                System.exit(0);
            }
            ServerSocket serverSocket =
                    new ServerSocket(port, 50, InetAddress.getByName(IP));
            serverSocket.close();
            return serverSocket;
        }
        catch (Exception exception) {
            boot_number += 1;
            return GetServerSocket();
        }
    }
}
