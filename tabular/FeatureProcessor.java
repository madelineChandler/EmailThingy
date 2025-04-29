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
    
    public FeatureProcessor() {}
    
    public void processEmail(String email) {
      ++emailCount;
      totalWordLength += email.length();
      
    }
    
    public void calculateAvgLength() {
      avgWordLength = totalWordLength/emailCount;
    }
    
    public int getAvgWordLength() {
      if (avgWordLength == -1) calculateAvgLength();
      return avgWordLength;
    }
}
