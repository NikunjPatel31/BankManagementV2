package BankManagement.services;

import BankManagement.ClientHandler;
import BankManagement.accounts.Saving;
import BankManagement.db.AccountDB;
import BankManagement.db.ClientDB;
import BankManagement.model.Client;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.PrintWriter;

public class AuthenticationService
{
    public static JSONObject login(int clientID, String password)
    {
        ClientDB db = new ClientDB();

        JSONObject response = null;

        try
        {
            response = new JSONObject();

            if (db.checkCredential(clientID, password))
            {
                Client client = db.getClient(clientID);

                response.put("Status", "ok");

                response.put("Message", "Login successful");

                response.put("CustomerID", clientID);

                response.put("AccountID", client.getAccountID());
            }
            else
            {
                response.put("Status", "error");

                response.put("Message", "Invalid credential");
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            return response;
        }
    }

    public static JSONObject createClient(String name, String email, String contact, String address, String password)
    {
        ClientDB db = new ClientDB();

        // initializing to null because if exception is catch then method will return null
        JSONObject response = null;

        try
        {
            Client client = new Client();

            client.setName(name);

            client.setAddress(address);

            client.setEmail(email);

            client.setPassword(password);

            client.setContact(contact);

            client.setCustomerID();

            response = new JSONObject();

            if (db.addClient(client))
            {
                // client created...
                response.put("Status", "ok");

                response.put("CustomerID", client.getCustomerID());

                response.put("Message", "Client add successfully");
            }
            else {
                // client already exists...

                response.put("Status", "error");

                response.put("Message", "Client already exists");
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            return response;
        }
    }

    public static JSONObject createAccount(int customerID, long balance)
    {
        AccountDB accountDB = new AccountDB();

        JSONObject response = null;

        try
        {
            Saving saving = new Saving();

            saving.setBalance(balance);

            saving.setCustomerID(customerID);

            saving.setAccountID();

            response = new JSONObject();

            if (accountDB.addAccount(saving))
            {
                new ClientDB().getClient(customerID).setAccountID(saving.getAccountNumber());

                response.put("Status", "ok");

                response.put("Message", "Account created successfully");

                response.put("AccountID", saving.getAccountNumber());
            }
            else
            {
                response.put("Status", "error");

                response.put("Message", "Account already exists");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            return response;
        }
    }
}
