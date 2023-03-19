package bullscows;
import java.util.Scanner;
import java.util.Random;


public class Main {
    public static Random random = new Random();
    public static int cows;
    public static int bulls;
    public static String sCode;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        String sizee = scanner.next();
        System.out.println("Input the number of possible symbols in the code:");
        int symbols = scanner.nextInt();
        if (!isNumeric(sizee)) {
            System.out.println("Error: " + sizee + " isn't a valid number.");
            System.exit(0);
        }
        int size = Integer.valueOf(sizee);
        if (symbols < size || (size == 0 && symbols != 0)) {
            System.out.println("Error: it's not possible to generate a code with a length of " + size + " with " + symbols + " unique symbols.");
            System.exit(0);
        }
        if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        } else if (symbols > 10 && symbols <= 36) {
            makeStringCode(size, symbols);
            String answer;
            int turn = 1;
            final String code = secCode(size, symbols);
            int copySize = size - 1;
            String secret = "*";
            while (copySize != 0) {
                secret += "*";
                copySize--;
            }
            clues(size, symbols, secret);
            System.out.println("Okay, let's start a game!");
            do {
                System.out.println("Turn " + turn +". Answer:");
                answer = scanner.next();
                bulls(code, answer);
                if (bulls < size) {
                    cows(code, answer);
                    cows = cows - bulls;
                    display(cows, bulls, size, code);
                } else {
                    display(cows, bulls, size, code);
                    break;
                }
                turn++;
            } while (!answer.equals(code));
        } else if (symbols <= 10) {
            int randomNumber = random.nextInt(999999999);
            String secretCode = String.valueOf(randomNumber);
            String answer;
            int turn = 1;
            final String code = secretCode(secretCode, randomNumber, size);
            int copySize = size - 1;
            String secret = "*";
            while (copySize != 0) {
                secret += "*";
                copySize--;
            }
            System.out.println("The secret is prepared:" + secret + " (0-" + (symbols - 1) + ")");
            System.out.println("Okay, let's start a game!");
            do {
                System.out.println("Turn " + turn +". Answer:");
                answer = scanner.next();
                bulls(code, answer);
                if (bulls < size) {
                    cows(code, answer);
                    cows = cows - bulls;
                    display(cows, bulls, size, code);
                } else {
                    display(cows, bulls, size, code);
                    break;
                }
                turn++;
            } while (!answer.equals(code));
        }
        scanner.close();
    }
    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    static String secretCode(String secretCode, int randomNumber, int size) {
        boolean check = false;
        while (!check){
            if (uniqueNumbers(secretCode) && secretCode.length() == size) {
                check = true;
                break;
            } else {
                check = false;
                randomNumber = random.nextInt(999999999);
                secretCode = String.valueOf(randomNumber);
            }

        }
        return secretCode;

    }

    static boolean uniqueNumbers(String secretCode) {
        for (int i = 0; i < secretCode.length() - 1; i++) {
            for (int j = i + 1; j < secretCode.length(); j++) {
                if (secretCode.charAt(i) == secretCode.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
    static String makeStringCode(int size, int symbols) {
        String abc123 = "abcdefghijklmnopqrstuvwxyz0123456789".substring(0, symbols - 10);
        String[] secretCode = new String[size];
        for (int i = 0 ; i < size; i++) {
            secretCode[i] = String.valueOf(abc123.charAt(random.nextInt(abc123.length())));
        }
        sCode = String.join("", secretCode);
        return sCode;
    }
    static String secCode(int size, int symbols) {
        boolean check = false;
        while (!check){
            if (uniqueSymbols(sCode)) {
                check = true;
                return sCode;
            } else {
                check = false;
                makeStringCode(size, symbols);
            }

        }
        return sCode;
    }

    static boolean uniqueSymbols(String sCode) {
        for (int i = 0; i < sCode.length() - 1; i++) {
            for (int j = i + 1; j < sCode.length(); j++) {
                if (sCode.charAt(i) == sCode.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
    static void clues(int size, int symbols, String secret) {
        char ch = "abcdefghijklmnopqrstuvwxyz0123456789".charAt(symbols- 11);
        System.out.println("The secret is prepared:" + secret + "(0-9, " + "a-" + ch + ").");
    }
    static void bulls(String secretCode, String answer) {
        bulls = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            char ch1 = secretCode.charAt(i);
            char ch2 = answer.charAt(i);
            if (ch1 == ch2) {
                bulls++;
            }
        }

    }
    static void cows(String secretCode, String answer) {
        cows = 0;
        for (int j = 0; j < secretCode.length(); j++) {
            if (secretCode.contains(String.valueOf(answer.charAt(j)))) {
                cows++;
            }
        }
    }

    static void display(int cows, int bulls, int size, String secretCode) {
        if (cows != 0 && bulls != 0) {
            System.out.println("Grade:" + bulls +" bull and " + cows + " cow.");
        } else if (cows != 0 && bulls == 0) {
            if (cows == 1){
                System.out.println("Grade: " + cows + " cow.");
            } else {
                System.out.println("Grade: " + cows + " cows.");
            }
        } else if (cows == 0 && bulls != 0) {
            if (bulls == 1 && size != 1) {
                System.out.println("Grade: " + bulls + " bull.");
            } else if (bulls > 1 && bulls < size) {
                System.out.println("Grade: " + bulls + " bulls.");
            } else if (bulls == size) {
                System.out.println("Grade: " + bulls + " bulls.");
                System.out.println("Congrats! The secret code is " + secretCode);
            }
        } else  {
            System.out.println("Grade: None.");
        }
    }

}
