package musicmemorygame;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.lang.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
    
    Scanner scan = new Scanner(System.in);
    Random rand = new Random();
    Thread T;
    
    //to store current position
    Long currentFrame;
    Clip clip;
    
    //current statis of clip
    String status;
    
    AudioInputStream audioInputStream;
    static String filePath;
    static File file;
    
    //constructor to initialize streams and clip
    public AudioPlayer(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        //this.filePath = filePath; 
        this.file = file;
        
        //create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(file);
        
        //create clip reference
        clip = AudioSystem.getClip();
        
        //open audioInputStream to the clip
        clip.open(audioInputStream);
        
        //default status is paused at start
        status = "paused";
    }
    
    public String getSongName()
    {
        System.out.println(file.getName());
        return file.getName().substring(0,file.getName().length()-4);
    }
    
    //method to play the audio
    public void play()
    {
        //start the clip
        clip.start();
        
        status = "play";
    }
    
    //method to stop the audio
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    
    //method to jump over a specific part
    public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if( c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }
    
    //method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(file);
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    //method to play from the start of the song
    public void playStartSong(long seconds) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        if(seconds > clip.getMicrosecondLength()/1000000) //check to make sure the max isn't gone over
            seconds = clip.getMicrosecondLength()/1000000;
        play();
        long initTime = System.currentTimeMillis();
        boolean timeElapsed = false;
        while(!timeElapsed)
        {
            if(System.currentTimeMillis() - initTime >= (seconds * 1000)) //check every second if the time has been elapsed
                timeElapsed = true;
            else
                T.sleep(1000);
        }
        stop();
    }
    
    //method to play from a random point in the song
    public long playRandomSong(long seconds, long startTime) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        if(startTime < 0)
            startTime = rand.nextInt((int)(clip.getMicrosecondLength()/1000000)-3); //3 seconds because thats the longest a sample can be
        //System.out.println("Starting Time: " + startTime);
        if(seconds > (clip.getMicrosecondLength() - startTime*1000000) /1000000) //check to make sure the max isn't gone over
            seconds = clip.getMicrosecondLength() - startTime*1000000;
        jump(startTime*1000000);
        long initTime = System.currentTimeMillis();
        boolean timeElapsed = false;
        while(!timeElapsed)
        {
            if(System.currentTimeMillis() - initTime >= (seconds * 1000)) //check every second if the time has been elapsed
                timeElapsed = true;
            else
                T.sleep(1000);
        }
        stop();
        
        return startTime;
    }
}
