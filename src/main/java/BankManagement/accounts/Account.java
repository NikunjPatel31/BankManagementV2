package BankManagement.accounts;

public interface Account
{
    public long getBalance();

    public long setBalance(long amount);

    public void setCustomerID(int customerID);

    public int getAccountNumber();

    public long withdraw(long amount);

    public long deposit(long amount);
}
