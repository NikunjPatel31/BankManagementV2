package BankManagement.model;

public class Client
{
    private static int CUSTOMER_ID_COUNT = 1;

    private String name;

    private String email;

    private String address;

    private String password;

    private String contact;

    private int customerID;

    private int accountID;

    public Client() {}

    public Client(String name, String email, String address, String password, String contact)
    {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.contact = contact;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public void setCustomerID()
    {
        this.customerID = incrementAndGet();
    }

    public void setAccountID(int accountID)
    {
        this.accountID = accountID;
    }

    public int getCustomerIdCount()
    {
        return CUSTOMER_ID_COUNT;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPassword()
    {
        return password;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public int getAccountID()
    {
        return accountID;
    }

    public String getContact()
    {
        return contact;
    }

    public int incrementAndGet()
    {
        return CUSTOMER_ID_COUNT++;
    }
}
