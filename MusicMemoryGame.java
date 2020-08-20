package musicmemorygame;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicMemoryGame {
    
    static Random rand = new Random();
    static Scanner scan = new Scanner(System.in);
    
    static File[] filesInDirectory;
    
    public AudioPlayer selectSong(File[] filesInDirectory) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        int randomSelection = rand.nextInt(filesInDirectory.length);
        //String filePath = (String)filesInDirectory[randomSelection];
        return new AudioPlayer(filesInDirectory[randomSelection]);
    }
    
    public long listen(boolean random, long startTime, long playTime, AudioPlayer audioPlayer) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        if(!random)
        {
            audioPlayer.playStartSong(playTime);
            return -1;
        }
        else
            return audioPlayer.playRandomSong(playTime, startTime);
        
    }
    
}
