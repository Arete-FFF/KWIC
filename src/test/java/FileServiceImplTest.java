import edu.bupt.service.FileService;
import edu.bupt.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class FileServiceImplTest {
    @Test
    void testFileOutput() {
        // Arrange
        List<String> resultList = Arrays.asList("Line 1", "Line 2", "Line 3");
        String outputFileName = "test_output.txt";
        FileService fileService = new FileServiceImpl();

        // Act
       fileService.fileOutput(resultList, outputFileName);

        // Assert (You may add assertions based on the expected behavior)
        // For example, you can check if the file exists or check its content.
    }

    @Test
    void testFileInput() {
        // Arrange
        String inputFileName = "input.txt";
        FileService fileService = new FileServiceImpl();

        // Act
        List<String> resultList = fileService.fileInput();

        // Assert (You may add assertions based on the expected behavior)
        // For example, you can check if the file exists or check its content.
    }

}
