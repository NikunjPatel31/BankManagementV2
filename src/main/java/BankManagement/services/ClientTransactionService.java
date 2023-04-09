package BankManagement.services;

import BankManagement.Constant;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class ClientTransactionService
{
    public static void withdraw(BufferedReader serverReader, BufferedReader reader , PrintWriter printWriter, int accountID)
    {
        try
        {
            var request = new JSONObject();

            System.out.print("Enter amount: ");

            request.put("Operation", "withdraw");

            var value = "";
            while ((value = reader.readLine()).isBlank()
                    || !Pattern.matches("\\d+",value))
            {
                System.out.print("Please enter proper amount: ");
            }

            request.put("Amount", value);

            request.put("AccountID", accountID);

            printWriter.println(request);

            printWriter.flush();

            // response from server
            var response = new JSONObject(serverReader.readLine());

            if (response.get("Status").toString().equals("ok"))
            {
                System.out.println("-------------------------------------------------");

                System.out.println(response.get("Message"));

                System.out.println("\nUpdated balance: " + response.get("Updated Balance"));

                System.out.println("-------------------------------------------------");
            } else
            {
                System.out.println("-------------------------------------------------");

                System.out.println(response.get("Message"));

                System.out.println("-------------------------------------------------");
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void deposit(BufferedReader serverReader, BufferedReader reader, PrintWriter printWriter, int accountID)
    {
        try
        {
            var request = new JSONObject();

            request.put("Operation", "deposit");

            System.out.print("Enter amount: ");

            var value = "";
            while ((value = reader.readLine()).isBlank()
                    || !Pattern.matches("\\d+",value))
            {
                System.out.print("Please enter proper amount: ");
            }

            request.put("Amount", value);

            request.put("AccountID", accountID);

            printWriter.println(request);

            printWriter.flush();

            var response = new JSONObject(serverReader.readLine());

            System.out.println(Constant.UNDERSCORE_SEQ);

            System.out.println(response.get("Message"));

            System.out.println("\nUpdated Balance: " + response.get("Updated Balance"));

            System.out.println(Constant.UNDERSCORE_SEQ);

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void checkBalance(BufferedReader serverReader, BufferedReader reader, PrintWriter printWriter, int accountID)
    {
        try
        {
            var request = new JSONObject();

            request.put("Operation", "check balance");

            request.put("AccountID", accountID);

            printWriter.println(request);

            printWriter.flush();

            // response from server...
            var response = new JSONObject(serverReader.readLine());

            System.out.println(Constant.UNDERSCORE_SEQ);

            System.out.println("Current Balance: " + response.get("Balance"));

            System.out.println(Constant.UNDERSCORE_SEQ);

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void transfer(BufferedReader serverReader, BufferedReader reader, PrintWriter printWriter, int accountID)
    {
        try
        {
            var request = new JSONObject();

            System.out.print("Enter recipient Account Number: ");

            var value = "";
            while ((value = reader.readLine()).isBlank()
                    || !Pattern.matches("\\d{5}",value))
            {
                System.out.print("Please enter proper account number: ");
            }

            request.put("Operation", "Transfer");

            request.put("Recipient AccountID", value);

            request.put("AccountID", accountID);

            System.out.print("Enter amount to transfer: ");

            while ((value = reader.readLine()).isBlank()
                    || !Pattern.matches("\\d+",value))
            {
                System.out.print("Please enter proper amount: ");
            }

            request.put("Amount", value);

            // send transfer request to server
//                            socket.send(request.toString());

            printWriter.println(request);

            printWriter.flush();

            var response = new JSONObject(serverReader.readLine());

            if (response.get("Status").toString().equals("ok"))
            {
                System.out.println(Constant.UNDERSCORE_SEQ);

                System.out.println(response.get("Message"));

                System.out.println("\nUpdated Balance: " + response.get("Updated Balance"));

                System.out.println(Constant.UNDERSCORE_SEQ);
            }
            else
            {
                System.out.println(Constant.UNDERSCORE_SEQ);

                System.out.println(response.get("Message"));

                System.out.println(Constant.UNDERSCORE_SEQ);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
