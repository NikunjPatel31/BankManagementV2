package BankManagement.services;

import BankManagement.Constant;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.regex.Pattern;


public class ClientAuthService
{
    public static void login(BufferedReader serverReader, BufferedReader reader, PrintWriter printWriter)
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

                printWriter.println(request);

                printWriter.flush();

                // response from server
                var serverResponse = serverReader.readLine();

                if (serverResponse == null)
                {
                    // this block will execute when server will go down...
                    System.out.println("Server is not available");

                    System.exit(0);
                }

                JSONObject response = new JSONObject(serverResponse);

                if (response.get("Status").toString().equals("ok"))
                {
                    System.out.println(Constant.UNDERSCORE_SEQ+"\nLogin successful\n"+Constant.UNDERSCORE_SEQ);

                    int accountID = Integer.parseInt(response.get("AccountID").toString());

                    options(reader,accountID, printWriter, serverReader);
                } else
                {
                    System.out.println(Constant.ASTERISK_SEQ+"\n"+response.get("Message")+"\n"+Constant.ASTERISK_SEQ);
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void createAccount(BufferedReader serverReader, BufferedReader reader, PrintWriter printWriter)
    {
        try
        {
            JSONObject request = new JSONObject();
            {
                System.out.print("Name: ");

                var value = "";

                while ((value = reader.readLine()).isBlank())
                {
                    System.out.print("Please enter proper name: ");
                }

                request.put("Name", value.trim());

                System.out.print("Email: ");

                while ((value = reader.readLine()).isBlank())
                {
                    System.out.print("Please enter proper email: ");
                }

                request.put("Email", value.trim());

                System.out.print("Contact: ");

                while ((value = reader.readLine()).isBlank()
                        || !Pattern.matches("\\d{10}", value))
                {
                    System.out.print("Please enter proper contact: ");
                }

                request.put("Contact", value.trim());

                System.out.print("Address: ");

                while ((value = reader.readLine()).isBlank())
                {
                    System.out.print("Please enter proper address: ");
                }

                request.put("Address", value.trim());

                System.out.print("Password: ");

                while ((value = reader.readLine()).isBlank()
                        || !Pattern.matches("[0-9a-zA-Z@#$%]{8,}", value))
                {
                    System.out.print("Please enter proper password: ");
                }

                request.put("Password", value.trim());

                request.put("Operation", "createCustomer");

                printWriter.println(request);

                printWriter.flush();

                var serverResponse = serverReader.readLine();

                if (serverResponse == null)
                {
                    System.out.println("Server is not available");

                    System.exit(0);
                }

                JSONObject response = new JSONObject(serverResponse);

                System.out.println("Response from server: "+response);

                var customerID = response.get("CustomerID");

                if (response.get("Status").toString().equals("ok"))
                {
                    // new customer registered
                    System.out.println(Constant.UNDERSCORE_SEQ+"\n"+response.get("Message")+"\nYour customerID: "+response.get("CustomerID")+"\n"+Constant.UNDERSCORE_SEQ);

                    request.clear();

                    do
                    {
                        System.out.print("Enter initial balance: ");

                        while ((value = reader.readLine()).isBlank()
                                || !Pattern.matches("\\d+", value)
                                || Integer.parseInt(value) <= 0)
                        {
                            System.out.println("Enter valid amount: ");
                        }

                        request.put("balance", value);

                        request.put("Operation", "createAccount");

                        request.put("CustomerID", customerID);

                        request.put("Operation", "createAccount");

                        printWriter.println(request);

                        printWriter.flush();

                        // response from server regarding creating of new account
                        serverResponse = serverReader.readLine();

                        if (serverResponse == null)
                        {
                            // this block will execute when server will go down...
                            System.out.println("Server is not available");

                            System.exit(0);
                        }

                        response = new JSONObject(serverResponse);

                        if (response.get("Status").toString().equals("ok"))
                        {
                            // we will need this accountID when will need to check for balance...
                            var accountID = Integer.parseInt(response.get("AccountID").toString());

                            System.out.println(Constant.UNDERSCORE_SEQ+"\n"+response.get("Message")+"\nYour accountID: "+response.get("AccountID")+"\n"+Constant.UNDERSCORE_SEQ);

                            // show options...
                            options(reader,accountID, printWriter, serverReader);
                        }
                        else
                        {
                            System.out.println(Constant.ASTERISK_SEQ+response.get("Message")+Constant.ASTERISK_SEQ);
                        }
                    } while (response.get("Status").toString().equals("error"));
                }
                else
                {
                    // error in creating new customer
                    System.out.println(Constant.ASTERISK_SEQ+response.get("Message")+Constant.ASTERISK_SEQ);
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void options(BufferedReader reader, int accountID, PrintWriter printWriter, BufferedReader serverReader)
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
