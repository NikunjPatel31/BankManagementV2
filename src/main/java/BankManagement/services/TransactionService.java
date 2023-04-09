package BankManagement.services;

import BankManagement.db.AccountDB;
import org.json.JSONObject;

public class TransactionService
{
    private static final AccountDB db = new AccountDB();
    public static JSONObject checkBalance(int accountID)
    {
        // should we create object of AccountDB in every method,
        // or it should be kept static because it is used in all methods...

        JSONObject response = new JSONObject();
        try
        {
            var balance = db.checkBalance(accountID);

            response.put("Balance", balance);

            response.put("Status", "ok");
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

    public static JSONObject withdraw(int accountID, long amount)
    {
        JSONObject response = new JSONObject();

        var updatedBalance = db.getAccount(accountID).withdraw(amount);

        if (updatedBalance == -1)
        {
            // insufficient balance
            response.put("Status", "error");

            response.put("Message", "Insufficient balance");
        }
        else
        {
            // withdraw completed
            response.put("Status","ok");

            response.put("Message", "withdraw complete");

            response.put("Updated Balance", updatedBalance);
        }

        return response;
    }

    public static JSONObject deposit(int accountID, long amount)
    {
        JSONObject response = new JSONObject();

        try
        {
            var updatedBalance = db.getAccount(accountID).deposit(amount);

            response.put("Status", "ok");

            response.put("Message", "Deposit complete");

            response.put("Updated Balance", updatedBalance);
        }
        catch (Exception exception)
        {
            // if for some reason exception is caught here
            // then what should be returned in response ?

            response.put("Status", "error");

            response.put("Message", "Error in depositing");
        }
        finally
        {
            return response;
        }
    }

    public static JSONObject transfer(int recipientAccountID, int accountID, long amount)
    {
        JSONObject response = new JSONObject();

        try
        {
            var updatedBal = db.getAccount(accountID)
                    .withdraw(amount);

            if (updatedBal != -1)
            {
                db.getAccount(recipientAccountID)
                        .deposit(amount);

                response.put("Status", "ok");

                response.put("Message", "Transfer completed");

                response.put("Updated Balance", updatedBal);
            }
            else {
                // insufficient balance
                response.put("Status", "error");

                response.put("Message", "Transfer not completed. Insufficient balance");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return response;
    }
}
