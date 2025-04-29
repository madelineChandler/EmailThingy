/*
 * Author:
 * Date:
 * Purpose:
 */

package tabular;

public class FeatureProcessor {
    private String[] top_ten; // top 10 words
    private int emailCount;
    private int totalWordLength;
    private int avgWordLength = -1;
    
    public FeatureProcessor() {} // default constructor

    /*
     * @param email - the email to process; used in a loop of the dataset
     * calculates: avg word length of the email,
     * top ten words, and
     */
    public void processEmail(String email) {
      ++emailCount;
      totalWordLength += email.length();
      /* TODO: implement top ten words */
    }
    
    public void calculateAvgLength() {
      avgWordLength = totalWordLength/emailCount;
    }

    /* returns average word length of the emails */
    public int getAvgWordLength() {
      if (avgWordLength == -1) calculateAvgLength(); // in case it hasn't been calculated yet
      return avgWordLength;
    }
}
