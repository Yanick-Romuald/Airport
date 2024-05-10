package com.mycompany.plfinal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import com.mycompany.plfinal.Airport;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class RMI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            
            InterfaceRemota rmi = (InterfaceRemota) Naming.lookup("//127.0.0.1/MyRMI");
            
            Airport madridAirport = rmi.getMadridAirport();
            Airport barcelonaAirport = rmi.getBarcelonaAirport();
            
            
            RMIinterface r = new RMIinterface(rmi);
            r.setVisible(true);
            
            while(true){
                r.displayHangar(madridAirport);
                r.displayAirway(madridAirport);
                r.displayPassengers(barcelonaAirport);
                r.displayTaxiArea(madridAirport);
                r.displayHangar(barcelonaAirport);
                r.displayHall(madridAirport);
                r.displayPassengers(madridAirport);
                r.displayParkingArea(madridAirport);
                r.displayParkingArea(barcelonaAirport);
                r.displayHall(barcelonaAirport);
                r.displayTaxiArea(barcelonaAirport);
                r.displayAirway(barcelonaAirport);

            }

        }
        catch (Exception e){
            Logger.getLogger(RMI.class.getName()).log(Level.SEVERE, null, e);
        }
    }


}


