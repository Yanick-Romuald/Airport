/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.Serializable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Yanick
 */
public class Airport implements Serializable{
    private static Airport madrid;
    private static Airport barcelona;
    
    private String name;
    private int totalPas;
    private JTextField pasField;
    
    private Hangar hangar;
    private MaintenanceHall maintenance;
    private BoardingGates gates;
    private Runways runways;
    private Parking parking;
    private TaxiArea taxi;
    private Airway airway;
    private BusTransfers airportT;
    private BusTransfers downtownT;
    
    public Airport(String name, JTextField hangar, JTextField maintenance, JTextField[] gates
            , JTextField[] runways, JTextField parking, JTextField taxi, JTextField airway
            , JTextField pasField, JTextField airportT, JTextField downtownT){
        
        this.name = name;
        this.hangar = new Hangar(hangar);
        this.maintenance = new MaintenanceHall(maintenance);
        this.gates = new BoardingGates(gates);
        this.runways = new Runways(runways);
        this.parking = new Parking(parking);
        this.taxi = new TaxiArea(taxi);
        this.airway = new Airway(airway);
        this.airportT = new BusTransfers(airportT);
        this.downtownT = new BusTransfers(downtownT);
        this.pasField = pasField;
        this.totalPas = 100;
        updateTextField();
        
        if(name.equalsIgnoreCase("Madrid")){
            madrid = this;
        }else if (name.equalsIgnoreCase("Barcelona")){
            barcelona = this;
        }
    }
    
    public Airport(String name){
        this.name = name;
    }
    
    public synchronized void addPassengers(int cont){
        totalPas += cont;
        if (totalPas < 0) totalPas = 0;
        updateTextField();
    }
    
    public synchronized void removePassengers(int cont){
        totalPas -= cont;
        if (totalPas < 0) totalPas = 0;
        updateTextField();
    }
    
    private void updateTextField(){
        String text = "Total passengers: " + totalPas;
        SwingUtilities.invokeLater(() -> pasField.setText(text));
    }
    
    public static Airport getMadrid(){
        return madrid;
    }
    
    public static Airport getBarcelona(){
        return barcelona;
    }
    
    public Hangar getHangar(){
        return hangar;
    }
    
    public MaintenanceHall getMaintenanceHall(){
        return maintenance;
    }
    
    public BoardingGates getGates(){
        return gates;
    }
    
    public Runways getRunways(){
        return runways;
    }
    
    public Parking getParking(){
        return parking;
    }
    
    public TaxiArea getTaxi(){
        return taxi;
    }
    
    public Airway getAirway(){
        return airway;
    }
    
    public String getName(){
        return name;
    }
    
    public int getPassengers(){
        return totalPas;
    }
    
    public BusTransfers getAirportT(){
        return airportT;
    }
    
    public BusTransfers getDowntownT(){
        return downtownT;
    }
}
