/*
 * Author: Madeline Chanlder and Shelby Dussintyl
 * Date:4/30/25
 * Purpose: Processes emails to compute average word length and identify top ten frequent (non-ignored) words.
 */
package tabular;

import java.util.*;

public class FeatureProcessor {

    private String[] topTen = new String[10];           // Top 10 frequent words
    private int emailCount;                             // Number of emails processed
    private int totalEmailLength;                       // Sum of word lengths (excluding ignored)
    private Set<String> ignoreWords;                    // Set of words to ignore (stop words)
    private ArrayList<Integer> allLengths = new ArrayList<>();
    private ArrayList<Integer> allAvgWordLengths = new ArrayList<>();
    private ArrayList<String> emails = new ArrayList<>(); // Stores processed emails

    // Constructor
    public FeatureProcessor() {
        this.emailCount = 0;
        this.totalEmailLength = 0;
        this.ignoreWords = new HashSet<>(Arrays.asList(
            "the", "a", "an", "and", "or", "but", "is", "are", "was", "were",
            "in", "on", "at", "to", "for", "with", "of", "by"
        ));
    }

    // Process an email: count words and update features
    public void processEmail(String emailContent) {
        emails.add(emailContent); // Store email for future reference
        String[] words = emailContent.split("\\s+");
        int wordLengthSum = 0;
        int totalWords = words.length; 
        int totalWordLength = 0;

        for (String word : words) {
            totalWordLength += word.length();
            String cleanedWord = word.toLowerCase().replaceAll("[^a-z]", "");
            if (!ignoreWords.contains(cleanedWord) && !cleanedWord.isEmpty()) {
                wordLengthSum += cleanedWord.length();
            }
        }

        emailCount++;
        totalEmailLength += wordLengthSum;
        allLengths.add(wordLengthSum);

        // Avoid division by zero
        if (totalWords > 0) {
            allAvgWordLengths.add(totalWordLength / totalWords);
        } else {
            allAvgWordLengths.add(0);
        }

        Collections.sort(allLengths);
        updateWordList(Arrays.asList(words));
    }

    // Get processed email contents
    public ArrayList<String> getEmails() {
        return emails;
    }

    public ArrayList<Integer> getAllLengths() {
        return allLengths;
    }

    public ArrayList<Integer> getAllAvgWordLengths() {
        return allAvgWordLengths;
    }
}
