import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
        
        // Create scanner
        Scanner scanner = new Scanner(System.in);

        //make query object
        Query query = new Query();
        //query.readFromDisk("17 | 40");
        
        //start input loop
        while (true) {
        System.out.print("\n\nThe program is ready for the next command: ");
        String functionInput = scanner.nextLine().trim().toLowerCase();
        String[] functionArray = functionInput.split(" ");

        if (functionInput.equalsIgnoreCase("exit")) {
            System.out.println("Exiting program...");
            break;
        }


        switch (functionArray[0]) {
            case "create":
                query.create();
                break;
            case "select":
                //check if range search
                if (functionInput.contains(" and ")){
                    query.selectArray(functionArray[7], functionArray[11]);
                }
                //equality search
                else{
                    query.selectHash(functionArray[7]); //v
                }
                break;
            default:
                System.out.println("Unknown command: " + functionInput);
                break;
            }
                    
        }

    }
}