package main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Init_ProxyService {
    public void init(String[] args) {
        ProxyService.simpleJson.setFile("../config/proxy_service.json");
        ProxyService.ProxyUrl = ProxyService.simpleJson.get("proxy");
        ProxyService.ProxyPort = Integer.parseInt(ProxyService.simpleJson.get("port"));

        ProxyService.version = Init_ProxyService.getFileContent("../config/version.md");
    }
    public static String getFileContent(String name)
    {
        try
        {
            File file = new File(name);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            String tmp = "";

            while ((line = bufferedReader.readLine()) != null)
            {
                tmp = tmp + line;
            }
            bufferedReader.close();
            fileReader.close();
            return tmp;
        }
        catch (Exception exception)
        {
            return null;
        }
    }
}
