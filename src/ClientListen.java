/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zain
 */
public class ClientListen implements Runnable{

     volatile boolean flag=true;
     ChatRemoteInterface Remote;
     int index=0;
    JTextArea chatArea;
    String name;
    
    public ClientListen(JTextArea area, String n){
        try {
            Remote=(ChatRemoteInterface) Naming.lookup("rmi://localhost:6666/zschat");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        chatArea=area;
        name=n;
    }
    
    public void cancel(){
        flag=false;
    
    System.out.println("Inside Cancel");
    }
    
    
    @Override
    public void run() {

        int i= 0;
        try {
            i = Remote.getIndex();
            index=i;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        while(flag){

            try {
                ArrayList<Message> msgs=Remote.getMessages(index);

                for(Message c:msgs){

                    if(c.getClient().equals("disconnect")){
                        chatArea.append(c.getMsg());
                    }
                    else if(c.getClient().equals("joined")){
                        chatArea.append(c.getMsg());
                    }


                 else if(c.getClient().equals(name))
                            chatArea.append("Me: "+c.getMsg());

                    else chatArea.append(c.getClient()+": "+c.getMsg());

                    chatArea.append("\n");
                }

                index+=msgs.size();



            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }

     
    
}
