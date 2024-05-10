package com.mycompany.plfinal;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author mario, Yanick
 */
public interface InterfaceRemota extends Remote{
    public int numberOfAirplanesInHangar(String a) throws RemoteException;
    public String airplanesInAirway(String a) throws RemoteException;
    public void closeRunway(String a, int n) throws RemoteException;
    public int numberOfPassengers(String a) throws RemoteException;
    public void openRunway(String a, int n) throws RemoteException;
    public int numberOfAirplanesInHall(String a) throws RemoteException;
    public int numberOfAirplanesInParking(String a) throws RemoteException;
    public int numberOfAirplanesInTaxi(String a) throws RemoteException;
    public Airport getMadridAirport() throws RemoteException;
    public Airport getBarcelonaAirport() throws RemoteException;
}



