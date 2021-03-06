/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbaproyect;

import commun.CORBA_Interface;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author Pedro
 */
public class Server_Socket {

    ServerSocket theServer;
    String serverIP = "10.0.5.215";
    int counter = 0;
    private DataOutputStream toClient;
    private DataInputStream fromClient;
    boolean RMIconnected = false;
    boolean sendBD = false;
    public CORBA_Interface helloImpl;
    ConnectBD cbd;
    Connection conn;

    public Server_Socket(int puerto, CORBA_Interface _helloImpl, String[] args) {
        this.helloImpl = _helloImpl;
        try {
            
            System.out.println("Starting up the server socket...");
            theServer = new ServerSocket(puerto);
            System.out.println("Server socket initializated... [OK]");
            cbd = new ConnectBD();
            conn = cbd.connectBD();
            System.out.println("Database's connection established... [OK]");

            while (true) {
                Socket theClient;
                theClient = theServer.accept();

                System.out.println("New incoming connection " + theClient);
                ((Server_Thread) new Server_Thread(theClient, helloImpl, conn, serverIP, this, args)).start();
            }
        } catch (IOException ex) {
            System.out.println("algo pasa");
            Logger.getLogger(Server_Socket.class.getName()).log(Level.ALL, null, ex);
        }
    }

}
