package BankManagement;

import BankManagement.accounts.Saving;
import BankManagement.db.ClientDB;
import BankManagement.model.Client;
import BankManagement.services.AuthenticationService;
import BankManagement.services.TransactionService;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    Socket socket;

    ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            while (true)
            {
                String requestSTr = reader.readLine();
                System.out.println("Request: "+requestSTr);
                JSONObject request = new JSONObject(requestSTr);

                System.out.println("JSON: "+request);

                switch (request.get("Operation").toString())
                {
                    case "login":
//                {
//                    JSONObject response = new JSONObject();
//
//                    try
//                    {
////                        AuthenticationService.login();
//
//                        if (server.bankDB.isAccountActive(Integer.parseInt(request.get("CustomerID").toString())))
//                        {
//                            response.put("Status", "error");
//
//                            response.put("Message", "User already logged in");
//                        }
//                        else
//                        {
//                            System.out.println("Val: "+server.bankDB.isAccountActive(Integer.parseInt(request.get("CustomerID").toString())));
//                            if (server.bankDB.checkCredential(Integer.parseInt(request.get("CustomerID").toString()),
//                                    request.get("Password").toString()))
//                            {
//                                // login successfully
//                                var customer = server.bankDB.getCustomer(Integer.parseInt(request.get("CustomerID").toString()));
//
//                                if (customer != null)
//                                {
//                                    server.bankDB.addActiveAccount(customer.getCustomerID());
//
//                                    response.put("CustomerID", customer.getCustomerID());
//
//                                    response.put("Message", "Login successful");
//
//                                    response.put("AccountID", customer.getAccountID());
//
//                                    response.put("Status","ok");
//                                }
//                                else {
//                                    // error
//                                    // show appropriate message
//
//                                    response.clear();
//
//                                    response.put("Message", "Error in login");
//
//                                    response.put("Status", "error");
//                                }
//
//                            }
//                            else
//                            {
//                                // credential are wrong
//                                response.clear();
//
//                                response.put("Message", "Invalid credentials");
//
//                                response.put("Status", "error");
//                            }
//                        }
//                    }
//                    catch (Exception exception)
//                    {
//                        exception.printStackTrace();
//
//                        response.clear();
//
//                        response.put("Message", "Error in login");
//
//                        response.put("Status", "error");
//                    }
//                    finally
//                    {
//                        socket.send(response.toString());
//                    }
//                }
                        break;
                    case "createCustomer":
                    {
                        JSONObject response = new JSONObject();

                        try
                        {
                            // adding customer to db
                            response = AuthenticationService.createClient(request.get("Name").toString().trim(),
                                    request.get("Email").toString().trim(),
                                    request.get("Contact").toString().trim(),
                                    request.get("Address").toString().trim(),
                                    request.get("Password").toString().trim());

                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        finally
                        {
                            //socket.send(response.toString());
                            printWriter.println(response);

                            printWriter.flush();

                            System.out.println("Response send: "+response);
                        }
                    }
                        break;
                    case "createAccount":
                    {
                        JSONObject response = new JSONObject();

                        try
                        {
                            Saving saving = new Saving();

                            saving.setBalance(Long.parseLong(request.get("balance").toString()));

                            saving.setCustomerID(Integer.parseInt(request.get("CustomerID").toString()));

                            saving.setAccountID();

                            // adding new account to db

                            response = AuthenticationService.createAccount(Integer.parseInt(request.get("CustomerID").toString()),Long.parseLong(request.get("balance").toString()));

                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();

                        }
                        finally
                        {
                            printWriter.println(response);

                            printWriter.flush();

                            System.out.println("createAccount -> Response send: "+response);
                        }
                    }
                        break;
                    case "check balance":
                {
                    JSONObject response = new JSONObject();
                    try
                    {
                        response = TransactionService.checkBalance(Integer.parseInt(request.get("AccountID").toString()));

                    }
                    catch (Exception exception)
                    {
                        System.out.println("Error in checking balance");
                    }
                    finally
                    {
                        printWriter.println(response);

                        printWriter.flush();

                        System.out.println("Check Balance: "+response);
                    }
                }
                        break;
                    case "withdraw":
//                {
//                    var response = new JSONObject();
//                    try
//                    {
//                        var updatedBal = server.bankDB.getAccount(Long.parseLong(request.get("AccountID").toString()))
//                                .withdraw(Long.parseLong(request.get("Amount").toString()));
//
//                        if (updatedBal == -1)
//                        {
//                            // insufficient balance
//                            response.put("Status","error");
//
//                            response.put("Message", "Insufficient Balance");
//                        }
//                        else
//                        {
//                            response.put("Status", "ok");
//
//                            response.put("Message", "Account withdraw complete");
//
//                            response.put("Updated Balance", updatedBal);
//                        }
//                    }
//                    catch (Exception exception)
//                    {
//                        System.out.println("Error in withdraw amount");
//
//                        response.clear();
//
//                        response.put("Status", "error");
//
//                        response.put("Message", "Error in withdraw");
//                    }
//                    finally
//                    {
//                        socket.send(response.toString());
//                    }
//                }
                        break;
                    case "deposit":
//                {
//                    var response = new JSONObject();
//                    try
//                    {
//                        var updatedBal = server.bankDB.getAccount(Long.parseLong(request.get("AccountID").toString()))
//                                .deposit(Long.parseLong(request.get("Amount").toString()));
//
//                        response.put("Status", "ok");
//
//                        response.put("Message","Deposit completed");
//
//                        response.put("Updated Balance", updatedBal);
//                    }
//                    catch (Exception exception)
//                    {
//                        System.out.println("Error in depositing");
//
//                        response.clear();
//
//                        response.put("Status", "error");
//
//                        response.put("Message", "Error in depositing");
//                    }
//                    finally
//                    {
//                        socket.send(response.toString());
//                    }
//                }
                        break;
                    case "Transfer":
//                {
//                    var response = new JSONObject();
//
//                    try
//                    {
//                        var updatedBal = server.bankDB.getAccount(Long.parseLong(request.get("AccountID").toString()))
//                                .withdraw(Long.parseLong(request.get("Amount").toString()));
//
//                        if (updatedBal != -1)
//                        {
//                            try
//                            {
//                                server.bankDB.getAccount(Long.parseLong(request.get("Recipient AccountID").toString()))
//                                        .deposit(Long.parseLong(request.get("Amount").toString()));
//
//                                response.put("Status", "ok");
//
//                                response.put("Message", "Transfer completed");
//
//                                response.put("Updated Balance", updatedBal);
//                            }
//                            catch (Exception exception)
//                            {
//                                System.out.println(exception.getMessage());
//
//                                response.put("Status", "error");
//
//                                response.put("Message", exception.getMessage());
//                            }
//
//                        }
//                        else
//                        {
//                            response.put("Status", "error");
//
//                            response.put("Message", "Transfer not completed, insufficient balance");
//                        }
//                    }
//                    catch (Exception exception)
//                    {
//                        System.out.println("Error in transferring");
//
//                        response.clear();
//
//                        response.put("Status", "error");
//
//                        response.put("Message", "Error in transferring");
//                    }
//                    finally
//                    {
//                        socket.send(response.toString());
//                    }
//                }
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
