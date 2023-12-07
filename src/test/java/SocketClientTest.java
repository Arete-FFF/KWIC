import edu.bupt.socket.SocketClient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class SocketClientTest {

    @Test
    public void testClient() {
        // Simulate user input
        String simulatedInput = "#\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        // Start the client
        SocketClient.startClient();


    }
}
