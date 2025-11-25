import java.util.Arrays;
import java.util.Hashtable;

public class Index {

    private Hashtable<String, String> hashIndex;
    private String[] arrayIndex;

    public Index(){
        hashIndex = new Hashtable<>();
        arrayIndex = new String[5000];
        for (int i = 0; i < 5000; i++){
            arrayIndex[i] = "0";
        };
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


    public void addToIndex(String fileNumber, char[] fileContent){
        //loop through file and get each record
        for (int offset = 0; offset <= 3960; offset += 40){
            char[] record = Arrays.copyOfRange(fileContent, offset, offset + 40);
            //get randV
            String recordStr = new String(record);
            String randV = recordStr.substring(33, 37);
            
            //set location - concat fileNumber and offset
            String loc = fileNumber.concat("-").concat(String.valueOf(offset));

            //check if the key is already in the hashtable - concat if true
            //if randv in hastable then in array too?
            if (hashIndex.containsKey(randV) == true){
                loc = hashIndex.get(randV) + " | " + loc;
                //System.out.println("loc from hash: " + loc);
            }
            //add to the hashTable
            hashIndex.put(randV, loc);

            //check if randv already assigned a location (value is not 0) - concat if true
            // if (!arrayIndex[index].equals("0")){
            //     loc = arrayIndex[index] + " | " + loc;
            //     System.out.println("loc from array: " + loc);

            // }
            //add to array
            int index = Integer.parseInt(randV) - 1;
            arrayIndex[index] = loc;
        }
        
    }

}