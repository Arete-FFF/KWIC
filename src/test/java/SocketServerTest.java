import edu.bupt.socket.SocketServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SocketServerTest {

    @Test
    public void testStartServer() throws IOException {
        // Start the server in a separate thread or process
        SocketServer.startServer();
    }
}// Perform any necessary setup for testing