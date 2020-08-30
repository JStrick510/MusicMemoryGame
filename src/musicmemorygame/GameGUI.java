package musicmemorygame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The GameGUI class is responsible for displaying the information while the
 * user is playing the game.
 * Responsibilities include: displaying info like guesses remaining and points,
 * allowing the user to guess, listen to the clip, skip the level/song,
 * allowing the user to go back to the main menu.
 * @author Jacob Strickland
 */
public class GameGUI extends JPanel implements ActionListener {

    private JFrame frame;
    private JButton listen;         //button to listen to the audio clip
    private JButton skipLevel;      //button to skip a level
    private JButton skipSong;       //button to skip a song
    private JButton close;          //button to close the GUI
    private JButton guessAction;    //button to guess if entered title was correct
    private JLabel points;          //display the points
    private JLabel time;            //display the amount of time the clip will play for
    private JLabel guessesLeft;     //display the number of guesses remaining
    private JLabel informUser;      //display info like if guess was correct
    private JTextField guessAnswer; //text field where user enters guess
    
    private boolean random;             //flag if the clip is playing from random starting time
    private AudioPlayer audioPlayer;    //audio player that controls anything related to the audio file
    private long playTime = 1;          //amoount of time the song is playing for currently
    private int guessesRemaining = 3;   //number of guesses remaining at current level
    private int pointsScore = 0;        //points the user has accumulated
    private long startTime = -1;        //starting time of the clip to conserve for when playing random (-1 plays from start)
    private File[] filesInDirectory;    //array of files that were in the user selected directory
    private String currentSong;         //the title of the current audio file that is playing
    private boolean doSave;             //flag if the score will be saved at the end of the game
    
    /**
     * The constructor for the GameGUI class.
     * @param random boolean, the flag if the audio files start from a random point
     * @param filesInDirectory file[], the array of the files that were in the selected directory
     * @param doSave boolean, flag if the mode was selected to save scores on close
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     * @throws InterruptedException
     */
    public GameGUI(boolean random, File[] filesInDirectory, boolean doSave) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    { 
        //set all the variables from the parameters
        this.filesInDirectory = filesInDirectory;
        audioPlayer = new AudioPlayer(filesInDirectory[0]);
        audioPlayer = audioPlayer.selectSong(filesInDirectory);
        currentSong = audioPlayer.getSongName();
        this.random = random;
        this.doSave = doSave;
        
        //initizaling the GUI elements with text and action listeners
        frame = new JFrame();
        listen = new JButton("Listen");
        listen.addActionListener(this);
        skipLevel = new JButton("Skip Level");
        skipLevel.addActionListener(this);
        skipSong = new JButton("Skip Song");
        skipSong.addActionListener(this);
        close = new JButton("Close");
        close.addActionListener(this);
        guessAction = new JButton("Guess");
        guessAction.addActionListener(this);
        points = new JLabel("Total Points: " + pointsScore);
        time = new JLabel("Listening to " + playTime + " second");
        guessesLeft = new JLabel("Guesses Remaining: " + guessesRemaining);
        informUser = new JLabel("");
        guessAnswer = new JTextField(30);
        guessAnswer.setBounds(50, 150, 200, 30);
        
        //setting the panel parameters and adding the elements
        int i = 7;
        int j = 3;
        JPanel[][] panelHolder = new JPanel[i][j];
        setLayout(new GridLayout(i,j));
        
        for(int m = 0; m < i; m ++) //initialize a 2D array of blank panels
        {
            for(int n = 0; n < j; n++)
            {
                panelHolder[m][n] = new JPanel();
                add(panelHolder[m][n]);
            }
        }
        
        //add elements that aren't blank at specific coordinates
        panelHolder[0][0].add(points);
        panelHolder[1][0].add(time);
        panelHolder[2][0].add(guessesLeft);
        panelHolder[0][2].add(listen);
        panelHolder[1][2].add(skipLevel);
        panelHolder[2][2].add(skipSong);
        panelHolder[3][2].add(close);
        panelHolder[4][1].add(informUser);
        panelHolder[5][1].add(guessAction);
        panelHolder[6][1].add(guessAnswer);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 3));
        
        for(int x = 0; x < i; x++)  //add the panels stored in the 2D array to the panel in order
        {
            for(int y = 0; y < j; y++)
                panel.add(panelHolder[x][y]);
        }

        //setting the frame information
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Music Memory Game");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
    

    @Override
    /**
     * The method that makes things happen when the buttons are pressed.
     * @param e ActionEvent, a button that was pressed
     */
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == listen) //button is pressed to listen to the audio clip
        {
            try
            {
                if(!random)         //play based on the right mode, conserve startTime
                    startTime = audioPlayer.playStartSong(playTime);
                else
                    startTime = audioPlayer.playRandomSong(playTime, startTime);
            }
            catch(Exception ex)
            {
                informUser.setText("Error with playing sound.");
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == skipLevel) //button is pressed to skip the level (more time)
        {
            if(playTime < 3)                //if play time is less than the max
            {
                playTime++;                 //increase the amount of time the audio clip will play for
                guessesRemaining = 3;       //reset the guesses
                
                //update the GUI
                time.setText("Listening to " + playTime + " seconds");
                guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                informUser.setText("You skipped the level");
            }
            else                            //increasing the play time would go above the max possible play time in the game
            {
                
                playTime = 1;               //reset the play time of the audio clip for the next song
                guessesRemaining = 3;       //reset the guesses
                startTime = -1;             //reset the start time to default
                
                //update the GUI
                informUser.setText("The song was: " + currentSong + ". Moving to next song...");
                time.setText("Listening to " + playTime + " second");
                guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                
                goToNextSong();
            } 
            
        }
        else if(e.getSource() == skipSong)  //button is pressed to skip the song
        {
            playTime = 1;                   //reset the play time of the audio clip for the next song
            guessesRemaining = 3;           //reset the guesses
            startTime = -1;                 //reset the start time to default
            
            //update the GUI
            informUser.setText("The song was: " + currentSong + ". Moving to next song...");
            time.setText("Listening to " + playTime + " second");
            guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
            
            goToNextSong();
        }
        else if(e.getSource() == close)                 //if the button is pressed to close the GUI
        {
            frame.dispose();                            //get rid of the current GUI
            if(doSave)                                  //if the challenge mode was played
                new SaveScoreGUI(random, pointsScore);  //open the GUI to prompt for name
            else
                new GUI();                              //go back to the main menu
        }
        else if(e.getSource() == guessAction)           //if the button is pressed to guess
        {
            String userAnswer = guessAnswer.getText();  //get what the user has typed in from the text field
            if(userAnswer.equals(currentSong))          //if the text field matches the title of the song
            {
                if(playTime == 1)                       //if the audio clip was playing for one second
                {
                    pointsScore+=3;                     //award the user max points
                    //update GUI
                    points.setText("Total Points: " + pointsScore);
                    informUser.setText("You are correct. You got 3 points");
                }
                else if(playTime == 2)                  //if the audio clip was playing for two seconds
                {
                    pointsScore+=2;                     //award the user less points
                    //update GUI
                    points.setText("Total Points: " + pointsScore);
                    informUser.setText("You are correct. You got 2 points");                               
                }                            
                else                                    //if the audio clip was playing for three seconds
                {
                    pointsScore+=1;                     //award the user least points
                    //update GUI
                    points.setText("Total Points: " + pointsScore);
                    informUser.setText("You are correct. You got 1 points");                               
                }
                playTime = 1;                           //reset the play time of the clip back to one
                
                //update GUI
                time.setText("Listening to " + playTime + " second");
                guessAnswer.setText("");
                
                goToNextSong();
            }
            else
            {
                guessesRemaining--;                             //reduce guesses remaing by one since incorrect
                //update GUI
                guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                informUser.setText("You were incorrect.");
                
                if(playTime < 3 && guessesRemaining == 0)       //if the time of listen to was less than max and no more guesses left
                {
                    playTime++;                                 //increase the listening time of the clip by one
                    guessesRemaining = 3;                       //reset the guesses left at level to three
                    
                    //update GUI
                    informUser.setText("You were incorrect. Moving to next level...");
                    time.setText("Listening to " + playTime + " seconds");
                    guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                }
                else if(playTime == 3 && guessesRemaining == 0) //if no more levels left and no more guesses left
                {
                    playTime = 1;               //reset the play time of the audio clip for the next song
                    guessesRemaining = 3;       //reset the guesses
                    startTime = -1;             //reset the start time to default 
                    
                    //update GUI
                    informUser.setText("Incorrect. The song was: " + currentSong);
                    time.setText("Listening to " + playTime + " second");
                    guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                    
                    goToNextSong();
                }
            }
        }
    }
    
    /**
     * The method that selects the next audio file for the game.
     */
    public void goToNextSong()
    {
        try
        {
            audioPlayer = audioPlayer.selectSong(filesInDirectory);     //get a new audio file
        }
        catch(Exception ex)
        {
            informUser.setText("Error with playing sound.");
            ex.printStackTrace();  
        }
        
        currentSong = audioPlayer.getSongName();                        //get the name of new audio file
    }
    
}
