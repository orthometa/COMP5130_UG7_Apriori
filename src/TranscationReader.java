import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Class for import data from the disk or database
 *
 * @path: relative file path to the program file.
 */
public class TranscationReader {
    private String filePath;

    public TranscationReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Read the data in the txt file
     *
     * @return ArrayList contains each line of a given file
     */
    public ArrayList<String> importData() {
        File dataFile = new File(filePath);
        ArrayList<String> resultList = new ArrayList<>();
        try {
            if (dataFile.isFile() && dataFile.exists()) {
                InputStreamReader dataReader = new InputStreamReader(new FileInputStream(dataFile), "UTF-8");
                BufferedReader bufferDataReader = new BufferedReader(dataReader);
                String lineBuffer;
                while ((lineBuffer = bufferDataReader.readLine()) != null) {
                    resultList.add(lineBuffer);
                }
            } else {
                System.err.println("Can not find given file!");
            }
        } catch (Exception e) {
            System.err.println("Error occured during reading file!");
            e.printStackTrace();
        }
        return resultList;
    }
}
