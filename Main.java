import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String csvFile = "results.csv";
        Random random = new Random();

        try (FileWriter writer = new FileWriter(csvFile)) {
            int minSize = 100;
            int maxSize = 10000;
            int steps = 50;
            double stepSize = (double) (maxSize - minSize) / (steps - 1);

            for (int i = 0; i < steps; i++) {
                int currentSize = (int) (minSize + i * stepSize);
                MyHashTable<Integer, Integer> hashTable = new MyHashTable<>();

                for (int j = 0; j < currentSize; j++) {
                    int randomKey = random.nextInt(100_000_000);
                    hashTable.put(randomKey, randomKey);
                }

                int operationsCount = 10000;
                long startTime = System.nanoTime();

                for (int j = 0; j < operationsCount; j++) {
                    int chance = random.nextInt(100); // число от 0 до 99
                    int randomKey = random.nextInt(100_000_000);

                    if (chance < 40) {
                        // 40% процентов — put
                        hashTable.put(randomKey, randomKey);
                    } else if (chance < 80) {
                        // 40% процентов — get (от 40 до 79)
                        hashTable.get(randomKey);
                    } else {
                        // 20% процентов — remove (от 80 до 99)
                        hashTable.remove(randomKey);
                    }
                }

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;

                double averageTime = (double) totalTime / operationsCount;

                writer.write(currentSize + "," + averageTime + "\n");
            }

            System.out.println("Результаты сохранены в файл: " + csvFile);

        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}

