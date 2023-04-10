package BankManagement;

import BankManagement.accounts.Saving;
import BankManagement.services.AuthenticationService;
import BankManagement.services.TransactionService;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    private final Socket socket;

    ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter printWriter = new PrintWriter(socket.getOutputStream()))
        {
            while (true)
            {
                var request = new JSONObject(reader.readLine());

                switch (request.get("Operation").toString())
                {
                    case "login":
                    {
                        var response = new JSONObject();

                        try
                        {
                            response = AuthenticationService.login(Integer.parseInt(request.get("CustomerID").toString()),
                                    request.get("Password").toString());

                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        printWriter.println(response);

                        printWriter.flush();

                        System.out.println("Login: " + response);
                    }
                    break;
                    case "createCustomer":
                    {
                        var response = new JSONObject();

                        try
                        {
                            // adding customer to db
                            response = AuthenticationService.createClient(request.get("Name").toString().trim(),
                                    request.get("Email").toString().trim(),
                                    request.get("Contact").toString().trim(),
                                    request.get("Address").toString().trim(),
                                    request.get("Password").toString().trim());

                        } catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        printWriter.println(response);

                        printWriter.flush();

                        System.out.println("Response send: " + response);
                    }
                    break;
                    case "createAccount":
                    {
                        var response = new JSONObject();

                        try
                        {
                            // adding new account to db

                            response = AuthenticationService.createAccount(Integer.parseInt(request.get("CustomerID").toString()), Long.parseLong(request.get("balance").toString()));

                            System.out.println("Create account: "+response);
                        } catch (Exception exception)
                        {
                            exception.printStackTrace();

                        }
                        printWriter.println(response);

                        printWriter.flush();

                        System.out.println("createAccount -> Response send: " + response);
                    }
                    break;
                    case "check balance":
                    {
                        var response = new JSONObject();
                        try
                        {
                            response = TransactionService.checkBalance(Integer.parseInt(request.get("AccountID").toString()));

                        } catch (Exception exception)
                        {
                            System.out.println("Error in checking balance");
                        }
                        printWriter.println(response);

                        printWriter.flush();

                        System.out.println("Check Balance: " + response);
                    }
                    break;
                    case "withdraw":
                    {
                        var response = new JSONObject();
                        try
                        {
                            response = TransactionService.withdraw(Integer.parseInt(request.get("AccountID").toString()), Long.parseLong(request.get("Amount").toString()));
                        } catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        printWriter.println(response);

                        printWriter.flush();
                    }
                    break;
                    case "deposit":
                    {
                        var response = new JSONObject();
                        try
                        {
                            response = TransactionService.deposit(Integer.parseInt((request.get("AccountID").toString())),
                                    Long.parseLong(request.get("Amount").toString()));
                        } catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }

                        printWriter.println(response);

                        printWriter.flush();
                    }
                    break;
                    case "Transfer":
                    {
                        var response = new JSONObject();

                        try
                        {
                            response = TransactionService.transfer(Integer.parseInt(request.get("Recipient AccountID").toString()),
                                    Integer.parseInt(request.get("AccountID").toString()),
                                    Long.parseLong(request.get("Amount").toString()));

                        } catch (Exception exception)
                        {
                            response.put("Status", "error");

                            response.put("Message", "Error in transferring");
                        }

                        printWriter.println(response);

                        printWriter.flush();
                    }
                    break;
                    case "logout":
//                {
//                    var response = new JSONObject();
//
//                    try
//                    {
//                        if (server.bankDB.removeActiveAcc(Integer.parseInt(request.get("CustomerID").toString())))
//                        {
//                            response.put("Status", "ok");
//
//                            response.put("Message", "Log out complete");
//                        }
//                        else
//                        {
//                            response.put("Status", "error");
//
//                            response.put("Message", "Error in logging out");
//                        }
//                    }
//                    catch (Exception exception)
//                    {
//                        response.put("Status", "error");
//
//                        response.put("Message", "Error in logging out");
//                    }
//                    finally
//                    {
//                        socket.send(response.toString());
//                    }
//                }
                        break;
                    default:
                        System.out.println("Invalid request");
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
