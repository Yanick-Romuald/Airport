/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author mario
 */

public class MaintenanceHall implements Serializable{
    
    private ArrayList<Airplane> airplanes= new ArrayList<>();
    private JTextField statusField;
    private Semaphore semaphore = new Semaphore(20, true);
    private Lock lock = new ReentrantLock(true);
    
    public MaintenanceHall(JTextField statusField){
        this.statusField = statusField;
    }
    
    public void addPlane(Airplane airplane){
        try {
            semaphore.acquire();
            lock.lock();
            try {
                airplanes.add(airplane);
                updateTextField();
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }
    
    public void removePlane(Airplane airplane){
        lock.lock();
        try {
            airplanes.remove(airplane);
            updateTextField();
        } finally {
            lock.unlock();
            semaphore.release();
        }
    }
    
    private void updateTextField(){
        SwingUtilities.invokeLater(() -> {
            String text = airplanes.toString();
            statusField.setText(text);
        });
    }
    
    public int getAirplaneCount() {
        return airplanes.size();
    }

}
