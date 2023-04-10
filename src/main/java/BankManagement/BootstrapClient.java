package BankManagement;

import BankManagement.services.ClientAuthService;
import BankManagement.services.ClientTransactionService;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.regex.Pattern;

public class BootstrapClient
{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args)
    {
        try (Socket socket = new Socket("localhost", 6001); PrintWriter printWriter = new PrintWriter(socket.getOutputStream()); BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            System.out.println("Connection established");

            while (true)
            {
                int customerID = -1;

                try
                {
                    System.out.println("Enter a choice: ");

                    System.out.println("1. Login");

                    System.out.println("2. Create account");

                    System.out.print("Choice: ");

                    var choice = reader.readLine();

                    switch (choice)
                    {
                        case "1" ->
                            // login...
                            ClientAuthService.login(serverReader, reader, printWriter);
                        case "2" ->
                            // create account...
                            ClientAuthService.createAccount(serverReader, reader, printWriter);
                        default -> System.out.println("Invalid choice");
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
        catch (ConnectException exception)
        {
            System.out.println("Server is not available");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
