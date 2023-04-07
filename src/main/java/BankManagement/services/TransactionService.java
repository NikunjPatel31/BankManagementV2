package BankManagement.services;

import BankManagement.db.AccountDB;
import org.json.JSONObject;

public class TransactionService
{
    public static JSONObject checkBalance(int accountID)
    {
        AccountDB db = new AccountDB();

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
}
