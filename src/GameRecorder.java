import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class GameRecorder {
    private int gameNumber;
    private final String fileName;

    public GameRecorder(String fileName) {
        this.fileName = fileName;
        readLastGameNumber();
    }

    private void readLastGameNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Game #")) {
                    gameNumber = Integer.parseInt(line.split(" ")[1].substring(1));
                }
            }
        } catch (IOException e) {
            // Обработка ошибки чтения файла (если файл не существует или не удалось прочитать)
            e.printStackTrace();
        }
    }

    public void recordGame(GameResult result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Game #" + (++gameNumber) + " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
            writer.newLine();
            writer.write("Загаданная строка: " + result.getSecretNumber());
            writer.newLine();

            for (String guess : result.getGuesses()) {
                writer.write("Запрос: " + guess + " Ответ: " + guess);
                writer.newLine();
            }

            writer.write("Строка была угадана за " + result.getGuesses().size() + " " + getAttemptsWord(result.getGuesses().size()) + ".");
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            // Обработка ошибки записи в файл (если не удалось записать)
            e.printStackTrace();
        }
    }

    private String getAttemptsWord(int attempts) {
        return attempts == 1 ? "попытку" : (attempts >= 2 && attempts <= 4 ? "попытки" : "попыток");
    }
}