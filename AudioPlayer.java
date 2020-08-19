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

        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    //work as the user enters their choice
    public void gotoChoice(int c) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        switch(c)
        {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
            case 5:
                System.out.println("Enter time in seconds (" + 0 + ", " + clip.getMicrosecondLength()/1000000 + ")");
                long c1 = scan.nextLong();
                jump(c1*1000000);
                break;
            case 6:
                playStartSong();
                break;
            case 7:
                playRandomSong();
                break;
        }
    }
    
    //method to play the audio
    public void play()
    {
        //start the clip
        clip.start();
        
        status = "play";
    }
    
    //method to pause the audio
    public void pause()
    {
        if(status.equals("paused"))
        {
            System.out.println("Audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
    
    //method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if(status.equals(("play")))
        {
            System.out.println("Audio is already being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }
    
    //method to restart the audio
    public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
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
    public void playStartSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        System.out.println("Enter how many seconds you would like to listen to: ");
        long seconds = scan.nextLong();
        if(seconds > clip.getMicrosecondLength()/1000000) //check to make sure the max isn't gone over
            seconds = clip.getMicrosecondLength()/1000000;
        play();
        long initTime = System.currentTimeMillis();
        boolean timeElapsed = false;
        while(!timeElapsed)
        {
            if(System.currentTimeMillis() - initTime >= (seconds * 1000))
                timeElapsed = true;
            else
                T.sleep(1000);
        }
        stop();
    }
    
    //method to play from a random point in the song
    public void playRandomSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        System.out.println("Enter how many seconds you would like to listen to: ");
        long seconds = scan.nextLong();
        int randomSelection = rand.nextInt((int)(clip.getMicrosecondLength()/1000000)-(int)seconds);
        long startTime = (long) randomSelection;
        System.out.println("Starting Time: " + startTime);
        if(seconds > (clip.getMicrosecondLength() - startTime*1000000) /1000000) //check to make sure the max isn't gone over
            seconds = clip.getMicrosecondLength() - startTime*1000000;
        jump(startTime*1000000);
        long initTime = System.currentTimeMillis();
        boolean timeElapsed = false;
        while(!timeElapsed)
        {
            if(System.currentTimeMillis() - initTime >= (seconds * 1000))
                timeElapsed = true;
            else
                T.sleep(1000);
        }
        stop();
    }
}
