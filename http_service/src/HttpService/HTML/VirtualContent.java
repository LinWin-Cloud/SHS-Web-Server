package HttpService.HTML;

import main.Main;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class VirtualContent {
    public HashMap<String,String> VirtualContent = new HashMap<>();

    public void load(File Dir) {
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
                }
                catch (Exception exception) {
                    continue;
                }
            }
        }
    }
}
