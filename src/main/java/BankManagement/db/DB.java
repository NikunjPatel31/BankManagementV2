package BankManagement.db;

public interface DB
{
    public boolean insert(Object value);

    public void delete(Object value);

    public Object get(Object value);
}
