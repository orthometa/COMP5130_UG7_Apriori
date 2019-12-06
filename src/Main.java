import java.util.Scanner; 

public class Main {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System. in);
        System.out.println("Please input a path for data set");
        String filePath = inputScanner.nextLine();
        System.out.println("Please input the minimum support for data set");
        Double minSupport = inputScanner.nextDouble();
        inputScanner.close();

        TranscationReader reader = new TranscationReader(filePath);
        Apriori apriori = new Apriori(reader.importData(), minSupport);
        apriori.aprioriProcess();

        // TranscationReader reader = new TranscationReader("test.dat");
        // Apriori apriori = new Apriori(reader.importData(), 0.05);
        // apriori.aprioriProcess();
    }
}
