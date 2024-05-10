/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Semaphore;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author mario, Yanick
 */

public class Log implements Serializable{

    private Semaphore semaphore = new Semaphore(1);
    private static final String LOG_FILE_NAME = "airportEvolution.txt";
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void write(String text) {
        try {
            semaphore.acquire();
            try (FileWriter fw = new FileWriter(LOG_FILE_NAME, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                Date date = new Date();
                pw.println(dateFormat.format(date) + " - " + text);
            } catch (IOException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                semaphore.release();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

