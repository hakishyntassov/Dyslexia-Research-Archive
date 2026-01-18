import java.util.*;
import java.io.*;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.FillPatternType;  
import org.apache.poi.ss.usermodel.IndexedColors;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class main
{
    private String[] ids; // array that stores IDs of each participant
    private float[] ages; // array that stores age of each participant
    private int[] genders; // array that stores gender of each participant --> gender_d: 0 - female, 1 - male
    private int[] types; // array that stores participant's type --> type: 1 - typical, 2 - atypical
    
    
    private float[][] maze1t; // [#][maze_trial]
    private float[][] maze5t; // [#][maze_trial]
    private float[][] maze6t; // [#][maze_trial]
    
    private float[][] maze8t; // [#][maze_trial]
    private float[] maze8ta; // average times
    private float[] maze8ta_ma; // average times for atypical males
    private float[] maze8ta_mt; // average times for typical males
    private float[] maze8ta_fa; // average times for atypical females
    private float[] maze8ta_ft; // average times for typical females
    
    private float[][] maze11t; // [#][maze_trial]
    private float[][] maze12t; // [#][maze_trial]
    
    private int[][] maze1e; // [#][maze_trial]
    private int[][] maze5e; // [#][maze_trial]
    private int[][] maze6e; // [#][maze_trial]
    private int[][] maze8e; // [#][maze_trial]
    private int[][] maze11e; // [#][maze_trial]
    private int[][] maze12e; // [#][maze_trial]
    
    // Distance in region A0 - A5
    private float[][] maze1dA;
    private float[][] maze5dA;
    private float[][] maze6dA;
    private float[][] maze8dA;
    private float[][] maze11dA;
    private float[][] maze12dA;
    
    // Distance in region B0 - B5
    private float[][] maze1dB;
    private float[][] maze5dB;
    private float[][] maze6dB;
    private float[][] maze8dB;
    private float[][] maze11dB;
    private float[][] maze12dB;
    
    // Distance in region C0 - C5
    private float[][] maze1dC;
    private float[][] maze5dC;
    private float[][] maze6dC;
    private float[][] maze8dC;
    private float[][] maze11dC;
    private float[][] maze12dC;
    
    // Distance in region D0 - D5
    private float[][] maze1dD;
    private float[][] maze5dD;
    private float[][] maze6dD;
    private float[][] maze8dD;
    private float[][] maze11dD;
    private float[][] maze12dD;
    
    // Distance in region E0 - E5
    private float[][] maze1dE;
    private float[][] maze5dE;
    private float[][] maze6dE;
    private float[][] maze8dE;
    private float[][] maze11dE;
    private float[][] maze12dE;
    
    // Distance in region F0 - F5
    private float[][] maze1dF;
    private float[][] maze5dF;
    private float[][] maze6dF;
    private float[][] maze8dF;
    private float[][] maze11dF;
    private float[][] maze12dF;
    

    /**
     * Constructor for objects of class main
     */
    public main()
    {
        ids = new String[91];
        ages = new float[91];
        genders = new int[91];
        types = new int[91];
        
        maze1t = new float[91][6];
        maze5t = new float[91][6];
        maze6t = new float[91][6];
        maze8t = new float[91][6];
        maze11t = new float[91][6];
        maze12t = new float[91][6];
        
        maze8ta = new float[91];
        
        maze1e = new int[91][6];
        maze5e = new int[91][6];
        maze6e = new int[91][6];
        maze8e = new int[91][6];
        maze11e = new int[91][6];
        maze12e = new int[91][6];
            
        // IMPORTANT: change the destination of DATA folder
        String destination = "/C:/Users/User/Dyslexia-Research/DATA";
        File file = new File(destination);
        String[] directories = file.list(new FilenameFilter() {
          @Override
          public boolean accept(File current, String name) {
            return new File(current, name).isDirectory();
          }
        });
        
        Participant[] p = new Participant[directories.length]; // array that stores all participants
        
        // go through each directory in DATA folder
        for(int i = 0; i < directories.length; i++){
            String dir = destination + "/" + directories[i] + "/" + "Logs";
            File folder = new File(dir);
            String[] files = folder.list();     
            //System.out.println(Arrays.toString(files));
            p[i] = new Participant(directories[i], files, dir); // new participant object
        }
        
        
        
         try {
            FileWriter csvWriter = new FileWriter("errors.csv");
            csvWriter.append("ID");
            csvWriter.append(",");
            csvWriter.append("type");
            csvWriter.append(",");
            csvWriter.append("gender");
            csvWriter.append(",");
            csvWriter.append("age");
            csvWriter.append(",");
            csvWriter.append("maze-1-1");
            csvWriter.append(",");
            csvWriter.append("maze-1-2");
            csvWriter.append(",");
            csvWriter.append("maze-1-3");
            csvWriter.append(",");
            csvWriter.append("maze-1-4");
            csvWriter.append(",");
            csvWriter.append("maze-1-5");
            csvWriter.append(",");
            csvWriter.append("maze-1-6");
            csvWriter.append(",");
            csvWriter.append("maze-5-1");
            csvWriter.append(",");
            csvWriter.append("maze-5-2");
            csvWriter.append(",");
            csvWriter.append("maze-5-3");
            csvWriter.append(",");
            csvWriter.append("maze-5-4");
            csvWriter.append(",");
            csvWriter.append("maze-5-5");
            csvWriter.append(",");
            csvWriter.append("maze-5-6");
            csvWriter.append(",");
            csvWriter.append("maze-6-1");
            csvWriter.append(",");
            csvWriter.append("maze-6-2");
            csvWriter.append(",");
            csvWriter.append("maze-6-3");
            csvWriter.append(",");
            csvWriter.append("maze-6-4");
            csvWriter.append(",");
            csvWriter.append("maze-6-5");
            csvWriter.append(",");
            csvWriter.append("maze-6-6");
            csvWriter.append(",");
            csvWriter.append("maze-8-1");
            csvWriter.append(",");
            csvWriter.append("maze-8-2");
            csvWriter.append(",");
            csvWriter.append("maze-8-3");
            csvWriter.append(",");
            csvWriter.append("maze-8-4");
            csvWriter.append(",");
            csvWriter.append("maze-8-5");
            csvWriter.append(",");
            csvWriter.append("maze-8-6");
            csvWriter.append(",");
            csvWriter.append("maze-11-1");
            csvWriter.append(",");
            csvWriter.append("maze-11-2");
            csvWriter.append(",");
            csvWriter.append("maze-11-3");
            csvWriter.append(",");
            csvWriter.append("maze-11-4");
            csvWriter.append(",");
            csvWriter.append("maze-11-5");
            csvWriter.append(",");
            csvWriter.append("maze-11-6");
            csvWriter.append(",");
            csvWriter.append("maze-12-1");
            csvWriter.append(",");
            csvWriter.append("maze-12-2");
            csvWriter.append(",");
            csvWriter.append("maze-12-3");
            csvWriter.append(",");
            csvWriter.append("maze-12-4");
            csvWriter.append(",");
            csvWriter.append("maze-12-5");
            csvWriter.append(",");
            csvWriter.append("maze-12-6");
            csvWriter.append("\n");
            
            for(int i = 0; i < 91; i++){
                csvWriter.append(p[i].getID());
                csvWriter.append(",");
                csvWriter.append("" + p[i].getType());
                csvWriter.append(",");
                csvWriter.append("" + p[i].getGender());
                csvWriter.append(",");
                csvWriter.append("" + p[i].getAge());
                csvWriter.append(",");
                for(int j = 0; j < 6; j++){
                    csvWriter.append("" + findError(p,i,0,j,5));
                    csvWriter.append(",");
                }
                for(int j = 0; j < 6; j++){
                    csvWriter.append("" + findError(p,i,3,j,5));
                    csvWriter.append(",");
                }
                for(int j = 0; j < 6; j++){
                    csvWriter.append("" + findError(p,i,4,j,5));
                    csvWriter.append(",");
                }
                for(int j = 0; j < 6; j++){
                    csvWriter.append("" + findError(p,i,5,j,5));
                    csvWriter.append(",");
                }
                for(int j = 0; j < 6; j++){
                    csvWriter.append("" + findError(p,i,1,j,3));
                    csvWriter.append(",");
                }
                for(int j = 0; j < 6; j++){
                    csvWriter.append("" + findError(p,i,2,j,3));
                    csvWriter.append(",");
                }
                csvWriter.append("\n");
            }
            
            csvWriter.flush();
            csvWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    
       //findError(p,0,3,1,5);
    }
    
    /*
     * Method that calculates and returns the total error for the given participant, given maze, given trial.
     * Arguments: Participant[] p - array that stores all participants (there are 91 participants + 1 ground-truth participant)
     * Arguments: int pIndex - participant's index (0 to 90)
     * Arguments: int mIndex - given maze (0 to 5)
     * Arguments: int tIndex - given trial (0 to 5)
     * Arguments: int gIndex - given ground-truth trial (0 to 5)
     */
    public int findError(Participant[] p, int pIndex, int mIndex, int tIndex, int gIndex){
        float[] xPos = p[pIndex].getXPos()[mIndex][tIndex]; // participant's x-positions
        float[] yPos = p[pIndex].getYPos()[mIndex][tIndex]; // participant's y-positions
        float[] gxPos = p[91].getXPos()[mIndex][gIndex]; // ground-truth x-positions
        float[] gyPos = p[91].getYPos()[mIndex][gIndex]; // ground-truth y-positions
                
        int countX = 0;
        int countY = 0;
        int gcountX = 0;
        int gcountY = 0;
        
        ArrayList<Integer> listX = new ArrayList<Integer>(); // ArrayList that stores cell's x indices  
        ArrayList<Integer> listY = new ArrayList<Integer>(); // ArrayList that stores cell's y indices 
        ArrayList<Integer> listErr = new ArrayList<Integer>(); // ArrayList that stores error zone indices 
        ArrayList<Integer> listD = new ArrayList<Integer>(); // ArrayList that stores decrement values for each cell visited by participant 
        ArrayList<Integer> glistX = new ArrayList<Integer>(); // ArrayList that stores ground-truth cell's x indices          
        ArrayList<Integer> glistY = new ArrayList<Integer>(); // ArrayList that stores ground-truth cell's y indices  
        ArrayList<Integer> gtIn = new ArrayList<Integer>(); // ArrayList that stores ground-truth indices matched

        ArrayList<Integer> errors = new ArrayList<Integer>(); // ArrayList that stores error value at each cell visited by a participant 
        
        int gridCoefficient = 64; // since original grid is 1536x1536, to make it 24x24 we need to divide each position value by 64
        
        for(int k = 0; k < 10000; k++){
            // participant's grid
            if(xPos[k] > 0 && yPos[k] > 0){
                countX = (int) (xPos[k] / gridCoefficient); // cell's x-index
                countY = (int) (yPos[k] / gridCoefficient); // cell's y-index
                // add the cell's x and y indices to listX and listY
                if(listX.size() == 0){
                    listX.add(countX);
                    listY.add(countY);
                }
                else{
                    if(listX.get(listX.size()-1) != countX || listY.get(listY.size()-1) != countY){
                        listX.add(countX);
                        listY.add(countY);
                    }
                }
            }
            
            // ground-truth grid and do the same thing
            if(gxPos[k] > 0 && gyPos[k] > 0){
                gcountX = (int) (gxPos[k] / gridCoefficient);
                gcountY = (int) (gyPos[k] / gridCoefficient);
                if(glistX.size() == 0){
                    glistX.add(gcountX);
                    glistY.add(gcountY);
                }
                else{
                    if(glistX.get(glistX.size()-1) != gcountX || glistY.get(glistY.size()-1) != gcountY){
                        glistX.add(gcountX);
                        glistY.add(gcountY);
                    }
                }
            }
        }
        
        
        int diffX = 0; // difference between participant's and ground-truth cell's x-indices
        int diffY = 0; // difference between participant's and ground-truth cell's y-indices
        
        double dist = 0; // distance between participant's and ground-truth cells
        double prevDist = 0; // previous distance
        
        int prevIndex = 0; // previous index
        
        int error = 0; // error value
        
        int decrement = 0; // decrement value
        //boolean check = false;
            
        for(int i = 0; i < listX.size(); i++){
            int gtIndex = 0; // difference between participant's and ground-truth cell's x-indices
            int changeX = 0; // difference between participant's and ground-truth cell's y-indices
            int changeY = 0; // change in participant's movement in y-direction
            double minDistance = 1000;
            double distance = 0;
            // we limited for loop to go from prevIndex to prevIndex + 2
            for(int j = prevIndex; j < prevIndex+3; j++){
                if(glistX.size() > j){
                    // calculate differences
                    changeX = Math.abs(listX.get(i) - glistX.get(j));
                    changeY = Math.abs(listY.get(i) - glistY.get(j));
                    // calculate distance
                    distance = Math.sqrt((changeX*changeX)+(changeY*changeY));
                    
                    // if statement to determine index that is closest to participant's cell
                    if(distance < minDistance){
                        minDistance = distance;
                        gtIndex = j;
                    }
                }
            }
            
            gtIn.add(gtIndex); // add matched ground-truth index
            
            // calculate differences and distance
            diffX = Math.abs(listX.get(i) - glistX.get(gtIndex));
            diffY = Math.abs(listY.get(i) - glistY.get(gtIndex));
            dist = Math.sqrt((diffX*diffX)+(diffY*diffY));
            
            // check which maze index it is and then add corresponding values to listErr
            if(mIndex == 0){
                listErr.add(0);
            }
            else if(mIndex == 1){
                if(listX.get(i) >= 0 && listX.get(i) <= 7 && listY.get(i) >= 8 && listY.get(i) <= 13){
                    listErr.add(1);
                }
                else if(listX.get(i) >= 0 && listX.get(i) <= 7 && listY.get(i) >= 12 && listY.get(i) <= 19){
                    listErr.add(2);
                }
                else if(listX.get(i) >= 12 && listX.get(i) <= 15 && listY.get(i) >= 0 && listY.get(i) <= 19){
                    listErr.add(3);
                }
                else if(listX.get(i) >= 16 && listX.get(i) <= 23 && listY.get(i) >= 0 && listY.get(i) <= 19){
                    listErr.add(4);
                }
                else{
                    listErr.add(0);
                }
            }
            else if(mIndex == 2){
                if(listX.get(i) >= 16 && listX.get(i) <= 19 && listY.get(i) >= 4 && listY.get(i) <= 23){
                    listErr.add(1);
                }
                else{
                    listErr.add(0);
                }
            }
            else if(mIndex == 3){
                if(listX.get(i) >= 0 && listX.get(i) <= 7 && listY.get(i) >= 4 && listY.get(i) <= 11){
                    listErr.add(1);
                }  
                else if(listX.get(i) >= 0 && listX.get(i) <= 7 && listY.get(i) >= 12 && listY.get(i) <= 23){
                    listErr.add(2);
                } 
                else if(listX.get(i) >= 8 && listX.get(i) <= 15 && listY.get(i) >= 20 && listY.get(i) <= 23){
                    listErr.add(3);
                } 
                else if(listX.get(i) >= 12 && listX.get(i) <= 23 && listY.get(i) >= 0 && listY.get(i) <= 11){
                    listErr.add(4);
                } 
                else if(listX.get(i) >= 20 && listX.get(i) <= 23 && listY.get(i) >= 12 && listY.get(i) <= 19){
                    listErr.add(4);
                } 
                else{
                    listErr.add(0);
                }
            }
            else if(mIndex == 4){
                if(listX.get(i) >= 8 && listX.get(i) <= 11 && listY.get(i) >= 0 && listY.get(i) <= 11){
                    listErr.add(1);
                }   
                else if(listX.get(i) >= 12 && listX.get(i) <= 23 && listY.get(i) >= 0 && listY.get(i) <= 11){
                    listErr.add(2);
                }      
                else if(listX.get(i) >= 8 && listX.get(i) <= 11 && listY.get(i) >= 12 && listY.get(i) <= 19){
                    listErr.add(3);
                } 
                else if(listX.get(i) >= 12 && listX.get(i) <= 23 && listY.get(i) >= 12 && listY.get(i) <= 19){
                    listErr.add(4);
                } 
                else{
                    listErr.add(0);
                }
            }
            else if(mIndex == 5){
                listErr.add(0);
            }
            
            // check that it's not first index
            if(i != 0){
                // check that current error zone is zero, previous is zero, and that the distance is more than zero
                if(listErr.get(i) == 0 && listErr.get(i-1) == 0 && dist > 0){
                    decrement = 1;
                }
                // check that current error zone is zero, previous is not zero, and that the distance is more than zero
                else if(listErr.get(i) == 0 && listErr.get(i-1) != 0 && dist > 0){
                    decrement = 1;
                }
                // check that current error zone is not zero, previous is zero
                else if(listErr.get(i) != 0 && listErr.get(i-1) == 0){
                    if(listErr.get(i) > listErr.get(i-1)){
                        decrement = decrement * 2;
                    }
                    else{
                        decrement = decrement / 2;
                    }
                }
                // check that current error zone is not zero, previous is not zero, and they are not equal
                else if(listErr.get(i) != 0 && listErr.get(i-1) != 0 && listErr.get(i) != listErr.get(i-1)){
                    if(listErr.get(i) > listErr.get(i-1)){
                        decrement = decrement * 2;
                    }
                    else{
                        decrement = decrement / 2;
                    }
                }
            }
            
            listD.add(decrement); // add decrement value

            // check if it's first index
            if(i == 0){
                // if participant's path deviates from ground-truth path
                if(dist > 0){
                    error = error - (int)dist;
                }
            }
            else{
                if(gtIndex == prevIndex && dist > prevDist){
                    error = error - decrement;
                }
                /*else if(gtIndex == prevIndex && dist <= prevDist){
                    error = error;
                }*/
                else if(dist > 0){
                    error = error - decrement;
                }
            }
            
            /*
            if(dist == 0){
                error = error;
            }
            else if(gtIndex == prevIndex && dist < prevDist){
                error = error;
            }
            else if(dist > 0 && i == 0){
                error = error - 1;
            }
            else{
                error = error - 1;
            }
            */
           
            prevIndex = gtIndex;
            prevDist = dist;
            
            errors.add(error);
            //System.out.println("index is: " + i + ", participantX: " + listX.get(i) + ", participantY: " + listY.get(i) + ", gtX: " + glistX.get(gtIndex) + ", gtY: " + glistY.get(gtIndex) + ", distance is: " + dist + ", error is: " + error);
            
        }
        /*
        try {
          FileWriter myWriter = new FileWriter("filename.txt");
          for(int i = 0; i < listX.size(); i++){
              myWriter.write("participantX: " + listX.get(i) + ", participantY: " + listY.get(i) + ", gtX: " + glistX.get(gtIn.get(i)) + ", gtY: " + glistY.get(gtIn.get(i)) + " , error: " + errors.get(i) + " \n");
          }
          myWriter.close();
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        */
       
        // print out ArrayLists
        System.out.println(listX.toString());
        System.out.println(listY.toString());
        System.out.println(listErr.toString());
        System.out.println(listD.toString());
        System.out.println("Error is " + error);
        
        
        // THIS generates Excel files for each trial and visualizes both ground-truth and participant's paths
        // and displays error value at each cell visited by participant
        // GREEN / RED: ground-truth path
        // YELLOW: participant's path
        try (OutputStream fileOut = new FileOutputStream("/C:/Users/User/Dyslexia-Research/DATA/" + p[pIndex].getID() + "/" + p[pIndex].getID() + "_" + mIndex + "_" + tIndex + ".xlsx")) {  
            Workbook wb = new XSSFWorkbook();  
            Row[] rows = new Row[25];
            short value = 800;
            Sheet sheet = wb.createSheet("Sheet"); 
            for(int i = 0; i < 25; i++){
                rows[i] = sheet.createRow(i);
                rows[i].setHeight(value);
            }
            Cell cell1 = rows[0].createCell(0);
            for(int i = 1; i < 25; i++){
                Cell cell = rows[i].createCell(0);
                cell.setCellValue(""+(i-1));
            }
            
            for(int i = 1; i < 25; i++){
                Cell cell = rows[0].createCell(i);
                cell.setCellValue(""+(i-1));
            }
            
            for(int i = 0; i < glistX.size(); i++){
                CellStyle style = wb.createCellStyle();  
                // Setting Background color  
                style.setFillBackgroundColor(IndexedColors.GREEN.getIndex()); 
                style.setFillPattern(FillPatternType.BIG_SPOTS);  
                Cell cell = rows[glistY.get(i)+1].createCell(glistX.get(i)+1);
                cell.setCellStyle(style);  
                cell.setCellValue("+");
            }
            
            for(int i = 0; i < gtIn.size(); i++){
                CellStyle style = wb.createCellStyle();  
                // Setting Background color  
                style.setFillBackgroundColor(IndexedColors.RED.getIndex()); 
                style.setFillPattern(FillPatternType.BIG_SPOTS);  
                Cell cell = rows[glistY.get(gtIn.get(i))+1].createCell(glistX.get(gtIn.get(i))+1);
                cell.setCellStyle(style);  
                cell.setCellValue("+");
            }
            
            for(int i = 0; i < listX.size(); i++){
                CellStyle style = wb.createCellStyle();  
                // Setting Background color  
                style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex()); 
                style.setFillPattern(FillPatternType.BIG_SPOTS);  
                Cell cell = rows[listY.get(i)+1].createCell(listX.get(i)+1);
                cell.setCellValue(errors.get(i));
                cell.setCellStyle(style);  
            }
            
            wb.write(fileOut);  
        }
        catch(Exception e) {  
            System.out.println(e.getMessage());  
        }
        
        //System.out.println("participant: " + xPos[1]);
        //System.out.println("ground-truth: " + gyPos[1]);
        
        return error;
    }
    
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
    
    public void fetchData(String fileName)
    {
        String splitBy = ",";  
        try{
            File file = new File("data.csv");
            List<String> lines = Files.readAllLines(file.toPath());  
            System.out.println(lines.size());
            for(int i=1; i < 92; i++){
                String line= lines.get(i);
                String[] array = line.split(splitBy);    // use comma as separator  
                
                ids[i-1] = array[0];
                ages[i-1] = ParseFloat(array[2]);
                
                genders[i-1] = ParseInt(array[3]);
                types[i-1] = ParseInt(array[24]);
                
                int x = 25;
                for(int j = 0; j < 6; j++){
                    maze1t[i-1][j] = ParseFloat(array[x]);
                    x++;
                }
                
                x=31;
                for(int j = 0; j < 6; j++){
                    maze5t[i-1][j] = ParseFloat(array[x]);
                    x++;
                }
                
                x=37;
                for(int j = 0; j < 6; j++){
                    maze6t[i-1][j] = ParseFloat(array[x]);
                    x++;
                }
                
                x=43;
                for(int j = 0; j < 6; j++){
                    maze8t[i-1][j] = ParseFloat(array[x]);
                    x++;
                }
                
                x=49;
                for(int j = 0; j < 6; j++){
                    maze11t[i-1][j] = ParseFloat(array[x]);
                    x++;
                }
                
                x=55;
                for(int j = 0; j < 6; j++){
                    maze12t[i-1][j] = ParseFloat(array[x]);
                    x++;
                }
                
                x=61;
                for(int j = 0; j < 6; j++){
                    maze1e[i-1][j] = ParseInt(array[x]);
                    x++;
                }
                
                x=67;
                for(int j = 0; j < 6; j++){
                    maze5e[i-1][j] = ParseInt(array[x]);
                    x++;
                }
                
                x=73;
                for(int j = 0; j < 6; j++){
                    maze6e[i-1][j] = ParseInt(array[x]);
                    x++;
                }
                
                x=79;
                for(int j = 0; j < 6; j++){
                    maze8e[i-1][j] = ParseInt(array[x]);
                    x++;
                }
                
                x=85;
                for(int j = 0; j < 6; j++){
                    maze11e[i-1][j] = ParseInt(array[x]);
                    x++;
                }
                
                x=91;
                for(int j = 0; j < 6; j++){
                    maze12e[i-1][j] = ParseInt(array[x]);
                    x++;
                }
            }
        } 
        catch (IOException e)   
        {  
            e.printStackTrace(); 
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
    
    public int ParseInt(String strNumber) {
       if (strNumber != null && strNumber.length() > 0) {
           try {
              return Integer.parseInt(strNumber);
           } catch(Exception e) {
              return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
           }
       }
       else return 0;
    }
    
    public float[] average(float[][] array){
        float[] a = new float[91];
        for(int i = 0; i < 91; i++){
            float sum = 0;
            float average = 0;
            for(int j = 0; j < 6; j++){
                sum = sum + array[i][j];
            }
            average = sum / 6;
            a[i] = average;
        }
        return a;
    }
    
    public void calculate(String[] fileNames){
        float[] xpos = new float[4328];
        float[] ypos = new float[4328];
        for(int i = 0; i < fileNames.length; i++){
            try (BufferedReader br = new BufferedReader(new FileReader(fileNames[i]))) {
                String line;
                int j = 0;
                while ((line = br.readLine()) != null) {
                    String[] array = line.split(" ");
                    String[] info = array[1].split(",");
                    xpos[j] = ParseFloat(info[0]);
                    ypos[j] = ParseFloat(info[1]);
                    j++;
                }
            }
            catch (IOException e)   
            {  
                e.printStackTrace(); 
            }  
        }
    }
}