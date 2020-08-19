package musicmemorygame;

import java.util.Scanner;

public class MusicMemoryGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try
        {
            String filePath = "D:\\Coding Projects\\WAV music\\Classroom of the Elite OP.wav";
            AudioPlayer audioPlayer = new AudioPlayer(filePath);
            
            audioPlayer.play();
            Scanner scan = new Scanner(System.in);
            
            while(true)
            {
                System.out.println("1. Pause"); 
                System.out.println("2. Resume"); 
                System.out.println("3. Restart"); 
                System.out.println("4. Stop"); 
                System.out.println("5. Jump to specific time"); 
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
