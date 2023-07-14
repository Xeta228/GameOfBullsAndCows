import java.util.List;

    class GameResult {
        private final String secretNumber;
        private final List<String> guesses;

        public GameResult(String secretNumber, List<String> guesses) {
            this.secretNumber = secretNumber;
            this.guesses = guesses;
        }

        public String getSecretNumber() {
            return secretNumber;
        }

        public List<String> getGuesses() {
            return guesses;
        }
    }

