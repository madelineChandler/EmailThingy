/*
 * Author:
 * Date:
 * Purpose:
 * Note: ngl I just copied the main file from hw 4 and I'll
 * just edit it later once we finish the rest of the files
 */

import java.util.ArrayList;
import tabular.*;

public class ModelDemo {
    public static void main(String[] args) {
      Gui window = new Gui(); // creates object for the GUI

      System.out.print("Enter file name: ");
        String fileName = "spam_or_not_spam.csv";
        DataSet ds = new DataSet(fileName);
        LinearModel lm = new LinearModel(0.005,ds);
      System.out.println("Trained Model: " + lm);
      System.out.println("Model Error: " + lm.sumSquaredError());
      ArrayList<DataRow> rows = ds.getRows();
      if(rows == null) {
          System.out.println("Data Set has no rows. stopping!");
          System.exit(10);
      }
      System.out.println("prediction, actual, error");
      for(DataRow row : rows) {
        double predict = lm.predict(row.getIndependentVariables());
        System.out.print(predict);
        System.out.print(",");
        System.out.print(row.getDependentVariable());
        System.out.print(",");
        System.out.println(predict - row.getDependentVariable());
      }
    }
}
