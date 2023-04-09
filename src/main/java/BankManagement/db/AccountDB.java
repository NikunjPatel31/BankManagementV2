package BankManagement.db;

import BankManagement.accounts.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountDB
{
    private static final Map<Integer, Account> accountMap = new HashMap<>();
    public boolean addAccount(Account account)
    {
        return accountMap.computeIfAbsent(account.getAccountNumber(),
                                        k -> account) != null;
    }

    public long checkBalance(int accountID)
    {
        return accountMap.get(accountID).getBalance();
    }

    public Account getAccount(int accountID)
    {
        return accountMap.get(accountID);
    }
}
