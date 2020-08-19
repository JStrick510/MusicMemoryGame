package musicmemorygame;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
    
    //to store current position
    Long currentFrame;
    Clip clip;
    
    //current statis of clip
    String status;
    
    AudioInputStream audioInputStream;
    static String filePath;
    
    //constructor to initialize streams and clip
    public AudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        this.filePath = filePath; 
        
        //create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        
        //create clip reference
        clip = AudioSystem.getClip();
        
        //open audioInputStream to the clip
        clip.open(audioInputStream);
        
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    //work as the user enters their choice
    public void gotoChoice(int c) throws UnsupportedAudioFileException, IOException, LineUnavailableException
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
                Scanner scan = new Scanner(System.in);
                long c1 = scan.nextLong();
                jump(c1*1000000);
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
    
    //method to reser audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
