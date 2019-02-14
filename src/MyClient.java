
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.*;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClient{

    JFrame mainFrame;
    static JTextArea chatArea;
    JMenu mainMenu;
    JMenuItem connect,disconnet;
    JMenuBar menuBar;

    JTextField writeMessage;
    JTextField clientName;
    JButton sendButton,setName;

    Socket client;
    ClientListen listenObj;
    Thread clientListenThread;

    String message="";

    boolean connected=false;
    boolean nameIsSet=false;

    ChatRemoteInterface remote;

    String name="Hero";

    MyClient(){


        try {
            remote=(ChatRemoteInterface) Naming.lookup("rmi://localhost:6666/zschat");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        mainFrame= new JFrame();

        menuBar=new JMenuBar();
        mainMenu=new JMenu("Menu");
        connect=new JMenuItem("connect");
        disconnet=new JMenuItem("disconnect");

        mainMenu.add(connect);
        mainMenu.add(disconnet);

        menuBar.add(mainMenu);

        chatArea =new JTextArea("Welcome To ZNS Chat App !\n");
        chatArea.setBounds(10,50, 330,280);
        chatArea.setEditable(false); //User will only be able to see information and nothing else.

        writeMessage=new JTextField();
        writeMessage.setBounds(10,350,300,50);

        sendButton=new JButton("Send");
        sendButton.setBounds(300,350,70,50);

       clientName=new JTextField();
        clientName.setBounds(100,5,100,30);

        setName=new JButton("Set Name");
        setName.setBounds(200,5,100,30);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if (connected) {

                    message = writeMessage.getText().toString();

                    if(!message.equals("")) {

                        try {
                            remote.sendMessage(message, name);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(mainFrame,"Please Enter Something !");

                    }
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame,"Please Connect to Server First !");
                }
            }
        });

        disconnet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(connected) {
                    listenObj.cancel();

                    try {
                        remote.sendMessage(name+" is Disconnected !","disconnect");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(mainFrame, "Disconnected !");
                    connected = false;

                    //mainFrame.setVisible(false);
                }
                else {

                    JOptionPane.showMessageDialog(mainFrame, "Already Disconnected !");

                }

            }
        });

        setName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!nameIsSet) {
                    name = clientName.getText().toString();

                    if (!name.equals("")) {

                        clientName.setEditable(false);
                        nameIsSet = true;
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Please Enter Something !");

                    }

                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "Your name is : "+name);


                }
            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(nameIsSet) {
                    listenObj = new ClientListen(chatArea, name);
                    clientListenThread = new Thread(listenObj);
                    clientListenThread.start();

                    try {
                        remote.sendMessage(name + " Joined Chat !", "joined");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }


                    connected = true;
                }
                else { JOptionPane.showMessageDialog(mainFrame,"Please Set Name First !");}
            }
        });

        mainFrame.add(menuBar);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(chatArea);
        mainFrame.add(writeMessage);
        mainFrame.add(clientName);
        mainFrame.add(setName);
        mainFrame.add(sendButton);
        // mainFrame.add(disconnectButton);

        mainFrame.setSize(400,500);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);


    }







public static void main(String args[])throws Exception{


        new MyClient();
}

}