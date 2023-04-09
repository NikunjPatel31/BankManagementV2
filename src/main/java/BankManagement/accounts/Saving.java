package BankManagement.accounts;

public class Saving implements Account
{
    private static int ACCOUNT_NUMBER = 10000;
    private float interestRate;
    private long  minReqBalance = 2000;
    private long balance;
    private int customerID;

    private int accountID;

    public Saving(){}

    public Saving(long balance, int customerID)
    {
        this.balance = balance;
        this.customerID = customerID;
    }

    @Override
    public long getBalance()
    {
        return this.balance;
    }

    @Override
    public void setBalance(long amount)
    {
        this.balance = amount;
    }

    @Override
    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    @Override
    public int getAccountNumber()
    {
        return this.accountID;
    }

    public int incrementAndGet()
    {
        return ACCOUNT_NUMBER++;
    }

    public void setAccountID()
    {
        this.accountID = incrementAndGet();
    }

    @Override
    public long withdraw(long amount)
    {
        if ((balance - amount) >= minReqBalance)
        {
            balance -= amount;

            return balance;
        }

        return -1;
    }

    @Override
    public long deposit(long amount)
    {
        return balance += amount;
    }
}
