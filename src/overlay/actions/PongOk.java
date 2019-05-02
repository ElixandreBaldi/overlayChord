/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.math.BigInteger;
import overlay.Utils;
import overlay.chord.ChordCreate;
import overlay.chord.ChordProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class PongOk implements Action{
    private int sender;
    private short[] timestampSender;
    private int startTime;    

    public PongOk(int sender, short[] succListSender, int startTime) {
        this.sender = sender;
        this.timestampSender = succListSender;
        this.startTime = startTime;        
    }

    @Override
    public void run(Node node, ChordProtocol protocol) {
        //System.out.println(protocol.getCurrentId()+";"+sender+";"+startTime+";"+time+";"+dif);                
        //System.out.println("Nodo: "+protocol.getCurrentId()+" recebeu Pong de nodo "+sender+"       no start: "+startTime+"      no tempo: "+CommonState.getIntTime());        
        Utils.updateTimestampLocal(protocol.getTimestamp(), this.timestampSender, protocol.getCurrentId(), this.sender);           
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}