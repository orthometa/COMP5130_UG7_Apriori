import java.util.Scanner; 

public class Main {

    public static void main(String[] args) {
        // Get the parameters for Apriori.
        Scanner inputScanner = new Scanner(System. in);
        System.out.println("Please input a path for data set");
        String filePath = inputScanner.nextLine();
        System.out.println("Please input the minimum support for data set");
        double minSupport = inputScanner.nextDouble();
        inputScanner.close();

        // Do the calculations
        TransactionReader reader = new TransactionReader(filePath);
        if (reader.isValidFile()) {
            Apriori apriori = new Apriori(reader.importData(), minSupport);
            apriori.aprioriProcess();
        } else {
            System.out.println("The file path you inputted is not valid, please try again!");
        }
    }
}
