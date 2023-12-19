import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sumarokov.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class mainTests {

    @Test
    public void main() {
        Main.main(new String[]
                {"C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\files",
                        "C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\files"
                });
        try (Scanner resultScanner = new Scanner
                (new File("C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\files\\result.txt"));
             Scanner expectedScanner = new Scanner(
                     new File("C:\\Users\\Mikhail\\IdeaProjects\\File_concatenation\\src\\test\\resources\\files\\expected.txt"));) {

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
