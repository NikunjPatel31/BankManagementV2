package BankManagement;

import java.net.ServerSocket;
import java.net.Socket;

public class BankServer
{
    public static void start()
    {
        try (ServerSocket server = new ServerSocket(6001))
        {
            while (true)
            {
                Socket socket = server.accept();

                System.out.println("Connection established: "+socket.getRemoteSocketAddress());

                new Thread(new ClientHandler(socket)).start();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
