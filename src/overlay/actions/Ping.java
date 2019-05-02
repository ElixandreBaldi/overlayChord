/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.Queue;
import overlay.Utils;
import overlay.chord.ChordProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;
import peersim.transport.Transport;

/**
 *
 * @author elixandre
 */
public class Ping implements Action{
    private short sender;   
    private int startTime;   
    private int indexSucc;   
    
    public Ping(short sender, int startTime, int indexSucc) {
        this.sender = sender;
        this.startTime = startTime;
        this.indexSucc = indexSucc;
    }

    public Ping() {        
    }

    @Override
    public void run(Node node, ChordProtocol protocol) {
        Action message = null;
        int scheduler = 1;
        
        //System.out.println("Nodo: "+protocol.getCurrentId()+" recebeu Ping de nodo "+sender+"  "+startTime+" "+CommonState.getIntTime());
        
        if(protocol.getStatus()){
            //System.out.println("Nodo: "+protocol.getCurrentId()+" recebeu Ping de nodo "+sender+"  "+startTime+" "+CommonState.getIntTime());        
            message = new PongOk(protocol.getCurrentId(), protocol.getTimestamp().clone(), startTime);
        } else{
            message = new PongError(protocol.getCurrentId(), protocol.getTimestamp().clone(), startTime, this.indexSucc);
            scheduler = 3;
        }
        
        EDSimulator.add(
            scheduler, 
            message, 
            Network.get(this.sender),
            Utils.pid);
    }            
    
    @Override
    public int getStartTime() {
        return -1;
    }
}
