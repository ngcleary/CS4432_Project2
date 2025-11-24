import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {

        Index index = new Index();
        
        // Create scanner
        Scanner scanner = new Scanner(System.in);
        
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
                index.CREATE();
                break;
            default:
                System.out.println("Unknown command: " + functionInput);
                break;
            }
                    
        }

    }
}