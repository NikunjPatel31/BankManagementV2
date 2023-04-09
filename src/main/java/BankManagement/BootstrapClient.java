package BankManagement;

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
                int accountID = -1;

                int customerID = -1;

                try
                {
                    System.out.println("Enter a choice: ");

                    System.out.println("1. Login");

                    System.out.println("2. Create account");

                    System.out.print("Choice: ");

                    int choice = Integer.parseInt(reader.readLine());

                    switch (choice)
                    {
                        case 1 ->
                        // login...
                        {
                            JSONObject request = login();

                            // send request to server for login...

                            printWriter.println(request);

                            printWriter.flush();

                            // response for login from server...
                            JSONObject response = new JSONObject(serverReader.readLine());

                            if (response.get("Status").toString().equals("ok"))
                            {
                                System.out.println(Constant.UNDERSCORE_SEQ);

                                System.out.println("Login successful");

                                System.out.println(Constant.UNDERSCORE_SEQ);

                                accountID = Integer.parseInt(response.get("AccountID").toString());

                                customerID = Integer.parseInt(response.get("CustomerID").toString());

                                System.out.println("Response: "+response);

                                options(socket, accountID, customerID, printWriter, serverReader);
                            } else
                            {
                                System.out.println(Constant.ASTERISK_SEQ+"\n"+response.get("Message")+"\n"+Constant.ASTERISK_SEQ);
                            }
                        }
                        case 2 ->
                        {
                            // create account...
                            try
                            {
                                JSONObject request = getUserDetails();

                                if (request != null)
                                {
                                    printWriter.println(request);

                                    printWriter.flush();

                                    System.out.println("Waiting for response...");

                                    System.out.println(request);

                                    String str = serverReader.readLine();

                                    JSONObject response = new JSONObject(str);

                                    System.out.println("Response from server: "+response);

                                    if (response.get("Status").toString().equals("ok"))
                                    {
                                        // new customer registered
                                        System.out.println(Constant.UNDERSCORE_SEQ);

                                        System.out.println(response.get("Message"));

                                        System.out.println("Your customerID: "+response.get("CustomerID"));

                                        System.out.println(Constant.UNDERSCORE_SEQ);

                                        request = getAccountDetails();

                                        request.put("CustomerID", response.get("CustomerID"));

                                        request.put("Operation", "createAccount");

                                        // sending account details for opening new account...
//                                        socket.send(account.toString().getBytes());

                                        printWriter.println(request);

                                        printWriter.flush();

                                        System.out.println("Request for create account send");

                                        // response from server regarding creating of new account
                                        response = new JSONObject(serverReader.readLine());

                                        if (response.get("Status").toString().equals("ok"))
                                        {
                                            // we will need this accountID when will need to check for balance...
                                            accountID = Integer.parseInt(response.get("AccountID").toString());

                                            System.out.println(Constant.UNDERSCORE_SEQ);

                                            System.out.println(response.get("Message"));

                                            System.out.println("Your accountID: "+response.get("AccountID"));

                                            System.out.println(Constant.UNDERSCORE_SEQ);

                                            // show options...
                                            options(socket, accountID, customerID, printWriter, serverReader);
                                        }
                                        else
                                        {
                                            System.out.println(Constant.ASTERISK_SEQ);

                                            System.out.println(response.get("Message"));

                                            System.out.println(Constant.ASTERISK_SEQ);                                        }
                                    } else
                                    {
                                        // error in creating new customer
                                        System.out.println(Constant.ASTERISK_SEQ);

                                        System.out.println(response.get("Message"));

                                        System.out.println(Constant.ASTERISK_SEQ);
                                    }

                                } else
                                {
                                    // exception thrown...

                                    System.out.println("Error...");
                                }
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                            }
                        }
                        default -> System.out.println("Invalid choice");
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }

//            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

        }
        catch (ConnectException exception)
        {
            System.out.println("Server is not available");
        }
        catch (Exception exception)
        {
//            System.out.println(exception.get());
            exception.printStackTrace();
        }
    }

    public static JSONObject login()
    {
        try
        {
            JSONObject request = new JSONObject();

            {
                System.out.print("CustomerID: ");

                var value = "";
                while ((value = reader.readLine()).isBlank()
                        || !Pattern.matches("\\d+", value))
                {
                    System.out.print("Enter valid customerID: ");
                }

                request.put("CustomerID", value.trim());

                System.out.print("Password: ");

                while ((value = reader.readLine()).isBlank()
                        || !Pattern.matches("[0-9a-zA-Z@#$%]{8,}", value))
                {
                    System.out.print("Enter valid password: ");
                }

                request.put("Password", value.trim());

                request.put("Operation", "login");

                return request;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    public static JSONObject getUserDetails()
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            {
                System.out.print("Name: ");

                var value = "";

                while ((value = reader.readLine()).isBlank())
                {
                    System.out.print("Please enter proper name: ");
                }

                jsonObject.put("Name", value.trim());

                System.out.print("Email: ");

                while ((value = reader.readLine()).isBlank())
                {
                    System.out.print("Please enter proper email: ");
                }

                jsonObject.put("Email", value.trim());

                System.out.print("Contact: ");

                while ((value = reader.readLine()).isBlank()
                        || !Pattern.matches("\\d{10}", value))
                {
                    System.out.print("Please enter proper contact: ");
                }

                jsonObject.put("Contact", value.trim());

                System.out.print("Address: ");

                while ((value = reader.readLine()).isBlank())
                {
                    System.out.print("Please enter proper address: ");
                }

                jsonObject.put("Address", value.trim());

                System.out.print("Password: ");

                while ((value = reader.readLine()).isBlank()
                        || !Pattern.matches("[0-9a-zA-Z@#$%]{8,}", value))
                {
                    System.out.print("Please enter proper password: ");
                }

                jsonObject.put("Password", value.trim());

                jsonObject.put("Operation", "createCustomer");

            }
            return jsonObject;

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    public static JSONObject getAccountDetails()
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            System.out.print("Enter initial balance: ");

            var value = "";

            while ((value = reader.readLine()).isBlank()
                    || !Pattern.matches("\\d+", value)
                    || Integer.parseInt(value) <= 0)
            {
                System.out.println("Enter valid amount: ");
            }

            jsonObject.put("balance", value);

            jsonObject.put("Operation", "createAccount");

            return jsonObject;

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    public static JSONObject getBalance(long accountID)
    {
        try
        {
            var request = new JSONObject();

            request.put("Operation", "check balance");

            request.put("AccountID", accountID);

            return request;
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    public static void options(Socket socket, int accountID, int customerID, PrintWriter printWriter, BufferedReader serverReader)
    {
        try
        {
            while (true)
            {
                System.out.print("Select Operation: \n1. Withdraw\n2. Deposit\n3. Check Balance\n4. Transfer\n5. Exit\nChoice: ");

                int operation = Integer.parseInt(reader.readLine());

                if (operation == 5)
                {
                    break;
                }

                switch (operation)
                {
                    case 1 ->
                    // withdraw
                    {
                        ClientTransactionService.withdraw(serverReader, reader ,printWriter, accountID);
                    }
                    case 2 ->
                    // deposit
                    {
                        ClientTransactionService.deposit( serverReader, reader, printWriter, accountID);
                    }
                    case 3 ->
                    // check balance
                    {
                        ClientTransactionService.checkBalance(serverReader, reader, printWriter, accountID);
                    }
                    case 4 ->
                    // transfer
                    {
                        ClientTransactionService.transfer(serverReader, reader, printWriter, accountID);
                    }
                    default -> System.out.println("Invalid choice ");
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
