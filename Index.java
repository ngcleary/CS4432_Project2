import java.util.Arrays;
import java.util.Hashtable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Index {

    private Hashtable<String, String> hashIndex;
    private String[] arrayIndex;

    public Index(){
        hashIndex = new Hashtable<>();
        arrayIndex =new String[5000];
    }

    //read the directory, folder by folder, record by record and create hash entry/array entry for each record
    public void read_folder(){
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
                    //System.out.println("file content: " + String.valueOf(fileContent));

                    //create hash entry
                    addToHash(fileNumber, fileContent);
                    


                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }

                //start reading the file - one record at a time

            }
             System.out.println("hashtable: " + hashIndex);
        }
    }

    public void addToHash(String fileNumber, char[] fileContent){
        //loop through file and get each record
        for (int offset = 0; offset <= 3960; offset += 40){
            char[] record = Arrays.copyOfRange(fileContent, offset, offset + 40);
            //get randV
            String randV = String.valueOf(record).substring(33, 37);
            //set location - concat fileNumber and offset
            String loc = fileNumber.concat(String.valueOf(offset));

            //check if the key is already in the hashtable - concat if true
            if (hashIndex.containsKey(randV) == true){
                loc = hashIndex.get(randV).concat(loc);
            }
            //add to the hashTable
            hashIndex.put(randV, loc);
        }
        
       
    }

    public void CREATE(){
        read_folder();
        System.out.println("The hash-based and array-based indexes are built successfully. Program is ready and waiting for user command.");
        //write to hash - new entry with location, key
        //write to array - put key at index[offset (modify for offset in file, not record)]
    }

    // public void selectWhereEquals(){

    // }

}