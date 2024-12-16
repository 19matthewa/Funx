import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NotParallell {
    private int counter = 0;

    public static void main(String[] args) {
        NotParallell notParallell = new NotParallell(); 

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
        }

        String fullString = longString.toString();

        int length = fullString.length();
        long startTime = System.currentTimeMillis();  // Start timer

        notParallell.countFun(fullString, 0, length);

        long endTime = System.currentTimeMillis();  // End timer
        System.out.println("Total count: " + notParallell.counter);
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }

    private void countFun(String word, int start, int end) {
        for (int i = start; i < end; i++) {
            if (i + 2 < word.length() && word.substring(i, i + 3).equalsIgnoreCase("Fun")) {
                counter++;
            }
        }
    }
}
