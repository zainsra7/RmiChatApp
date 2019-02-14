
import java.rmi.*;
public class MyServer{
public static void main(String args[])throws Exception{
Remote r=new ChatInterfaceImplementation();
Naming.rebind("rmi://localhost:6666/zschat",r);
}}