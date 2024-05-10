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
 * @author Yanick
 */
public class BoardingGates implements Serializable{
    
    private ArrayList<Airplane>[] gates;
    private JTextField[] gateFields;
    private ReentrantLock[] locks;
    private Condition[] conditions;
    
    public BoardingGates(JTextField[] gateFields){
        this.gateFields = gateFields;
        gates = new ArrayList[6];
        locks = new ReentrantLock[6];
        conditions = new Condition[6];
        
        for (int i = 0; i < gates.length; i++) {
            gates[i] = new ArrayList<>();
            locks[i] = new ReentrantLock(true);
            conditions[i] = locks[i].newCondition();
        }
    }
    
    public boolean addPlane(Airplane airplane, boolean isLanding){
        for (int i = 0; i < gates.length; i++) {
            locks[i].lock();
            try {
                if(gates[i].isEmpty() && canAddPlane(i, isLanding)){
                    gates[i].add(airplane);
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
        for (int i = 0; i < gates.length; i++) {
            locks[i].lock();
            try{
                if(gates[i].contains(airplane)){
                    gates[i].remove(airplane);
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
            String text = gates[index].toString();
            gateFields[index].setText(text);
        });
    }
    
    private boolean canAddPlane(int index, boolean isLanding){
        return (index == 0 && !isLanding) || (index == 1 && isLanding) || (index > 1);
    }
}
