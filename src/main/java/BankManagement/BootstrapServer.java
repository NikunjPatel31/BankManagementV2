package BankManagement;

public class BootstrapServer
{
    public static void main(String[] args)
    {
        try
        {
            BankServer.start();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
