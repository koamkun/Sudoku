import java.util.Scanner;
public class Main {
    private static String[] words = {"cat", "cow", "all", "rain", "eye", "cool"};
    private static String word = words[(int) (Math.random() * words.length)];
    private static String asterisk = new String(new char[word.length()]).replace("\0", "*");
    private static long count = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (count < 7 && asterisk.contains("*")) {
            System.out.println("Guess any letter in the word");
            System.out.println(asterisk);
            String guess = sc.next();
            hang(guess);
        }
        sc.close();
    }
    public static void hang(String guess) {
        if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
            System.out.println("Please enter a valid letter.");
            return;
        }
        guess = guess.toLowerCase();
        String newasterisk = "";
        boolean correctGuess = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                newasterisk += guess.charAt(0);
                correctGuess = true;
            } else if (asterisk.charAt(i) != '*') {
                newasterisk += word.charAt(i);
            } else {
                newasterisk += "*";
            }
        }
        if (asterisk.equals(newasterisk)) {
            if (!correctGuess) {
                count++;
                hangmanImage();
            }
        } else {
            asterisk = newasterisk;
        }

        if (asterisk.equals(word)) {
            System.out.println("Correct! YOU WON! The word was " + word);
        }
    }
    public static void hangmanImage() {
        if (count == 1) {
            System.out.println("Wrong guess, try again");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("___|___");
        }
        if (count == 2) {
            System.out.println("Wrong guess, try again");
            System.out.println("   |   O");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("___|___");
        }
        if (count == 3) {
            System.out.println("Wrong guess, try again");
            System.out.println("   |   O");
            System.out.println("   |   |");
            System.out.println("   |");
            System.out.println("___|___");
        }
        if (count == 4) {
            System.out.println("Wrong guess, try again");
            System.out.println("   |   O");
            System.out.println("   |  /|\\");
            System.out.println("   |");
            System.out.println("___|___");
        }
        if (count == 5) {
            System.out.println("Wrong guess, try again");
            System.out.println("   |   O");
            System.out.println("   |  /|\\");
            System.out.println("   |  /");
            System.out.println("___|___");
        }
        if (count == 6) {
            System.out.println("Wrong guess, try again");
            System.out.println("   |   O");
            System.out.println("   |  /|\\");
            System.out.println("   |  / \\");
            System.out.println("___|___");
        }
        if (count == 7) {
            System.out.println("GAME OVER!");
            System.out.println("   |   O");
            System.out.println("   |  /|\\");
            System.out.println("   |  / \\");
            System.out.println("___|___");
            System.out.println("GAME OVER! The word was " + word);
        }
    }
}