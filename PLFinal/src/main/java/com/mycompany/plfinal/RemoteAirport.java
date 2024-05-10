package com.mycompany.plfinal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.mycompany.plfinal.Airport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.mycompany.plfinal.Airport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author mario
 */

public class RemoteAirport extends UnicastRemoteObject implements InterfaceRemota {
    
    private Airport madridAirport, barcelonaAirport;
    
    public RemoteAirport() throws RemoteException {}
    
    public void setAll(Airport m, Airport b) {
        madridAirport = m;
	barcelonaAirport = b;
	
    }

    @Override
    public Airport getMadridAirport() {
        return madridAirport;
    }

    @Override
    public Airport getBarcelonaAirport() {
        return barcelonaAirport;
    }
    
    @Override
    public int numberOfAirplanesInHangar(String a) throws RemoteException
    {
        System.out.println(madridAirport.getHangar().getAirplaneCount());
        if(a.equals("Madrid"))
        {
            return madridAirport.getHangar().getAirplaneCount();
        }
        else
        {
            return barcelonaAirport.getHangar().getAirplaneCount();
        }
    }
    
    @Override
    public String airplanesInAirway(String a) throws RemoteException
    {
        if(a.equals("Madrid"))
        {
            return madridAirport.getAirway().getAirplanes().toString();
        }
        else
        {
            return barcelonaAirport.getAirway().getAirplanes().toString();
        }
    }
    
    @Override
    public void closeRunway(String a, int n) throws RemoteException
    {
        if(a.equals("Madrid"))
        {
            madridAirport.getRunways().closeRunway(n);
        }
        else
        {
            barcelonaAirport.getRunways().closeRunway(n);
        }
    }
    
    @Override
    public int numberOfPassengers(String a) throws RemoteException
    {
        if(a.equals("Madrid"))
        {
            return madridAirport.getPassengers();
        }
        else
        {
            return barcelonaAirport.getPassengers();
        }
    }


    @Override
    public void openRunway(String a, int n) throws RemoteException
    {
        if(a.equals("Madrid"))
        {
            madridAirport.getRunways().openRunway(n);
        }
        else
        {
            barcelonaAirport.getRunways().openRunway(n);
        }
    }

    @Override
    public int numberOfAirplanesInHall(String a) throws RemoteException
    {
        if(a.equals("Madrid"))
        {
            return madridAirport.getMaintenanceHall().getAirplaneCount();
        }
        else
        {
            return barcelonaAirport.getMaintenanceHall().getAirplaneCount();
        }
    }

    @Override
    public int numberOfAirplanesInParking(String a) throws RemoteException
    {
        if(a.equals("Madrid"))
        {
            return madridAirport.getParking().getAirplaneCount();
        }
        else
        {
            return barcelonaAirport.getParking().getAirplaneCount();
        }
    }

    @Override
    public int numberOfAirplanesInTaxi(String a) throws RemoteException
    {
        if(a.equals("Madrid"))
        {
            return madridAirport.getTaxi().getAirplaneCount();
        }
        else
        {
            return barcelonaAirport.getTaxi().getAirplaneCount();
        }
    }

}


