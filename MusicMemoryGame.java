package musicmemorygame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;
import javax.swing.JFrame;
import java.util.Random;

public class MusicMemoryGame {
    
    static Random rand = new Random();
    
    static File[] filesInDirectory;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("");
        DirectorySelector panel = new DirectorySelector();
        frame.addWindowListener(
            new WindowAdapter() 
            { 
                public void windowClosing(WindowEvent e) 
                {
                    panel.setVisible(false);
                    filesInDirectory = panel.getFilesInDirectory();
        
                    //for(int i = 0; i < 4; i++)
                        //System.out.println(filesInDirectory[i]);
                    
                    mediaPlayer();
                    System.exit(0);
                }
            }
        );
        
        frame.getContentPane().add(panel, "Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);

    }
    
    public static void mediaPlayer()
    {
        try
        {
            int randomSelection = rand.nextInt(filesInDirectory.length);
            //String filePath = (String)filesInDirectory[randomSelection];
            AudioPlayer audioPlayer = new AudioPlayer(filesInDirectory[randomSelection]);
            
            //audioPlayer.play();
            Scanner scan = new Scanner(System.in);
            
            while(true)
            {
                System.out.println("1. Pause"); 
                System.out.println("2. Resume"); 
                System.out.println("3. Restart"); 
                System.out.println("4. Stop"); 
                System.out.println("5. Jump to specific time"); 
                System.out.println("6. Listen to a song from the start"); 
                System.out.println("7. Listen to a song from a random time"); 
                int c = scan.nextInt(); 
                audioPlayer.gotoChoice(c); 
                if (c == 4) 
                break;
            }
            scan.close();
        }
        
        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    
}
