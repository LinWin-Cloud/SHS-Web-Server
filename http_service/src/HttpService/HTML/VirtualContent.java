package HttpService.HTML;

import main.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class VirtualContent {
    public HashMap<String,String> VirtualContent = new HashMap<>();

    public void load(File Dir)  {
        String root = Dir.getAbsolutePath().replace("\\","/") + "/";
        root = root.replace("//","/");

        try {
            if (root.equals(Main.HtmlPath)) {
                for (String i : Main.IndexFile)
                {
                    File file1 = new File(root+"/"+i);
                    if (file1.exists() && file1.isFile())
                    {
                        String root_path = "/";
                        System.out.println(root_path);
                        this.VirtualContent.put(root_path,this.GetFileContent(file1.getAbsolutePath()));
                        break;
                    }
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        for (File file : Objects.requireNonNull(Dir.listFiles()))
        {
            if (file.isDirectory())
            {
                load(new File(Dir.getAbsolutePath()+"/"+file.getName()));
            }
            else {
                try
                {
                    String name = file.getName();
                    String last_name = name.substring(name.lastIndexOf(".")).trim();
                    if (
                            last_name.equals(".htm")
                            || last_name.equals(".html")
                            || last_name.equals(".css")
                            || last_name.equals(".js")
                            || last_name.equals(".txt"))
                    {
                        this.PutRootPath(file);
                    }
                }
                catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    continue;
                }
            }
        }
    }
    public String GetFileContent(String path) throws Exception
    {
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        while (true)
        {
            String line = bufferedReader.readLine();
            if (line == null)
            {
                break;
            }
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        bufferedReader.close();
        fileReader.close();
        return stringBuilder.toString();
    }
    public void PutRootPath(File file) throws Exception {
        String root_path = "/"+file.getAbsolutePath().substring(Main.HtmlPath.length());
        root_path = root_path.replace("//","/");
        System.out.println(root_path);
        this.VirtualContent.put(root_path,this.GetFileContent(file.getAbsolutePath()));

    }
}
