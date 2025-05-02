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
    private int totalEmailLength;                        // Sum of word lengths (excluding ignored)
    private int totalWords;
    private int totalWordLength;
    private Set<String> ignoreWords;                    // Set of words to ignore (e.g., stop words)
    private ArrayList<Integer> allLengths = new ArrayList<Integer>();
    private ArrayList<Integer> allAvgWordLengths = new ArrayList<Integer>();

    // Constructor
    public FeatureProcessor() {
        this.emailCount = 0;
        this.totalEmailLength = 0;
        this.ignoreWords = new HashSet<>(Arrays.asList(
            "the", "a", "an", "and", "or", "but", "is", "are", "was", "were",
            "in", "on", "at", "to", "for", "with", "of", "by"
        ));
    }

    // Add a word to the ignore list
    public void addIgnoreWord(String word) {
        ignoreWords.add(word.toLowerCase());
    }

    // Remove a word from the ignore list
    public void removeIgnoreWord(String word) {
        ignoreWords.remove(word.toLowerCase());
    }

    // Get current ignore list
    public Set<String> getIgnoreWords() {
        return ignoreWords;
    }

    // Process an email: count words and update total word length (excluding ignored words)
    public void processEmail(String emailContent) {
        String[] words = emailContent.split("\\s+"); // split each word by spaces
        int wordLengthSum = 0;
        int totalWords = 0;
        int totalWordLength = 0;
        for (String word : words) {
            this.totalWords++; totalWords++;
            this.totalWordLength += word.length(); totalWordLength += word.length();
            String cleanedWord = word.toLowerCase().replaceAll("[^a-z]", "");
            if (!ignoreWords.contains(cleanedWord) && !cleanedWord.isEmpty()) {
                wordLengthSum += cleanedWord.length();
            }
        }

        // Extract all words to update top words
        this.updateWordList(Arrays.asList(words));

        emailCount++;
        totalEmailLength += wordLengthSum;
        allLengths.add(wordLengthSum); // adds to list of all emails lengths
        allAvgWordLengths.add(totalWordLength / totalWords); // adds to list of all top 1 word
        Collections.sort(allLengths);
    }

    // Calculate average word length across all processed emails
    public double getAverageWordLength() {
        if (emailCount == 0) return 0.0;
        return (double) totalEmailLength / emailCount;
    }

    // Update the top ten frequent words based on a list of all words from emails
    public void updateWordList(List<String> allWords) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : allWords) {
            String cleanedWord = word.toLowerCase().replaceAll("[^a-z]", "");
            if (!ignoreWords.contains(cleanedWord) && !cleanedWord.isEmpty()) {
                frequencyMap.put(cleanedWord, frequencyMap.getOrDefault(cleanedWord, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(frequencyMap.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        for (int i = 0; i < 10 && i < sorted.size(); i++) {
            this.topTen[i] = sorted.get(i).getKey();
        }

        for (int i = sorted.size(); i < 10; i++) {
            this.topTen[i] = null;
        }
    }

    // Get top 10 frequent words
    public String[] getTopTen() {
        return topTen;
    }

    public String curTopWord(String[] words) {
        String[] topTen = new String[10];

        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            String cleanedWord = word.toLowerCase().replaceAll("[^a-z]", "");
            if (!ignoreWords.contains(cleanedWord) && !cleanedWord.isEmpty()) {
                frequencyMap.put(cleanedWord, frequencyMap.getOrDefault(cleanedWord, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(frequencyMap.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        for (int i = 0; i < 10 && i < sorted.size(); i++) {
            topTen[i] = sorted.get(i).getKey();
        }

        for (int i = sorted.size(); i < 10; i++) {
            topTen[i] = null;
        }

        return topTen[0];
    }

    // Get total number of emails processed
    public int getEmailCount() {
        return emailCount;
    }

    public ArrayList<Integer> getAllLengths() {
        return allLengths;
    }

    public ArrayList<Integer> allAvgWordLengths() {
        return allAvgWordLengths;
    }

    public String getTopWord() {
        return topTen[0];
    }

    public int getAvgWordLength() {
        return totalWordLength / totalWords;
    }
}

    // Main method for testing
    /* public static void main(String[] args) {
        FeatureProcessor processor = new FeatureProcessor();

        // Simulate a list of words from multiple emails
        List<String> words = Arrays.asList(
            "the", "and", "spam", "filter", "the", "spam", "indicator", "words",
            "email", "process", "email", "filter", "alert", "urgent", "prize"
        );
        processor.updateWordList(words);

        System.out.println("Top Ten Words (ignoring common words): " + Arrays.toString(processor.getTopTen()));

        // Process a sample email
        processor.processEmail("This is a test email for the spam filter process. Claim your prize now!");
        processor.processEmail("An urgent alert: you've won a prize in our email sweepstakes!");

        System.out.println("Email Count: " + processor.getEmailCount());
        System.out.printf("Average Word Length (ignoring stop words): %.2f\n", processor.getAverageWordLength());
    } */