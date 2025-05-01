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
    public DataRow(String y, int x)
    {
        this.y = y;
        this.x = x;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return y;
    }

    /**
     * @return whether email is ham or spam
     */
    public String getLabel() {
        if (x == 0) 
            return "ham";
        return "spam";
    }


}
