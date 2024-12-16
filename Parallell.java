import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Parallell {
    private volatile int counter = 0;

    public static void main(String[] args) {
        Parallell parallell = new Parallell(); 
        ExecutorService executor = Executors.newFixedThreadPool(4);

        StringBuilder longString = new StringBuilder();

        try (FileReader fileReader = new FileReader("fun.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                longString.append(line).append("\n");
            }

            System.out.println("Content successfully read into longString.");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String fullString = longString.toString();

        int length = fullString.length();
        int quarter = length / 4;

        long startTime = System.currentTimeMillis();  // Start timer

        // Submit separate tasks for parallel counting
        executor.submit(() -> parallell.countFun(fullString, 0, quarter));
        executor.submit(() -> parallell.countFun(fullString, quarter, 2 * quarter));
        executor.submit(() -> parallell.countFun(fullString, 2 * quarter, 3 * quarter));
        executor.submit(() -> parallell.countFun(fullString, 3 * quarter, length));

        executor.shutdown();

        try {
            // Wait for all tasks to finish with a timeout
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        long endTime = System.currentTimeMillis();  // End timer
        System.out.println("Total count: " + parallell.counter);
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }

    private void countFun(String word, int start, int end) {
        int localCounter = 0;
        for (int i = start; i < end; i++) {
            if (i + 2 < word.length() && word.substring(i, i + 3).equalsIgnoreCase("Fun")) {
                localCounter++;
            }
        }
        
        // Use atomic increment to avoid excessive synchronization
        synchronized (this) {
            counter += localCounter;
        }
    }
}