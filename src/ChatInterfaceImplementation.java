import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatInterfaceImplementation extends UnicastRemoteObject implements ChatRemoteInterface{
    ChatInterfaceImplementation()throws RemoteException {message=new ArrayList<Message>();}

    ArrayList<Message> message;

    @Override
    public void sendMessage(String msg, String client) throws RemoteException{

        Message m=new Message(msg,client);
        message.add(m);

        if(!client.equals("disconnect") && !client.equals("joined")){

            try {
                Database.addMessage(msg, client);
            } catch (SQLException ex) {

            }
        }

    }

    @Override
    public ArrayList<Message> getMessages(int index) throws RemoteException {
        ArrayList<Message> tempMessage;

        if(message.size()==0 || index==message.size()) return tempMessage=new ArrayList<>();

        else tempMessage=new ArrayList<Message>(message.subList(index,message.size()));

        return tempMessage;
    }

    @Override
    public int getIndex() throws RemoteException {
        return message.size();
    }
}
