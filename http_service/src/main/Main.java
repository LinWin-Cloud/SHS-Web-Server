package main;


import java.net.ServerSocket;
import java.text.SimpleDateFormat;

public class Main {
    public static int boot_number = 0;
    public static int port = 0;
    public static String IP;
    public static SimpleJson Http_Service_Config = new SimpleJson();

    public static void main(String[] args) {
        ServerSocket serverSocket = Main.GetServerSocket();
    }
    public static void LoadConfig() {
        Http_Service_Config.setFile("../config/http_service.json");
        port = Integer.parseInt(Http_Service_Config.get("port"));
        IP = Http_Service_Config.get("IP");
    }
    public static ServerSocket GetServerSocket() {
        try {

            if (boot_number >= 10) {
                System.exit(0);
            }
        }
        catch (Exception exception) {
            boot_number += 1;
            return GetServerSocket();
        }
    }
}
