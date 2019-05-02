/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.ArrayList;
import java.util.List;
import overlay.Utils;
import overlay.chord.ChordCreate;
import overlay.chord.ChordProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class FindMostAppropriate implements Action{
    private int startTime;
    private short root;
    
    public FindMostAppropriate(short root, int startTime) {
        this.startTime = startTime;
        this.root = root;
    }
    
    public int counterBit(int n) {
        int count = 0;
        for (int i = 0; i < 32; ++i) {
            if (((n >>> i) & 1) == 1) {
                ++count;
            }
        }
        return count;
    }
    
    public int findMostAppropriate(short[] timestamp, int fuller, ChordProtocol protocol){        
        short newFuller = Utils.findFuller(timestamp, fuller);
        int indexLoop = fuller;
        int distance = 1;
        if(newFuller == fuller) {
            do{
                indexLoop--;
                if(indexLoop == -1) indexLoop = timestamp.length - 1;
                
                if(timestamp[indexLoop] % 2 == 0){
                    if(distance <= 3) {
                        if(fuller > 0) return fuller - 1;
                        return timestamp.length - 1;
                    }
                    
                    if(distance % 2 != 0) distance--;
                    
                    int apropriateDistance = distance / 2;
                    
                    if(apropriateDistance <= fuller) {
                        return fuller - apropriateDistance;
                    } else {
                        return timestamp.length - (apropriateDistance - fuller);
                    }
                } else {
                    distance++;
                }
                
            } while(true);
        }else {
            EDSimulator.add(1, new FindMostAppropriate(protocol.getCurrentId(), CommonState.getIntTime()), Network.get(newFuller), Utils.pid);
            return -1;
        }        
    }

    @Override
    public void run(Node node, ChordProtocol protocol) {        
        
        int indexMostAppropriate = findMostAppropriate(protocol.getTimestamp().clone(), protocol.getCurrentId(), protocol);
        //System.out.println("imprimiu: "+indexMostAppropriate);
        if(indexMostAppropriate >= 0) {
            ChordProtocol target = (ChordProtocol) Network.get(indexMostAppropriate).getProtocol(Utils.pid);
            target.setStatus(true, protocol.getCurrentId());
        }
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}
