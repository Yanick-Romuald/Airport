/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author mario
 */
public class Runways implements Serializable{
    
    private ArrayList<Airplane>[] runways;
    private JTextField[] runwayFields;
    private ReentrantLock[] locks;
    private Condition[] conditions;
    private boolean[] open;
    
    public Runways(JTextField[] runwayFields){
        this.runwayFields = runwayFields;
        runways = new ArrayList[4];
        locks = new ReentrantLock[4];
        conditions = new Condition[4];
        open = new boolean[4];
        
        for (int i = 0; i < runways.length; i++) {
            runways[i] = new ArrayList<>();
            locks[i] = new ReentrantLock(true);
            conditions[i] = locks[i].newCondition();
            open[i] = true;
        }
    }
    
    public boolean addPlane(Airplane airplane){
        for (int i = 0; i < runways.length; i++) {
            locks[i].lock();
            try {
                if(runways[i].isEmpty()){
                    runways[i].add(airplane);
                    updateTextField(i);
                    return true;
                }
            } finally {
                locks[i].unlock();
            }
        }
        return false;
    }
    
    public void removePlane(Airplane airplane){
        for (int i = 0; i < runways.length; i++) {
            locks[i].lock();
            try{
                if(runways[i].contains(airplane)){
                    runways[i].remove(airplane);
                    updateTextField(i);
                    conditions[i].signalAll();
                    break;
                }
            }finally{
                locks[i].unlock();
            }
        }
    }
    
    private void updateTextField(int index){
        SwingUtilities.invokeLater(() -> {
            String text = runways[index].toString();
            runwayFields[index].setText(text + (open[index] ? " Open " : " Closed "));
        });
    }
    
    public void openRunway(int runwayNumber) {
        ReentrantLock lock = locks[runwayNumber];
        lock.lock();
        try {
            open[runwayNumber] = true;
            conditions[runwayNumber].signalAll();
        } finally {
            lock.unlock();
        }
    }
    
    public void closeRunway(int number) {
        locks[number].lock();
        try {
            open[number] = false;
            System.out.println("Runway " + number + " is now closed.");
        } finally {
            locks[number].unlock();
        }
    }
    
    public boolean isRunwayAvailable() {
        for (int i = 0; i < open.length; i++) {
            if (open[i]) {
                return true;
            }
        }
        return false;
    }
    
    
}
