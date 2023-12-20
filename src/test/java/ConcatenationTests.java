import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sumarokov.Concatenation;
import ru.sumarokov.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConcatenationTests {

    @Test
    public void concatenation() {
        new Concatenation().concatenation(
                "C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\concatenation_test\\files",
                        "C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\concatenation_test"
                );
        try (Scanner resultScanner = new Scanner
                (new File("C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\concatenation_test\\result.txt"));
             Scanner expectedScanner = new Scanner(
                     new File("C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\concatenation_test\\expected.txt"));) {

            while (resultScanner.hasNext() || expectedScanner.hasNext()) {
                if (!resultScanner.nextLine().equals(expectedScanner.nextLine())) {
                    Assertions.fail();
                }
            }
        } catch (FileNotFoundException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
