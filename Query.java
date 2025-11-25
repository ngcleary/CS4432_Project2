import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Query{

    private boolean hasIndex;
    private Index index;

    public Query(){
        hasIndex = false;
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

    //read the directory, folder by folder, record by record and create hash entry/array entry for each record
    public void readDirectory(Index index){
        //loop through text files in the directory
        File folder = new File("Project2Dataset");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                //get file number
                String fileNumber = file.getName().substring(1,3);
                if (fileNumber.contains(".")){
                    fileNumber = fileNumber.substring(0,1);
                }

                //start reading the file - read entire file at a time
                try (FileReader fr = new FileReader(file)) {
                    char[] fileContent = new char[4000];
                    int fileIndex = 0;
                    int charInt;

                    while ((charInt = fr.read()) != -1) {
                        fileContent[fileIndex] = (char) charInt;
                        fileIndex++;
                    }

                    //add record to the hashtable
                    index.addToIndex(fileNumber, fileContent);
                    
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }

            }
             System.out.println("hashtable: " + index.getHash().size());
        }
    }

    //take in string of locations (one or more seperated by | ) and read val from disk
    public void readFromDisk(String location){
        String[] locationArray = location.split("|");
        System.out.print("location: " + locationArray); 
    }

    //read the file from the disk and add each record to hash and array
    public void create() {
        if (hasIndex == true){
            System.out.println("Index already created.");
        } else {
            //create an index object and set flag
            setIndex();
            hasIndex = true;

            readDirectory(index);

            System.out.println("The hash-based and array-based indexes are built successfully. Program is ready and waiting for user command.");
            //System.out.println("hashtable: " + index.getHash().toString());
            // for (int i = 0; i < index.getArray().length; i++){
            //     System.out.print(i + ": " + index.getArray()[i]);
            // }
        }
    }

    //If index exists, search hash for values
    public void selectHash(String v){
        if (hasIndex == true){
            System.out.println("Records: " + index.getHash().get(v));
        }
    }

    public void selectArray(String value1, String value2){
        //convert to int and adjust for 0-4999 array structure
        int v1 = Integer.parseInt(value1) - 1;
        int v2 = Integer.parseInt(value2) - 1;

        //create an array for the projection
        ArrayList<String> output = new ArrayList<String>();
        for (int i = v1 + 1; i < v2; i++){
            if(!index.getArray()[i].equals("0")){
                output.add(index.getArray()[i]);
            }
        }
       // System.out.println("Records greater than " + value1 + " and less than " + value2 + ": " + output);
       

        for (String element : output) {
            System.out.println(element);
            //readFromDisk(elemet)
        }

    }

}