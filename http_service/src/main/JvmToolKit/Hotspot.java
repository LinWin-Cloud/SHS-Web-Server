package main.JvmToolKit;

import main.Main;

import java.net.HttpURLConnection;
import java.net.URL;

public class Hotspot {
    public void NetWork_Hotspot() {
        /**
         * In the first , jvm cannot build the local machine code.
         * So we must do something that the java vm build the local machine code.
         * After the local machine code is compiled , the java applications will run faster.
         * [Notice] this function only use by network.
         */
        try {
            URL url = new URL("http://localhost:"+ Main.port);
            for (int i = 0 ; i < 100 ; i++)
            {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                httpURLConnection.disconnect();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
