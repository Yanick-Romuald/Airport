/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.Serializable;
import java.util.Random;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Yanick
 */

public class Bus extends Thread implements Serializable{
    
    private String id;
    private Airport homeAirport;
    private static final Random random = new Random();
    private int passengerCount = 0;
    private Log log;
    private Gateway gateway;

    public Bus(Airport madrid, Airport barcelona, Log log, Gateway gateway) {
        this.log = log;
        this.gateway = gateway;
        this.id = generateUniqueId();
        int numericId = Integer.parseInt(id.substring(2));
        this.homeAirport = numericId % 2 == 0 ? madrid : barcelona;
        log.write("Bus " + id + " is created for " + homeAirport.getName());
        
    }

    private static String generateUniqueId() {
        char firstChar = 'B';
        int number = random.nextInt(10000);
        return String.format("%c-%04d", firstChar, number);
    }

    @Override
    public void run() {
        log.write("Bus " + id + " starts operation at " + homeAirport.getName());
        try {
            while (!isInterrupted()) {
                gateway.look();
                arriveDowntown();
                letPassengersIn();
                travelToAirport();
                arriveAtAirport();
                waitForPassengersAtAirport();
                letPassengersInAtAirport();
                returnToDowntown();
                offloadPassengersDowntown();
            }
        } catch (InterruptedException e) {
            System.out.println("Bus " + id + " interrupted.");
            log.write("Bus " + id + " interrupted.");
        }
    }

    public void arriveDowntown() throws InterruptedException{
        gateway.look();
        log.write(id + " arrives downtown from " + homeAirport.getName());
        homeAirport.getDowntownT().addBus(this);
        System.out.println(id + " arrives downtown");
        Thread.sleep(random.nextInt(2000, 5000));
    }
    
    public void letPassengersIn() throws InterruptedException{
        gateway.look();
        int passengersBoarding = random.nextInt(51);
        passengerCount += passengersBoarding;
        log.write(id + " boarded " + passengersBoarding + " passengers at downtown.");
        System.out.println(id + " boarded " + passengersBoarding + " passengers at downtown.");
    }
    
    private void travelToAirport() throws InterruptedException {
        gateway.look();
        log.write(id + " is traveling to the airport from downtown.");
        homeAirport.getDowntownT().removeBus(this);
        System.out.println(id + " is traveling to the airport.");
        Thread.sleep(random.nextInt(5000, 10001));
    }
    
    private void arriveAtAirport() {
        gateway.look();
        log.write(id + " arrived at airport bus-stop and offloaded passengers.");
        homeAirport.getAirportT().addBus(this);
        System.out.println(id + " arrived at airport bus-stop and offloaded passengers.");
        homeAirport.addPassengers(passengerCount);
        passengerCount = 0;
    }
    
    private void waitForPassengersAtAirport() throws InterruptedException {
        gateway.look();
        log.write(id + " is waiting for new passengers at the airport.");
        System.out.println(id + " is waiting for new passengers at the airport.");
        Thread.sleep(random.nextInt(2000, 5001));
    }
    
    private void letPassengersInAtAirport() {
        gateway.look();
        int passengersBoarding = random.nextInt(51);
        homeAirport.removePassengers(passengersBoarding);
        passengerCount += passengersBoarding;
        log.write(id + " boarded " + passengersBoarding + " passengers at the airport.");
        System.out.println(id + " boarded " + passengersBoarding + " passengers at the airport.");
    }
    
    private void returnToDowntown() throws InterruptedException {
        gateway.look();
        log.write(id + " is returning to downtown from the airport.");
        homeAirport.getAirportT().removeBus(this);
        System.out.println(id + " is returning to downtown.");
        Thread.sleep(random.nextInt(5000, 10001));
    }
    
    private void offloadPassengersDowntown() {
        gateway.look();
        log.write(id + " arrived downtown and offloaded all passengers.");
        System.out.println(id + " arrived downtown and offloaded all passengers.");
        passengerCount = 0;
    }
    
    public String toString(){
        return id;
    }
}





