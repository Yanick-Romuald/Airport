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
 * @author mario
 */

public class TaxiArea implements Serializable{
    
    private ArrayList<Airplane> airplanes = new ArrayList<>();
    private JTextField statusField;
    
    public TaxiArea (JTextField statusField){
        this.statusField = statusField;
    }
    
    public synchronized void addPlane(Airplane airplane){
        airplanes.add(airplane);
        updateTextField();
    }
    
    public synchronized void removePlane(Airplane airplane){
        airplanes.remove(airplane);
        updateTextField();
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
