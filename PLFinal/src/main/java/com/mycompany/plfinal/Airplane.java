/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Yanick
 */

public class Airplane extends Thread implements Serializable{
    
    private String id;
    private Airport homeAirport;
    private int maxCapacity;
    private static final Random random = new Random();
    private int passengerCount = 0;
    private int flightsCompleted = 0;
    private Log log;
    private Gateway gateway;

    public Airplane(Airport madrid, Airport barcelona, Log log, Gateway gateway) {
        this.log = log;
        this.gateway = gateway;
        this.id = generateUniqueId();
        this.maxCapacity = 100 + random.nextInt(201);
        int numericId = Integer.parseInt(id.substring(2));
        this.homeAirport = numericId % 2 == 0 ? madrid : barcelona;
        log.write("Airplane " + id + " is created at " + homeAirport.getName());
        try {
            enterHangar();
        } catch (InterruptedException e) {
            log.write("Airplane " + id + " interrupted while trying to enter hangar at " + homeAirport.getName());
        }
    }

    private static String generateUniqueId() {
        char firstChar = (char) ('A' + random.nextInt(26));
        char secondChar = (char) ('A' + random.nextInt(26));
        int number = random.nextInt(10000);
        return String.format("%c%c-%04d", firstChar, secondChar, number);
    }

    @Override
    public void run() {
        log.write("Airplane " + id + " starts operation cycle at " + homeAirport.getName());
        try {
            while (!isInterrupted()) {
                gateway.look();
                enterParking();
                waitingGate();
                board();
                enterTaxiArea();
                waitingRunway();
                enterAirway();
                waitingLanding();
                enterTaxiArea2();
                waitingGateArrivals();
                debark();
                enterParkingArrival();
                enterMaintenance();
                finalDecision();
            }
        } catch (InterruptedException e) {
            System.out.println("Airplane " + id + " interrupted.");
            log.write("Airplane " + id + " interrupted.");
        }
    }
    
    private void enterHangar()throws InterruptedException{
        gateway.look();
        homeAirport.getHangar().addPlane(this);
        System.out.println(id + " entering hangar.");
        log.write(id + " entering hangar at " + homeAirport.getName());
    }
    
    private void enterParking()throws InterruptedException{
        gateway.look();
        Thread.sleep(5000);
        homeAirport.getHangar().removePlane(this);
        homeAirport.getParking().addPlane(this);
        System.out.println(id + "moved to parking area.");
        log.write(id + " moved to parking area at " + homeAirport.getName());
        Thread.sleep(random.nextInt(1000, 5000));
    }
    
    private void waitingGate()throws InterruptedException{
        gateway.look();
        homeAirport.getParking().removePlane(this);
        boolean placed = false;
        
        while(!placed){
            placed = homeAirport.getGates().addPlane(this, false);
            if(!placed){
                Thread.sleep(1000);
            }
        }
        
        System.out.println(id + " has accessed a boarding gate.");
        log.write(id + " has accessed a boarding gate at " + homeAirport.getName());
    }
    
    private void board()throws InterruptedException{
        gateway.look();
        
        int attempts = 0;
        boolean ready = false;
        
        while(!ready && attempts < 3){
            int needs = maxCapacity - passengerCount;
            int totalAirport = homeAirport.getPassengers();
            
            if(totalAirport > 0){
                int board = Math.min(needs, totalAirport);
                homeAirport.removePassengers(board);
                
                for (int i = 0; i < board; i++) {
                    if(passengerCount < maxCapacity){
                        passengerCount++;
                        Thread.sleep(random.nextInt(1000, 3000));
                    }     
                }
                System.out.println(id + " boarded " + board + " passengers.");
                log.write(id + " boarded " + board + " passengers at " + homeAirport.getName());
            }
            
            if(passengerCount >= maxCapacity){
                ready = true;
                System.out.println(id + " id ready to fly with full capacity");
                log.write(id + " is ready to fly with full capacity from " + homeAirport.getName());
            }else{
                Thread.sleep(random.nextInt(1000, 5000));
                attempts++;
            }
        }
        
        if(!ready){
            System.out.println(id+ " is ready to fly without full capacity");
            log.write(id + " is ready to fly without full capacity from " + homeAirport.getName());
        }
    }
    
    private void enterTaxiArea()throws InterruptedException{
        gateway.look();
        homeAirport.getGates().removePlane(this);
        homeAirport.getTaxi().addPlane(this);
        System.out.println(id + " moves to taxi area");
        log.write(id + " moves to taxi area at " + homeAirport.getName());
        Thread.sleep(random.nextInt(1000, 5000));
    }
    
    private void waitingRunway() throws InterruptedException{
        gateway.look();
        homeAirport.getTaxi().removePlane(this);
        boolean placed = false;
        while(!placed && homeAirport.getRunways().isRunwayAvailable()){
            placed = homeAirport.getRunways().addPlane(this);
            if(!placed){
                Thread.sleep(1000);
            }
        }
        
        log.write(id + " has been placed in a runway at " + homeAirport.getName());
        System.out.println(id + " has been placed in a runway");
        System.out.println(id + " is doing the last comprobations");
        Thread.sleep(random.nextInt(1000, 3000));
        log.write(id + " is taking off from " + homeAirport.getName());
        System.out.println(id+ " is taking off");
        Thread.sleep(random.nextInt(1000, 5000));
        
        
    }
    
    private void enterAirway() throws InterruptedException{
        gateway.look();
        homeAirport.getRunways().removePlane(this);
        homeAirport.getAirway().addPlane(this);
        Airport destination = this.homeAirport == Airport.getMadrid() ? Airport.getBarcelona() : Airport.getMadrid();
        System.out.println(id + " is fliying from " + homeAirport.getName() + " to " + destination.getName());
        log.write(id + " is flying from " + homeAirport.getName() + " to " + destination.getName());
        Thread.sleep(random.nextInt(15000, 30000));
        
        this.homeAirport.getAirway().removePlane(this);
        this.homeAirport = destination;
        System.out.println(id + " has landed at " + homeAirport.getName());
        log.write(id + " has landed at " + homeAirport.getName());
        
    }
    
    private void waitingLanding() throws InterruptedException{
        gateway.look();
        boolean landed = false;
        while(!landed && homeAirport.getRunways().isRunwayAvailable()){
            landed = homeAirport.getRunways().addPlane(this);
            if(!landed){
                System.out.println(id + " triying again to land");
                log.write(id + " trying again to land at " + homeAirport.getName());
                Thread.sleep(random.nextInt(1000, 5000));
            }
        }
        
        log.write(id + " is in a runway to land at " + homeAirport.getName());
        System.out.println(id + " is in a runway to land");
        Thread.sleep(random.nextInt(1000, 5000));
        System.out.println(id + " has landed");
        log.write(id + " has landed at " + homeAirport.getName());
    }
    
    public void enterTaxiArea2() throws InterruptedException{
        gateway.look();
        homeAirport.getRunways().removePlane(this);
        homeAirport.getTaxi().addPlane(this);
        System.out.println(id + " is going to a gate.");
        log.write(id + " is going to a gate at " + homeAirport.getName());
        Thread.sleep(random.nextInt(3000, 5000));
    }
    
    public void waitingGateArrivals()throws InterruptedException{
        gateway.look();
        homeAirport.getTaxi().removePlane(this);
        boolean placed = false;
        
        while(!placed){
            placed = homeAirport.getGates().addPlane(this, true);
            if(!placed){
                Thread.sleep(1000);
            }
        }
        
        System.out.println(id + " has accessed a boarding gate.");
        log.write(id + " has accessed a boarding gate for arrivals at " + homeAirport.getName());
    }
    
    public void debark()throws InterruptedException{
        gateway.look();
        log.write(id + " is debarking passengers at " + homeAirport.getName());
        System.out.println(id + " id debarking passengers");
        for (int i = 0; i < passengerCount; i++) {
             Thread.sleep(random.nextInt(1000, 5000));
        }
        
        homeAirport.addPassengers(passengerCount);
        passengerCount=0;
        log.write(id + " has debarked all the passengers at " + homeAirport.getName());
        System.out.println(id + " has debarked all the passengers at" + homeAirport.getName());
    }
    
    public void enterParkingArrival()throws InterruptedException{
        gateway.look();
        homeAirport.getGates().removePlane(this);
        homeAirport.getParking().addPlane(this);
        System.out.println(id + "moved to parking area.");
        log.write(id + " moved to parking area upon arrival at " + homeAirport.getName());
        Thread.sleep(random.nextInt(1000, 5000));
    }
    
    public void enterMaintenance()throws InterruptedException{
        gateway.look();
        homeAirport.getParking().removePlane(this);
        flightsCompleted++;
        
        homeAirport.getMaintenanceHall().addPlane(this);
        boolean needsMant = flightsCompleted % 15 == 0;
        int inspectionTime = needsMant ? random.nextInt(5000, 10000) : random.nextInt(1000, 5000);
        log.write(id + " entering the maintenance hall for " + (needsMant ? "deep inspection." : "quick check.") + " at " + homeAirport.getName());
        System.out.println(id+ " entering the maintenance hall for " + (needsMant ? " deep inspection." : " quick check."));
        Thread.sleep(inspectionTime);
        
        System.out.println(id+ " has completed maintenance");
        log.write(id + " has completed maintenance at " + homeAirport.getName());
    }
    
    public void finalDecision()throws InterruptedException{
        gateway.look();
        homeAirport.getMaintenanceHall().removePlane(this);
        boolean rest = random.nextBoolean();
        
        if(rest){
            gateway.look();
            homeAirport.getHangar().addPlane(this);
            System.out.println(id + "is resting in the hangar");
            log.write(id + " is resting in the hangar at " + homeAirport.getName());
            Thread.sleep(random.nextInt(15000, 30000));
            homeAirport.getHangar().removePlane(this);
        }
    }
    
    public String toString(){
        return id;
    }
}
