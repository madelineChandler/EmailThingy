package tabular;
/**
 * Author: Madeline Chandler
 * Date: 4/28/25
 * Purpose: Store multiple rows of data from a csv file
 */

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataSet {
    private ArrayList<DataRow> data = new ArrayList<DataRow>();
    private ArrayList<DataRow> toTrain = new ArrayList<DataRow>();
    private ArrayList<DataRow> toTest = new ArrayList<DataRow>();

    /**
     * @param filename
     * The name of the file to read the data set from
     */
    public DataSet(String filename)
    {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.nextLine(); // skip first line

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(","); // Split by comma

                String y = (values[0]); // email
                int x = Integer.parseInt(values[1]); // ham or spam

                this.data.add(new DataRow(y, x));
            }
            split(); // splits dataset (80/20)
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
    
    private void split() {
      for (int i = 0; i < data.size(); i++) {
         if (i <= data.size()*.8) {
            // 80% of data to train
            this.toTrain.add(data.get(i));
         } else {
            // remaining 20% of data for testing
            this.toTest.add(data.get(i));
         }
      }
    }

    /**
     * @return the list of rows
     */
    public ArrayList<DataRow> getRows() {
        return data;
    }
    
    public ArrayList<DataRow> getTrainingData() {
      return toTrain;
    }
    
    public ArrayList<DataRow> getTestingData() {
      return toTest;
    }
}
