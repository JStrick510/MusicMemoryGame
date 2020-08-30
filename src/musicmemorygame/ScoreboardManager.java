package musicmemorygame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The ScoreboardManager class is responsible for giving the proper information
 * to ScoreboardGUI to display.
 * Responsibilities include: creating a new scoreboard text file if one doesn't
 * exist, gathering the data from an existing scoreboard text file, sorting the
 * scores by type, and returning the display scoreboard information
 * @author Jacob Strickland
 */
public class ScoreboardManager {
    
    private ArrayList<String> listOfLines = new ArrayList<>();
    private ArrayList<String> allSort = new ArrayList<>();
    private ArrayList<String> startSort = new ArrayList<>();
    private ArrayList<String> randSort = new ArrayList<>();
    private String[] types = new String[5];
    private String[] names = new String[5];
    private String[] scores = new String[5];
    private String[] dates = new String[5];
    
    /**
     * The constructor for the ScorebaordManager.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ScoreboardManager() throws FileNotFoundException, IOException
    {
        try
        {
            File file = new File("scoreboard.txt");
            if(!(file.createNewFile()))     //if there exists a file, get the information from it
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("scoreboard.txt"));
        
                //add all the lines in the file to the arraylist
                String line = bufferedReader.readLine();
                while(line != null)
                {
                    listOfLines.add(line);
                    line = bufferedReader.readLine();
                }

                bufferedReader.close();
            }
            
        }
        catch(IOException e)
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        
    }
    
    /**
     * The method that sorts the scores by type of game played.
     * @param type String, which leaderboard to sort for
     */
    public void sortScores(String type)
    {
        for(int i = 0; i < 5; i++) //initialize as blank
        {
            allSort.add("N, ,0, ");
            startSort.add("N, ,0, ");
            randSort.add("N, ,0, ");
        }
        
        if(type.equals(""))
        {
            for(String line : listOfLines)                              //for all the lines in the scoreboard text file
            {
                String[] temp = line.split("\\s*,\\s*");                //split based on commas
                int score = Integer.parseInt(temp[2]);                  //set the score to the third value
                for(int j = 0; j < 5; j++)                              //iterate through the current top five
                {
                    String[] check = allSort.get(j).split("\\s*,\\s*"); //split the current top five score based on commas
                    int checkScore = Integer.parseInt(check[2]);        //set the comparison score to the third value
                    if(score >= checkScore)                             //if score is greater than the current top 5 score
                    {
                        allSort.add(j, line);                           //add the line to the score arraylist at position
                        if(allSort.size() > 5)                          //if the arraylist is now greater than five (it always will be when a score is added)
                            allSort.remove(5);                          //pop the last element since the scoreboard only displays the top five
                        break;
                    }
                }
            }
        }
        
        else if(type.equals("S"))
        {
            for(String line : listOfLines)                                      //for all the lines in the scoreboard text file
            {
                String[] temp = line.split("\\s*,\\s*");                        //split based on commas
                if(temp[0].equals("S"))                                         //check to see if the type matches the type that is being sorted
                {
                    int score = Integer.parseInt(temp[2]);                      //set the score to the third value
                    for(int j = 0; j < 5; j++)                                  //iterate through the current top five
                    {
                        String[] check = startSort.get(j).split("\\s*,\\s*");   //split the current top five score based on commas
                        int checkScore = Integer.parseInt(check[2]);            //set the comparison score to the third value
                        if(score >= checkScore)                                 //if score is greater than the current top 5 score
                        {
                            startSort.add(j, line);                             //add the line to the score arraylist at position
                            if(startSort.size() > 5)                            //if the arraylist is now greater than five (it always will be when a score is added)
                                startSort.remove(5);                            //pop the last element since the scoreboard only displays the top five
                            break;
                        }
                    }
                }
            }
        }
        
        else if(type.equals("R"))
        {
            for(String line : listOfLines)                                      //for all the lines in the scoreboard text file
            {
                String[] temp = line.split("\\s*,\\s*");                        //split based on commas
                if(temp[0].equals("R"))                                         //check to see if the type matches the type that is being sorted
                {
                    int score = Integer.parseInt(temp[2]);                      //set the score to the third value
                    for(int j = 0; j < 5; j++)                                  //iterate through the current top five
                    {
                        String[] check = randSort.get(j).split("\\s*,\\s*");    //split the current top five score based on commas
                        int checkScore = Integer.parseInt(check[2]);            //set the comparison score to the third value
                        if(score >= checkScore)                                 //if score is greater than the current top 5 score
                        {
                            randSort.add(j, line);                              //add the line to the score arraylist at position
                            if(randSort.size() > 5)                             //if the arraylist is now greater than five (it always will be when a score is added)
                                randSort.remove(5);                             //pop the last element since the scoreboard only displays the top five
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * The method that gets the types in leaderboard order.
     * @param x int, which leaderboard to get info for
     * @return String[], the array of types in order
     */
    public String[] getTypes(int x)
    {
        if(x == 0)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = allSort.get(i).split("\\s*,\\s*");
                types[i] = temp[0];
            }
        }
        
        else if(x == 1)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = startSort.get(i).split("\\s*,\\s*");
                types[i] = temp[0];
            }
        }
        
        if(x == 2)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = randSort.get(i).split("\\s*,\\s*");
                types[i] = temp[0];
            }
        }
        
        return types;
    }
    
    /**
     * The method that gets the names in leaderboard order.
     * @param x int, which leaderboard to get info for
     * @return String[], the array of name in order
     */
    public String[] getNames(int x)
    {
        if(x == 0)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = allSort.get(i).split("\\s*,\\s*");
                names[i] = temp[1];
            }
        }
        
        else if(x == 1)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = startSort.get(i).split("\\s*,\\s*");
                names[i] = temp[1];
            }
        }
        
        else if(x == 2)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = randSort.get(i).split("\\s*,\\s*");
                names[i] = temp[1];
            }
        }
        
        return names;
    }
    
    /**
     * The method that gets the scores in leaderboard order.
     * @param x int, which leaderboard to get info for
     * @return String[], the array of scores in order
     */
    public String[] getScores(int x)
    {
        if(x == 0)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = allSort.get(i).split("\\s*,\\s*");
                scores[i] = temp[2];
            }
        }
        
        else if(x == 1)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = startSort.get(i).split("\\s*,\\s*");
                scores[i] = temp[2];
            }
        }
        
        else if(x == 2)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = randSort.get(i).split("\\s*,\\s*");
                scores[i] = temp[2];
            }
        }
        
        return scores;
    }
    
    /**
     * The method that gets the dates in leaderboard order.
     * @param x int, which leaderboard to get info for
     * @return String[], the array of dates in order
     */
    public String[] getDates(int x)
    {
        if(x == 0)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = allSort.get(i).split(",");
                dates[i] = temp[3];
            }
        }
        
        else if(x == 1)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = startSort.get(i).split(",");
                dates[i] = temp[3];
            }
        }
        
        else if(x == 2)
        {
            for(int i = 0; i < 5; i++)
            {
                String[] temp = randSort.get(i).split(",");
                dates[i] = temp[3];
            }
        }
        
        return dates;
    }
    
}
