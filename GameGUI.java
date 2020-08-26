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

public class GameGUI extends JPanel implements ActionListener {

    private JFrame frame;
    private JButton listen;
    private JButton skipLevel;
    private JButton skipSong;
    private JButton close;
    private JButton guessAction;
    private JLabel points;
    private JLabel time;
    private JLabel guessesLeft;
    private JLabel informUser;
    private JTextField guessAnswer;
    
    private boolean random;
    MusicMemoryGame game;
    private AudioPlayer audioPlayer;
    private long playTime = 1;
    private int guessesRemaining = 3;
    private int pointsScore = 0;
    private long startTime = -1;
    private File[] filesInDirectory;
    private String currentSong;
    private boolean doSave;
    
    public GameGUI(boolean random, File[] filesInDirectory, boolean doSave) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    { 
        this.filesInDirectory = filesInDirectory;
        game = new MusicMemoryGame();
        audioPlayer = game.selectSong(filesInDirectory);
        currentSong = audioPlayer.getSongName();
        this.random = random;
        this.doSave = doSave;
        
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
        
        int i = 7;
        int j = 3;
        JPanel[][] panelHolder = new JPanel[i][j];
        setLayout(new GridLayout(i,j));
        
        for(int m = 0; m < i; m ++)
        {
            for(int n = 0; n < j; n++)
            {
                panelHolder[m][n] = new JPanel();
                add(panelHolder[m][n]);
            }
        }
            
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
        //panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 30, 100));
        panel.setLayout(new GridLayout(7, 3));
        
        for(int x = 0; x < i; x++)
        {
            for(int y = 0; y < j; y++)
                panel.add(panelHolder[x][y]);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Music Memory Game");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == listen)
        {
            try
            {
                startTime = game.listen(random, startTime, playTime, audioPlayer);
            }
            catch(Exception ex)
            {
                informUser.setText("Error with playing sound.");
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == skipLevel)
        {
            if(playTime < 3)
            {
                playTime++;
                time.setText("Listening to " + playTime + " seconds");
                guessesRemaining = 3;
                guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                informUser.setText("You skipped the level");
            }
            else
            {
                informUser.setText("The song was: " + currentSong + ". Moving to next song...");
                playTime = 1;
                time.setText("Listening to " + playTime + " second");
                guessesRemaining = 3;
                guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                startTime = -1;
                //go to next song
                goToNextSong();
            } 
            
        }
        else if(e.getSource() == skipSong)
        {
            informUser.setText("The song was: " + currentSong + ". Moving to next song...");
            playTime = 1;
            time.setText("Listening to " + playTime + " second");
            guessesRemaining = 3;
            guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
            startTime = -1;
            //go to next song
            goToNextSong();
        }
        else if(e.getSource() == close)
        {
            frame.dispose();
            if(doSave)
                new SaveScoreGUI(random, pointsScore);
            else
                new GUI();
        }
        else if(e.getSource() == guessAction)
        {
            String userAnswer = guessAnswer.getText();
            if(userAnswer.equals(currentSong))
            {
                if(playTime == 1)
                {
                    pointsScore+=3;
                    points.setText("Total Points: " + pointsScore);
                    informUser.setText("You are correct. You got 3 points");
                }
                else if(playTime == 2)
                {
                    pointsScore+=2;
                    points.setText("Total Points: " + pointsScore);
                    informUser.setText("You are correct. You got 2 points");                               
                }                            
                else
                {
                    pointsScore+=1;
                    points.setText("Total Points: " + pointsScore);
                    informUser.setText("You are correct. You got 1 points");                               
                }
                playTime = 1;
                time.setText("Listening to " + playTime + " second");
                guessAnswer.setText("");
                //go to next song
                goToNextSong();
            }
            else
            {
                guessesRemaining--;
                guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                informUser.setText("You were incorrect.");
                if(playTime < 3 && guessesRemaining == 0)
                {
                    informUser.setText("You were incorrect. Moving to next level...");
                    playTime++;
                    time.setText("Listening to " + playTime + " seconds");
                    guessesRemaining = 3;
                    guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                }
                else if(playTime == 3 && guessesRemaining == 0)
                {
                    informUser.setText("Incorrect. The song was: " + currentSong);
                    playTime = 1;
                    time.setText("Listening to " + playTime + " second");
                    guessesRemaining = 3;
                    guessesLeft.setText("Guesses Remaining: " + guessesRemaining);
                    startTime = -1;
                    //go to next song
                    goToNextSong();
                }
            }
        }
    }
    
    public void goToNextSong()
    {
        try
        {
            audioPlayer = game.selectSong(filesInDirectory);
        }
        catch(Exception ex)
        {
            informUser.setText("Error with playing sound.");
            ex.printStackTrace();  
        }
        currentSong = audioPlayer.getSongName(); 
    }
    
}
