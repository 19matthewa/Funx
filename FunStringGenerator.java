import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FunStringGenerator {
    public static void main(String[] args) {
        // File path for output
        String filePath = "fun.txt";
        
        // Size of the file in characters
        long fileSize = 100_000_000L; // 100 million characters
        
        // Probability of inserting "Fun"
        double funProbability = 0.01; // 1% chance of "Fun"
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Random random = new Random();
            
            // Track total characters written
            long charsWritten = 0;
            
            while (charsWritten < fileSize) {
                // Decide whether to write "Fun" or a random character
                if (random.nextDouble() < funProbability) {
                    writer.write("Fun");
                    charsWritten += 3;
                } else {
                    // Generate a random lowercase letter
                    char randomChar = (char) (random.nextInt(26) + 'a');
                    writer.write(randomChar);
                    charsWritten++;
                }
                
                // Optional: Add a newline periodically to prevent extremely long lines
                if (charsWritten % 1000 == 0) {
                    writer.newLine();
                }
            }
            
            System.out.println("File generated: " + filePath);
            System.out.println("Total file size: " + charsWritten + " characters");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}