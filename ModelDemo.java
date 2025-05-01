/*
 * Author:
 * Date:
 * Purpose:
 * Note: ngl I just copied the main file from hw 4 and I'll
 * just edit it later once we finish the rest of the files
 */

import java.util.ArrayList;
import tabular.*;
import gui.*;

public class ModelDemo {
    public static void main(String[] args) {
      // MainFrame frame = new MainFrame();
      // frame.initialize(); // initializes gui
      
      String fileName = "spam_or_not_spam.csv";
      DataSet ds = new DataSet(fileName);
      
      System.out.println("Trained Model: ");
      System.out.println("Model Error: ");
      ArrayList<DataRow> rows = ds.getRows();
      if(rows == null) {
          System.out.println("Data Set has no rows. stopping!");
          System.exit(10);
      }
      System.out.println("prediction, actual, error");
      for(DataRow row : rows) {
        System.out.println("");
        System.out.print(",");
        System.out.print(row.getDependentVariable());
        System.out.print(",");
        System.out.println(row.getDependentVariable());
      }
    }
}
