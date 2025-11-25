import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;


public class Query{

    private boolean hasIndex;
    private Index index;
    private int lowerBound;
    private int upperBound;

    public Query(){
        hasIndex = false;
        lowerBound = -1;
        upperBound = -1;
    }

    //getters and setters
    public Index getIndex(){
        return index;
    }
    //only call once or will be overwirtten
    public void setIndex(){
        index = new Index();
    }
    public boolean getHasIndex(){
        return hasIndex;
    }
    public void setHasIndex(boolean newhasIndex){
        hasIndex = newhasIndex;
    }
    public int getLower(){
        return lowerBound;
    }
    public void setLower(int newLower){
        lowerBound = newLower;
    }
    public int getUpper(){
        return upperBound;
    }
    public void setUpper(int newUpper){
        upperBound = newUpper;
    }

    //read the directory, folder by folder, record by record and create hash entry/array entry for each record
    public void readDirectory(){
        //loop through text files in the directory
        File folder = new File("Project2Dataset");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        //store selected records
        ArrayList<String> selectRecords = new ArrayList<>();
        //initialize file count
        int fileCount = 0;
        if (files != null) {
            for (File file : files) {
                //get file number
                String fileNumber = file.getName().substring(1,3);
                if (fileNumber.contains(".")){
                    fileNumber = fileNumber.substring(0,1);
                }

                //track number of files read
                fileCount++;

                //start reading the file - read entire file at a time
                try (FileReader fr = new FileReader(file)) {
                    char[] fileContent = new char[4000];
                    int fileIndex = 0;
                    int charInt;

                    while ((charInt = fr.read()) != -1) {
                        fileContent[fileIndex] = (char) charInt;
                        fileIndex++;
                    }
                    
                    //condition for table scan
                    if(hasIndex == false){
                        //loop through the file by offset of 33
                        for (int offset = 0; offset <= 3960; offset += 40){
                            char[] record = Arrays.copyOfRange(fileContent, offset, offset + 40);
                            //get randV
                            String recordStr = new String(record);
                            int randV = Integer.parseInt(recordStr.substring(33, 37));
                            
                            //check for equaality or range search
                            if(getLower() != -1 && getUpper() == -1){                            
                                if(randV == getLower()){
                                    selectRecords.add(String.valueOf(record));
                                }
                            } else if (getLower() != -1 && getUpper() != -1){
                                if(randV > getLower() && randV < getUpper()){
                                    selectRecords.add(String.valueOf(record));
                                }
                            }
                        }

                    }

                    //condition for creating index
                    else{
                        index.addToIndex(fileNumber, fileContent);
                    }
                    
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }

            }
            
        }
        if (!selectRecords.isEmpty()){
            System.out.println("Records found using Table Scan: ");
            for (String item : selectRecords) {
                System.out.println(item);
            }
            System.out.println("Data files read from disk: " + fileCount);

        }
    }

    //take in string of locations (one or more seperated by | ) and read val from disk
    public int readFromDisk(String location){
        ArrayList<String> recordsList = new ArrayList<>();
        //track how many files read from disk
        int fileCount = 0;
        String previousFile = ""; 

        String[] locationArray = location.split(" | ");
        for (int i = 0; i < locationArray.length; i++){
            if (!locationArray[i].contains("|")){
                //System.out.print(locationArray[i]);

                String[] recordLocation = locationArray[i].split("-");

                //loop through the locations and put files in recordsList
                String fileNumber = recordLocation[0];
                if(!fileNumber.equals(previousFile)){
                    fileCount++;
                }
                
                try {
                    //use raf to keep track of exact location
                    RandomAccessFile raf = new RandomAccessFile("Project2Dataset/F" + fileNumber + ".txt", "r");
                    int offset = Integer.parseInt(recordLocation[1]);
                    raf.seek(offset);

                    byte[] recordBytes = new byte[40];
                    //readFully reads until byte array is full
                    raf.readFully(recordBytes); 

                    String record = new String(recordBytes);
                    recordsList.add(record);

                    raf.close();

                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }

            }
        } 
        for (String item : recordsList) {
            System.out.print(item);
            System.out.println(" ");
        }
        // System.out.println("Data files read from disk: " + fileCount);
        return fileCount;

    }


    //read the file from the disk and add each record to hash and array
    public void create() {
        if (hasIndex == true){
            System.out.println("Index already created.");
        } else {
            //create an index object and set flag
            setIndex();
            hasIndex = true;
            
            readDirectory();
            long endTime = System.currentTimeMillis();

            System.out.println("The hash-based and array-based indexes are built successfully. Program is ready and waiting for user command.");
        }
    }

    //If index exists, search hash for values
    public void selectHash(String v){
        long startTime = 0;
        long endTime = 0;
        if (hasIndex == true){
            System.out.println("Records found using hash-based index: ");
            //start timer
            startTime = System.currentTimeMillis();
            readFromDisk(index.getHash().get(v));
            endTime = System.currentTimeMillis();
        } else {
            //set v1 for table scan equality search
            setLower(Integer.parseInt(v));
            startTime = System.currentTimeMillis();
            readDirectory();
            endTime = System.currentTimeMillis();
            //reset v1
            setLower(-1);
        }
        System.out.println("Time taken: " + (endTime - startTime) + " ms");

    }

    public void selectArray(String value1, String value2){
        long startTime = 0;
        long endTime = 0;
        //convert to int and adjust for 0-4999 array structure
        int v1 = Integer.parseInt(value1) - 1;
        int v2 = Integer.parseInt(value2) - 1;
        if(hasIndex == true){

            //create an array for the projection
            ArrayList<String> output = new ArrayList<String>();
            startTime = System.currentTimeMillis();
            for (int i = v1 + 1; i < v2; i++){
                if(!index.getArray()[i].equals("0")){
                    output.add(index.getArray()[i]);
                }
            } 
            endTime = System.currentTimeMillis();      

            System.out.println("Records found using array-based index: ");
            int fileCount = 0;
            for (String element : output) {
                fileCount += readFromDisk(element);
            }
            System.out.println("Data files read from disk: " + fileCount);

        } else {
            //set v1 and v2
            setLower(v1 + 1);
            setUpper(v2 + 1);
            startTime = System.currentTimeMillis();
            readDirectory();
            endTime = System.currentTimeMillis();
            //reset v1
            setLower(-1);
            setUpper(-1);
        }
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
    }

}