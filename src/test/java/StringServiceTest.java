import edu.bupt.service.StringService;
import edu.bupt.service.impl.StringServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringServiceTest {

    @Test
    public void testStringList() {
        StringService stringService = new StringServiceImpl();
        List<String> stringList = stringService.getStringList();
        System.out.println(stringList);
        // Add assertions based on the expected behavior of getStringList()
        // For example:
        // assertEquals(3, stringList.size());
        // assertTrue(stringList.contains("exampleString"));
    }

    @Test
    public void testAddInputString() {
        StringService stringService = new StringServiceImpl();
        // Perform the addInputString operation
        stringService.addInputString("testInputString");
        // Add assertions based on the expected behavior after adding an input string
        // For example:
        // List<String> stringList = stringService.getStringList();
        // assertTrue(stringList.contains("testInputString"));
    }

    @Test
    public void testAddOutputString() {
        StringService stringService = new StringServiceImpl();
        // Perform the addOutputString operation
        stringService.addOutputString("testOutputString");
        // Add assertions based on the expected behavior after adding an output string
        // For example:
        // List<String> stringList = stringService.getStringList();
        // assertTrue(stringList.contains("testOutputString"));
    }

    @Test
    public void testDeleteOutput() {
        StringService stringService = new StringServiceImpl();
        // Perform the deleteOutput operation
        stringService.deleteOutput();
        // Add assertions based on the expected behavior after deleting output
        // For example:
        // List<String> stringList = stringService.getStringList();
        // assertTrue(stringList.isEmpty());
    }
}
