/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.plfinal;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author mario, Yanick
 */
public class Main {

    public static void main(String[] args) {
        
        Gateway gateway = new Gateway();
        Interface gui = new Interface(gateway);
        gui.setVisible(true);
        Log log = new Log();
        Random random = new Random();
        
        JTextField[] madridGateFields = {gui.getJTextField7(), gui.getJTextField8(), gui.getJTextField9(), gui.getJTextField10(), gui.getJTextField12(), gui.getJTextField13()};
        JTextField[] barcelonaGateFields = {gui.getJTextField28(), gui.getJTextField29(), gui.getJTextField30(), gui.getJTextField31(), gui.getJTextField32(), gui.getJTextField33()};
        
        JTextField[] madridRunways = {gui.getJTextField15(), gui.getJTextField17(), gui.getJTextField16(), gui.getJTextField18()};
        JTextField[] barcelonaRunways = {gui.getJTextField35(), gui.getJTextField37(), gui.getJTextField36(), gui.getJTextField38()};
           
        Airport madrid = new Airport("Madrid", gui.getJTextField4(), gui.getJTextField5()
                , madridGateFields, madridRunways, gui.getJTextField6()
                , gui.getJTextField14(), gui.getJTextField19(), gui.getJTextField3()
                , gui.getJTextField1(), gui.getJTextField2());
        Airport barcelona = new Airport("Barcelona", gui.getJTextField25(), gui.getJTextField26()
                , barcelonaGateFields, barcelonaRunways, gui.getJTextField27()
                , gui.getJTextField34(), gui.getJTextField20(), gui.getJTextField24()
                , gui.getJTextField21(), gui.getJTextField23());

        
        Thread airplaneThread = new Thread(() -> {
            for (int i = 0; i < 8000; i++) {
                try {
                    Airplane airplane = new Airplane(madrid, barcelona, log, gateway);
                    airplane.start();
                    Thread.sleep(random.nextInt(2000) + 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread busThread = new Thread(() -> {
            for (int i = 0; i < 4000; i++) {
                try {
                    Bus bus = new Bus(madrid, barcelona, log, gateway);
                    bus.start();
                    Thread.sleep(random.nextInt(500) + 500); 
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    Thread.currentThread().interrupt(); 
                }
            }
        });

        airplaneThread.start();
        busThread.start();
        
        try
        {
            RemoteAirport rmi = new RemoteAirport();
            rmi.setAll(madrid, barcelona);

            Registry r = LocateRegistry.createRegistry(1099);
            Naming.rebind("//127.0.0.1/MyRMI", rmi);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
   
}