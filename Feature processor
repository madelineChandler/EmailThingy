import java.util.*;

public class FeatureProcessor {

    private String[] topTen = new String[10];
    private int emailCount;
    private int totalWordLength;

    public FeatureProcessor() {
        this.emailCount = 0;
        this.totalWordLength = 0;
    }

    // Updating the top ten spam indicator words
    public void updateWordList(List<String> allWords) {
        // Simulate scoring or frequency-based word picking (e.g., top 10 frequent words)
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : allWords) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        // Get top 10 words by frequency
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(frequencyMap.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        for (int i = 0; i < 10 && i < sorted.size(); i++) {
            topTen[i] = sorted.get(i).getKey();
        }

        // Fill remaining slots with null if less than 10 words
        for (int i = sorted.size(); i < 10; i++) {
            topTen[i] = null;
        }
    }

    // Adds a new email and updates internal stats
    public void processEmail(String emailContent) {
        String[] words = emailContent.split("\\s+");
        int wordLengthSum = 0;
        for (String word : words) {
            wordLengthSum += word.length();
        }

        emailCount++;
        totalWordLength += wordLengthSum;
    }

    // Returns avg word length across all emails
    public double getAverageWordLength() {
        if (emailCount == 0) return 0;
        return (double) totalWordLength / emailCount;
    }

    // Getter for topTen
    public String[] getTopTen() {
        return topTen;
    }

    // Getter for emailCount
    public int getEmailCount() {
        return emailCount;
    }
}
