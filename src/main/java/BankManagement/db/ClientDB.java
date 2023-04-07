package BankManagement.db;

import BankManagement.model.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientDB
{
    private static final Map<Integer, Client> clientMap = new HashMap<>();
    public boolean addClient(Client client)
    {
        return clientMap.computeIfAbsent(client.getCustomerID(), k -> client) != null;
    }
}
