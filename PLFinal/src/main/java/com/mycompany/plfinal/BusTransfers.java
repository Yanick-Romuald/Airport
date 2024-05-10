/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Yanick
 */
public class BusTransfers implements Serializable{
    
    private ArrayList<Bus> buses = new ArrayList<>();
    private JTextField statusField;
    
    public BusTransfers(JTextField statusField){
        this.statusField=statusField;
    }
    
    public synchronized void addBus(Bus bus){
        buses.add(bus);
        updateTextField();
    }
    
    public synchronized void removeBus(Bus bus){
        buses.remove(bus);
        updateTextField();
    }
    
    private void updateTextField(){
        SwingUtilities.invokeLater(() -> {
            String text = buses.toString();
            statusField.setText(text);
        });
    }
}
