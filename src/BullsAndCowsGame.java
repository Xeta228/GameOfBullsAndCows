import java.util.*;

class BullsAndCowsGame {
    private static final int NUMBER_LENGTH = 4;
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String RECORDS_FILE = "game_records.txt";

    private String secretNumber;
    private List<String> guesses;
    private GameRecorder gameRecorder;

    public BullsAndCowsGame() {
        gameRecorder = new GameRecorder(RECORDS_FILE);
    }

    private String generateSecretNumber() {
        Set<Integer> digits = new HashSet<>();
        while (digits.size() < NUMBER_LENGTH) {
            int digit = RANDOM.nextInt(10);
            digits.add(digit);
        }

        StringBuilder sb = new StringBuilder();
        for (int digit : digits) {
            sb.append(digit);
        }

        return sb.toString();
    }

    public void playGame() {
        secretNumber = generateSecretNumber();
        guesses = new ArrayList<>();
        int attempts = 0;
        boolean guessed = false;

        while (!guessed) {
            System.out.print("Введите число: ");
            String guess = SCANNER.nextLine();

            if (guess.length() != NUMBER_LENGTH) {
                System.out.println("Пожалуйста, введите число из " + NUMBER_LENGTH + " цифр.");
                continue;
            }

            if (!isValidGuess(guess)) {
                System.out.println("Число должно содержать только уникальные цифры.");
                continue;
            }

            guesses.add(guess);
            attempts++;

            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < NUMBER_LENGTH; i++) {
                char digit = guess.charAt(i);

                if (digit == secretNumber.charAt(i)) {
                    bulls++;
                } else if (secretNumber.contains(String.valueOf(digit))) {
                    cows++;
                }
            }

            String bullsFormat;
            if(bulls==0){
                bullsFormat = "быков";
            }
            else if (bulls==1){
                bullsFormat = "бык";
            }
            else{
                bullsFormat = "быка";
            }

            String cowsFormat;
            if(cows == 0){
                cowsFormat = "коров";
            }
            else if (cows ==1 ){
                cowsFormat = "корова";
            }
            else {
                cowsFormat = "коровы";
            }

            System.out.println(bulls + " " + bullsFormat + " " +
                    cows + " " + cowsFormat);

            if (bulls == NUMBER_LENGTH) {
                System.out.println("Поздравляем! Вы угадали число " + secretNumber +
                        " за " + attempts + " " + getAttemptsWord(attempts) + ".");
                guessed = true;

                GameResult result = new GameResult(secretNumber, guesses);
                gameRecorder.recordGame(result);
            }
        }
    }

    private boolean isValidGuess(String guess) {
        Set<Character> uniqueDigits = new HashSet<>();
        for (char digit : guess.toCharArray()) {
            if (!Character.isDigit(digit) || uniqueDigits.contains(digit)) {
                return false;
            }
            uniqueDigits.add(digit);
        }
        return true;
    }

    private String getAttemptsWord(int attempts) {
        return attempts == 1 ? "попытку" : (attempts >= 2 && attempts <= 4 ? "попытки" : "попыток");
    }
}
