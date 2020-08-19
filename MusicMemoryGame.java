package musicmemorygame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;
import javax.swing.JFrame;
import java.util.Random;

public class MusicMemoryGame {
    
    static Random rand = new Random();
    static Scanner scan = new Scanner(System.in);
    
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
                    frame.setVisible(false);
                    filesInDirectory = panel.getFilesInDirectory();
        
                    //for(int i = 0; i < 4; i++)
                        //System.out.println(filesInDirectory[i]);
                    
                    mainMenu();
                    System.exit(0);
                }
            }
        );
        
        frame.getContentPane().add(panel, "Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);

    }
    
    public static void mainMenu()
    {
        while(true)
        {
            System.out.println("Welcome to The Music Memory Game");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Please choose which mode you would like to play:");
            System.out.println("1. Quiz on songs from the start"); 
            System.out.println("2. Quiz on songs from a random point"); 
            System.out.println("3. Exit"); 
            int c = scan.nextInt();
            if (c == 3) 
                break;
            if(c == 1 || c == 2)
                secondaryMenu(c);
        }
    }
    
    public static void secondaryMenu(int choice)
    {
        try
        {
            int randomSelection = rand.nextInt(filesInDirectory.length);
            //String filePath = (String)filesInDirectory[randomSelection];
            AudioPlayer audioPlayer = new AudioPlayer(filesInDirectory[randomSelection]);
            
            //System.out.println(filesInDirectory[randomSelection].getName().substring(0,filesInDirectory[randomSelection].getName().length()-4));
            
            //audioPlayer.play();
            Scanner scan = new Scanner(System.in);
            
            long time = 1;
            int guesses = 3;
            int points = 0;
            long startTime = -1;
            
            while(true)
            {
                System.out.println("You are at " + points + " point(s)");
                System.out.println("You are currently listening to " + time + " second(s) of the song.");
                System.out.println("You have " + guesses + " left at this level.");
                System.out.println("1. Listen");
                System.out.println("2. Guess");
                System.out.println("3. Skip level");
                System.out.println("4. Skip song");
                System.out.println("5. Exit"); 
                int c = scan.nextInt(); 
                if (c == 5) 
                    break;
                
                switch(c)
                {
                    case 1:
                        if(choice == 1)
                            audioPlayer.playStartSong(time);
                        else
                            startTime = audioPlayer.playRandomSong(time, startTime);
                        break;
                    case 2:
                        System.out.print("Enter your guess: ");
                        String guess = scan.nextLine();
                        guess = scan.nextLine();
                        if(guess.equals(filesInDirectory[randomSelection].getName().substring(0,filesInDirectory[randomSelection].getName().length()-4)))
                        {
                            System.out.println("You are correct");
                            if(time == 1)
                            {
                                points+=3;
                                System.out.println("You got 3 points. Total points: " + points);                                
                            }
                            else if(time == 2)
                            {
                                points+=2;
                                System.out.println("You got 2 points. Total points: " + points);                                
                            }                            
                            else
                            {
                                points+=1;
                                System.out.println("You got 1 points. Total points: " + points);                                
                            }
                            time = 1;
                            //go to next song
                            randomSelection = rand.nextInt(filesInDirectory.length);
                            audioPlayer = new AudioPlayer(filesInDirectory[randomSelection]);
                        }
                        else
                        {
                            guesses--;
                            System.out.println("You were incorrect. Guesses remaining: " + guesses);
                            if(time < 3 && guesses == 0)
                            {
                                System.out.println("Moving to next level.");
                                time++;
                                guesses = 3;
                            }
                            else if(time == 3 && guesses == 0)
                            {
                                System.out.println("You were unable to guess the song, the song was: " + filesInDirectory[randomSelection].getName().substring(0,filesInDirectory[randomSelection].getName().length()-4));
                                time = 1;
                                guesses = 3;
                                startTime = -1;
                            }
                        }
                        break;
                    case 3:
                        if(time < 3)
                        {
                            time++;
                            guesses = 3;
                            System.out.println("You skipped the level");
                        }
                        else
                        {
                            System.out.print("You skipped the song. ");
                            System.out.println("The song was: " + filesInDirectory[randomSelection].getName().substring(0,filesInDirectory[randomSelection].getName().length()-4));
                            System.out.println("Moving to next song...");
                            time = 1;
                            guesses = 3;
                            startTime = -1;
                            //go to next song
                            randomSelection = rand.nextInt(filesInDirectory.length);
                            audioPlayer = new AudioPlayer(filesInDirectory[randomSelection]);
                        }   
                        break;
                    case 4:
                        System.out.print("You skipped the song. ");
                        System.out.println("The song was: " + filesInDirectory[randomSelection].getName().substring(0,filesInDirectory[randomSelection].getName().length()-4));
                        System.out.println("Moving to next song...");
                        time = 1;
                        guesses = 3;
                        startTime = -1;
                        //go to next song
                        randomSelection = rand.nextInt(filesInDirectory.length);
                        audioPlayer = new AudioPlayer(filesInDirectory[randomSelection]);
                        break;
                }
                //audioPlayer.gotoChoice(c); 
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
