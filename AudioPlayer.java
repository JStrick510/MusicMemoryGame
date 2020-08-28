package musicmemorygame;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The AudioPlayer class is responsible for anything involved with the audio files.
 * Responsibilities include: playing the song for the right amount of time, playing 
 * the song at the right starting point, selecting the next song, and giving song
 * information.
 * @author Jacob Strickland
 */
public class AudioPlayer {
    
    //used to randomly decide what song to play next
    private Random rand = new Random();
    
    //array the contains the files that are in the directory chosen
    private File[] filesInDirectory;
    
    //to store current position
    private long currentFrame;
    private Clip clip;
    
    //audio stream that allows the audio file to be played
    private AudioInputStream audioInputStream;
    
    //the audio file that is being played
    private File file;
    
    //conversion of seconds to microseconds
    private final long usConv = 1000000;
    
    /**
     * The constructor for the AudioPlayer class.
     * @param file File, the audio file that is being used
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */ 
    public AudioPlayer(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        this.file = file;
        
        audioInputStream = AudioSystem.getAudioInputStream(file);   //create AudioInputStream object
        clip = AudioSystem.getClip();                               //create clip reference
        clip.open(audioInputStream);                                //open audioInputStream to the clip
        
    }
    
    /**
     * Method that gets the title of the audio file.
     * @return String, the name of the audio file
     */ 
    public String getSongName()
    {
        return file.getName().substring(0,file.getName().length()-4);
    }
    
    /**
     * Method that plays the audio file.
     */ 
    public void play()
    {
        clip.start();
    }
    
    /**
     * Method that stops the audio file.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    
    /**
     * Method that jumps to a specific part of the audio file.
     * @param jumpFrame long, the frame of the song to jump to
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void jump(long jumpFrame) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if(jumpFrame > 0 && jumpFrame < clip.getMicrosecondLength())    //if given frame is within the song length
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = jumpFrame;
            clip.setMicrosecondPosition(jumpFrame);
            this.play();
        }
    }
    
    /**
     * Method that resets the audio stream.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(file);
        clip.open(audioInputStream);
    }
    
    /**
     * Method that jumps to a specific part of the audio file.
     * @param seconds long, the amount of seconds the clip will be played for
     * @return startTime, long the startTime of the clip (default -1 since from the start)
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     * @throws InterruptedException
     */
    public long playStartSong(long seconds) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        int jumpInterval = 1000;                                                    //increase listening time by 1000ms (1 sec) each time
        
        if(seconds > clip.getMicrosecondLength()/usConv)                            //check to make sure the max isn't gone over
            seconds = clip.getMicrosecondLength()/usConv;
        play();                                                                     //play the song from the start
        long initTime = System.currentTimeMillis();
        boolean timeElapsed = false;
        while(!timeElapsed)
        {
            if(System.currentTimeMillis() - initTime >= (seconds * jumpInterval))   //check if the system time matches the length of time the song should have played for
                timeElapsed = true;
            else
                Thread.sleep(jumpInterval);                                         //do not check until the interval has elapsed again
        }
        stop();                                                                     //stop playing the audio file after the time has elapsed
        resetAudioStream();
        
        return -1;                                                                  //return the default -1 startTime
    }
    
    /**
     * Method that jumps to a specific part of the audio file.
     * @param seconds long, the amount of seconds the clip will be played for
     * @param startTime long, the frame to start the playing of the clip at
     * @return startTime long, the frame to start the playing of the clip at
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     * @throws InterruptedException
     */
    public long playRandomSong(long seconds, long startTime) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        int jumpInterval = 1000;                                                                //increase listening time by 1000ms (1 sec) each time
        int secondsMax = 3;                                                                     //max time that an audio clip could play for
        
        if(startTime < 0)                                                                       //if startTime is at the deafult -1
            startTime = rand.nextInt((int)(clip.getMicrosecondLength()/usConv)-secondsMax);     //select a start time that allows the max listen time to be reached if selected near the end
        if(seconds > (clip.getMicrosecondLength() - startTime*usConv) /usConv)                  //check to make sure the max isn't gone over
            seconds = clip.getMicrosecondLength() - startTime*usConv;
        jump(startTime*usConv);                                                                 //jump to the startTime to play the audio file
        long initTime = System.currentTimeMillis();
        boolean timeElapsed = false;
        while(!timeElapsed)
        {
            if(System.currentTimeMillis() - initTime >= (seconds * jumpInterval))               //check if the system time matches the length of time the song should have played for
                timeElapsed = true;
            else
                Thread.sleep(jumpInterval);                                                     //do not check until the interval has elapsed again
        }
        stop();                                                                                 //stop playing the audio file after the time has elapsed
        
        return startTime;                                                                       //return the startTime to conserve it for next listening
    }
    
    /**
     * Method that jumps to a specific part of the audio file.
     * @param filesInDirectory File[], the array of files that were in the selected directory
     * @return AudioPlayer, a new audioPlayer with a different random song
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     * @throws InterruptedException
     */
    public AudioPlayer selectSong(File[] filesInDirectory) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        return new AudioPlayer(filesInDirectory[rand.nextInt(filesInDirectory.length)]);    //use a random position in the directory array to choose next audio file
    }
    
}
