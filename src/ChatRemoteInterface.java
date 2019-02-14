import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ChatRemoteInterface extends Remote {

    public void sendMessage(String msg, String client)throws RemoteException;

    public ArrayList<Message> getMessages(int index) throws RemoteException;

    public int getIndex()throws RemoteException;

}

