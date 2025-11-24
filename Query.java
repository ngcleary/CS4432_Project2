import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
                //System.out.println("fileNumber: " + fileNumber);

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
                    index.addToHash(fileNumber, fileContent);
                    
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }

            }
             System.out.println("hashtable: " + index.getHash().size());
        }
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
        }
    }

    //If index exists, search hash for values
    public void selectHash(String v){
        if (hasIndex == true){
            System.out.println("testing select: " + index.getHash().toString());
            System.out.println("Records: " + index.getHash().get(v));
        }
    }

}