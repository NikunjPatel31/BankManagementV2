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

    public boolean checkCredential(int clientID, String password)
    {
        return clientMap.containsKey(clientID)
                && clientMap.get(clientID).getPassword().equals(password);
    }

    public Client getClient(int clientID)
    {
        return clientMap.get(clientID);
    }
}
