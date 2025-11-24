import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
        
        // Create scanner
        Scanner scanner = new Scanner(System.in);

        //make query object
        Query query = new Query();
        
        //start input loop
        while (true) {
        System.out.print("The program is ready for the next command: ");
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
                query.selectHash("1403");
                break;
            default:
                System.out.println("Unknown command: " + functionInput);
                break;
            }
                    
        }

    }
}