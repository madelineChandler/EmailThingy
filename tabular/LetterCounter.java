package tabular;

public class LetterCounter {
    private int uppercaseCount;
    private int lowercaseCount;

    public void reset() {
        uppercaseCount = 0;
        lowercaseCount = 0;
    }

    public void countLetters(String email) {
        reset();
        for (char c : email.toCharArray()) {
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            }
        }
    }

    public int getUppercaseCount() {
        return uppercaseCount;
    }

    public int getLowercaseCount() {
        return lowercaseCount;
    }
}