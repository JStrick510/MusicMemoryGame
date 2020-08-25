package musicmemorygame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreboardManager {
    
    private ArrayList<String> listOfLines = new ArrayList<>();
    private ArrayList<String> allSort = new ArrayList<>();
    private ArrayList<String> startSort = new ArrayList<>();
    private ArrayList<String> randSort = new ArrayList<>();
    private String[] types = new String[5];
    private String[] names = new String[5];
    private String[] scores = new String[5];
    private String[] dates = new String[5];
    
    public ScoreboardManager() throws FileNotFoundException, IOException
    {
        try
        {
            File file = new File("scoreboard.txt");
            if(file.createNewFile())
                System.out.println("file created");
            else
            {
                System.out.println("file already exists");
                BufferedReader bufferedReader = new BufferedReader(new FileReader("scoreboard.txt"));
        
                String line = bufferedReader.readLine();
                while(line != null)
                {
                    listOfLines.add(line);
                    line = bufferedReader.readLine();
                }

                bufferedReader.close();
                
                sortScores("");
                System.out.println();
                sortScores("S");
                System.out.println();
                sortScores("R");
                
                getTypes(0);
                getNames(0);
                getScores(0);
                getDates(0);

                //for(String lines : listOfLines)
                    //System.out.println(lines);
            }
            
        }
        catch(IOException e)
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        
    }
    
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
            for(String line : listOfLines)
            {
                String[] temp = line.split("\\s*,\\s*");
                int score = Integer.parseInt(temp[2]);
                for(int j = 0; j < 5; j++)
                {
                    String[] check = allSort.get(j).split("\\s*,\\s*");
                    int checkScore = Integer.parseInt(check[2]);
                    if(score >= checkScore)
                    {
                        allSort.add(j, line);
                        if(allSort.size() > 5)
                            allSort.remove(5);
                        break;
                    }
                }
            }
            
            for(int k = 0; k < 5; k++)
                System.out.println(allSort.get(k));
        }
        
        else if(type.equals("S"))
        {
            for(String line : listOfLines)
            {
                String[] temp = line.split("\\s*,\\s*");
                if(temp[0].equals("S"))
                {
                    int score = Integer.parseInt(temp[2]);
                    for(int j = 0; j < 5; j++)
                    {
                        String[] check = startSort.get(j).split("\\s*,\\s*");
                        int checkScore = Integer.parseInt(check[2]);
                        if(score >= checkScore)
                        {
                            startSort.add(j, line);
                            if(startSort.size() > 5)
                                startSort.remove(5);
                            break;
                        }
                    }
                }
            }
            
            for(int k = 0; k < 5; k++)
                System.out.println(startSort.get(k));
        }
        
        else if(type.equals("R"))
        {
            for(String line : listOfLines)
            {
                String[] temp = line.split("\\s*,\\s*");
                if(temp[0].equals("R"))
                {
                    int score = Integer.parseInt(temp[2]);
                    for(int j = 0; j < 5; j++)
                    {
                        String[] check = randSort.get(j).split("\\s*,\\s*");
                        int checkScore = Integer.parseInt(check[2]);
                        if(score >= checkScore)
                        {
                            randSort.add(j, line);
                            if(randSort.size() > 5)
                                randSort.remove(5);
                            break;
                        }
                    }
                }
            }
            
            for(int k = 0; k < 5; k++)
                System.out.println(randSort.get(k));
        }
    }
    
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
