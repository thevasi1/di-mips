/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

/**
 *
 * @author Usuario
 */
public class Memory {
    
    int[] data;
    
    public Memory(int length){
        this.data = new int[length];
    }
    
    public void setValue(int position, int value){
        data[position] = value;
    }
    
    public int getValue(int position){
       return data[position];
    }
}
