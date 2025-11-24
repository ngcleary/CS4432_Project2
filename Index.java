import java.util.Arrays;
import java.util.Hashtable;

import java.io.IOException;


public class Index {

    private Hashtable<String, String> hashIndex;
    private String[] arrayIndex;

    public Index(){
        hashIndex = new Hashtable<>();
        arrayIndex =new String[5000];
    }

    //getters and setters
    public Hashtable<String, String> getHash(){
        return hashIndex;
    }
    public void setHash(Hashtable<String, String> newHash){
        hashIndex = newHash;
    }

    public String[] getArray(){
        return arrayIndex;
    }
    public void setArray(String[] newArray){
        arrayIndex = newArray;
    }


    public void addToHash(String fileNumber, char[] fileContent){
        //loop through file and get each record
        for (int offset = 0; offset <= 3960; offset += 40){
            char[] record = Arrays.copyOfRange(fileContent, offset, offset + 40);
            //get randV
            String recordStr = new String(record);
            String randV = recordStr.substring(33, 37);
            //String randV = String.valueOf(record).substring(33, 37);
            //set location - concat fileNumber and offset
            String loc = fileNumber.concat(String.valueOf(offset));

            //check if the key is already in the hashtable - concat if true
            if (hashIndex.containsKey(randV) == true){
                loc = hashIndex.get(randV) + " | " + loc;
            }
            //add to the hashTable
            hashIndex.put(randV, loc);
        }
        
       
    }

}