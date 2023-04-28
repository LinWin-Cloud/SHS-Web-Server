package main;


import HttpService.HttpService;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static int boot_number = 0;
    public static int port = 0;
    public static String IP;
    public static SimpleJson Http_Service_Config = new SimpleJson();
    public static HttpService httpService = new HttpService();
    public static ExecutorService executorService = Executors.newFixedThreadPool(10000);
    public static HashSet<String> IndexFile = new HashSet<>();

    public static void main(String[] args) {
        LoadConfig(); // load all the config file and project to the jvm
        httpService.run();
    }
    public static void LoadConfig() {
        Http_Service_Config.setFile("../config/http_service.json");
        port = Integer.parseInt(Http_Service_Config.get("port"));
        IP = Http_Service_Config.get("IP");

        String[] Index = Http_Service_Config.get("default").split(",");
        IndexFile.addAll(Arrays.asList(Index));
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
