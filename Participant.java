import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Write a description of class Participant here.
 *
 * Haki Shyntassov (your name)
 * @version (a version number or a date)
 */
public class Participant
{
    // instance variables - replace the example below with your own
    private List<String> fileNames;
    private String directory;
    
    private String id;
    private int type; // 1 - typical, 2 - atypical
    private int gender; // 0 - female, 1 - male
    private float age;

    private float[][][] xPos; // [maze_id][trial_id][xPos_id]
    private float[][][] yPos; // [maze_id][trial_id][yPos_id]
    private float[][][] time;
    
    private float[][][] vector_x; // x for vectors [maze_id][trial_id][vector_x_id]
    private float[][][] vector_y; // y for vectors [maze_id][trial_id][vector_y_id]
    private double[][][] vector_a; // angles for vectors [maze_id][trial_id][vector_a_id]
    
    // average vector data for each trial of each maze
    private float[][] vector_x_av_t;
    private float[][] vector_y_av_t;
    private double[][] vector_a_av_t;
    
    // average vector data for each maze
    private float[] vector_x_av;
    private float[] vector_y_av;
    private double[] vector_a_av;
    
    private int[][][][] grid; // positions [maze_id][trial][x][y] = 1 (present) or 0 (absent)
    
    // maze_id: 0, 1, 2, 3, 4, 5
    // trial_id: 0, 1, 2, 3, 4, 5
    // xPos_id and yPos_id: [0 ... n_lines]

    /**
     * Constructor for objects of class Participant
     */
    public Participant(String id, List<String> fileNames, String directory)
    {
        // initialise instance variables
        this.id = id;      
        this.fileNames = fileNames;
        this.directory = directory;
        //getInfo(); // call it to get gender, age info
        
        xPos = new float[4][6][15500];
        yPos = new float[4][6][15500];
        time = new float[4][6][15500];
        //System.out.println(id);
        loadRawData(); // call it when participant is initialized
        
        grid = new int[4][6][24][24];
        loadGrid();
    }

    public void loadGrid()
    {
        int countX = 0;
        int countY = 0;
        int count = 1; // tracks the order
        ArrayList<Integer> listX = new ArrayList<Integer>();        
        ArrayList<Integer> listY = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++){
            count = 1;
            for(int j = 0; j < 6; j++){
                count = 1;
                for(int k = 0; k < 15500; k++){
                    if(xPos[i][j][k] != 0 && yPos[i][j][k] != 0){
                        countX = Math.abs((int) (xPos[i][j][k]) / 64);
                        countY = Math.abs((int) (yPos[i][j][k]) / 64);
                        if(listX.size() == 0){
                            grid[i][j][countX][countY] = count;
                            listX.add(countX);
                            listY.add(countY);
                            count++;
                        }
                        if(listX.size() != 0 && listX.get(listX.size()-1) != countX && listY.get(listY.size()-1) != countY){
                            grid[i][j][countX][countY] = count;
                            listX.add(countX);
                            listY.add(countY);
                            count++;
                        }
                    }
                }
            }
        }
        
        // find zero-error cells
        
    }
    
    public int[][][][] getGroundTruthGrid(){
        int[][][][] array = new int[4][6][24][24];
        
        return array;
    }
    public int[][][][] getGrid(){
        return this.grid;
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void loadRawData()
    {
        int count = 0; // counter for files in directory [0 ... 35]
        // mazes [0 ... 3] i.e. mazes 6, 8, 11, 12
        for(int k = 0; k < 4; k++){
            // trials [0 ... 5]
            for(int i = 0; i < 6; i++){
                try (BufferedReader br = new BufferedReader(new FileReader(directory + "/" + fileNames.get(count)))) {
                    String line;
                    int j = 0;
                    // lines in the file [0 ... number of lines]
                    while ((line = br.readLine()) != null) {
                        String[] array = line.split(" ");
                        String[] times = array[0].split(":");
                        String[] something = times[0].split("T");
                        float hour = ParseFloat(something[1]);
                        float min = ParseFloat(times[1]);
                        float minstosec = (hour * 60 * 60) + (min * 60);
                        float sec = ParseFloat(times[2]);
                        float totalSec = minstosec + sec;
                        time[k][i][j] = totalSec;
                        String[] info = array[1].split(",");
                        xPos[k][i][j] = ParseFloat(info[0]);
                        yPos[k][i][j] = ParseFloat(info[1]);
                        j++;
                        //System.out.println(totalSec);
                    }
                }
                catch (IOException e)   
                {  
                    e.printStackTrace(); 
                }  
                count++;
            }
        }
    }
    
    public float ParseFloat(String strNumber) {
       if (strNumber != null && strNumber.length() > 0) {
           try {
              return Float.parseFloat(strNumber);
           } catch(Exception e) {
              return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
           }
       }
       else return 0;
    }
    
    public void getInfo(){ 
        int n = 229;
        try{
            File file = new File("/Users/khakiss/Desktop/Dyslexia-Research/data_new.csv");
            List<String> lines = Files.readAllLines(file.toPath());  
            //System.out.println(lines.size());
            for(int i = 1; i < n-1; i++){
                String line = lines.get(i);
                String[] array = line.split(",");    // use comma as separator  
            }
        }
        catch (IOException e)   
        {  
            e.printStackTrace(); 
        }  
    }
    
    public void averageVectors(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 6; j++){
                float vector_x_sum = 0;
                float vector_y_sum = 0;
                double vector_a_sum = 0;
                int count = 0;
                for(int k = 0; k < 15500; k++){
                    if(vector_x[i][j][k] != -15500){
                        vector_x_sum = vector_x_sum + vector_x[i][j][k];
                        vector_y_sum = vector_y_sum + vector_y[i][j][k];
                        vector_a_sum = vector_a_sum + vector_a[i][j][k];
                        count++;
                    }
                }
                vector_x_av_t[i][j] = vector_x_sum / count;
                vector_y_av_t[i][j] = vector_y_sum / count;
                vector_a_av_t[i][j] = vector_a_sum / count;
            }
        }
        
        for(int x = 0; x < 4; x++){
            float vector_x_s = 0;
            float vector_y_s = 0;
            double vector_a_s = 0;
            for(int y = 0; y < 6; y++){
                vector_x_s = vector_x_s + vector_x_av_t[x][y];
                vector_y_s = vector_y_s + vector_y_av_t[x][y];
                vector_a_s = vector_a_s + vector_a_av_t[x][y];
            }
            vector_x_av[x] = vector_x_s / 6;
            vector_y_av[x] = vector_y_s / 6;
            vector_a_av[x] = vector_a_s / 6;
        }
    }
    
    public double[][] calcTotalDist(){
        double dist[][] = new double[6][6];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 6; j++){
                double sum = 0;
                for(int k = 1; k < xPos[i][j].length; k++){
                    double diffX = xPos[i][j][k] - xPos[i][j][k-1];
                    double diffY = yPos[i][j][k] - yPos[i][j][k-1];
                    double distance = Math.sqrt(Math.pow(diffX,2) + Math.pow(diffY,2));
                    sum = sum + distance;
                }
                dist[i][j] = sum;
            }
        }
        
        return dist;
    }
    
    public double[][] calcTotalTime(){
        double totTime[][] = new double[6][6];
        
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 6; j++){
                double totalTime = 0;
                double begin = time[i][j][0];
                double stop = 0;
                for(int k = 0; k < time[i][j].length; k++){
                    if(time[i][j][k]==0){
                        stop = time[i][j][k-1];
                        break;
                    }
                }
                totTime[i][j] = stop - begin;
            }
        }
        
        return totTime;
    }
    
    public float getFirstTime(){
        return time[0][0][0];
    }
    
    public float[][][] getXPos(){
        return xPos;
    }
    
    public float[][][] getYPos(){
        return yPos;
    }
    
    public int getType(){
        return this.type;
    }
    
    public String getID(){
        return this.id;
    }
    
    public int getGender(){
        return this.gender;
    }
    
    public float getAge(){
        return this.age;
    }
    
    public List<String> getFileNames(){
        return this.fileNames;
    }
    
    public boolean isGroundTruth(){
        String[] idGT = this.id.split("_");
        String begin = idGT[0];
        if(begin.equals("2021.04.03")){
            return true;
        }
        return false;
    }
}
