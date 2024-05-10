/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plfinal;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Yanick
 */

public class Gateway implements Serializable{
    
    private boolean close = false;
    private Lock l = new ReentrantLock();
    private Condition stop = l.newCondition();
    
    public void look(){
        try {
            l.lock();
            while(close){
                try {
                    stop.await();
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
        } finally {
            l.unlock();
        }
    }
    
    public void open(){
        try{
            l.lock();
            close = false;
            stop.signalAll();
        }catch(Exception e){
        }finally{
            l.unlock();
        }
    }
    
    public void close(){
        try {
            l.lock();
            close = true;
        } catch (Exception e) {
        } finally {
            l.unlock();
        }
    }
    
}
