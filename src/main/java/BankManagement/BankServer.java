package BankManagement;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankServer
{
    public static void start()
    {
        try (ServerSocket server = new ServerSocket(6001))
        {
//            ExecutorService executorService = Executors.newFixedThreadPool()
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
