package musicmemorygame;

import java.io.File;
import java.io.IOException;

public class ScoreboardManager {
    
    public void createFile()
    {
        try
        {
            File file = new File("scoreboard.txt");
            if(file.createNewFile())
                System.out.println("file created");
            else
                System.out.println("file already exists");
        }
        catch(IOException e)
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
    
}
