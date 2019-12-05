
public class Main {

    public static void main(String[] args) {
        TranscationReader reader = new TranscationReader("test.dat");
        Apriori apriori = new Apriori(reader.importData(), 0.01);
        apriori.aprioriProcess();
    }
}
