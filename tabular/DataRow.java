package tabular;

/*
 * Author: Madeline Chandler
 * Date: 4/28/25
 * Purpose: Store a row of info from .csv file
 */


public class DataRow {
    private String y; // independent variable (email)
    private int x; // dependent variable (ham/spam)

    /**
     * @param y the dependent variable
     * @param x the array of independent variables
     */
    public DataRow(String y, double x)
    {
        this.y = y;
        this.x = x;
    }

    /**
     * @return the dependent variable
     */
    public double getDependentVariable() {
        return y;
    }

    /**
     * @return the array of independent variables
     */
    public double getIndependentVariable() {
        return x;
    }
}
