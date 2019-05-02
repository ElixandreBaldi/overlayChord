/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.ArrayList;
import overlay.Utils;
import overlay.chord.Parameters;
import overlay.chord.ChordProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;


/**
 *
 * @author elixandre
 */
public class ExecutePing implements Action{
    
    private int indexSucc;
    public ExecutePing() {   
        this.indexSucc = 0;
    }

    ExecutePing(int indexSucc) {
        this.indexSucc = indexSucc;
    }
    @Override
    public void run(Node node, ChordProtocol protocol) {
        
        if(!protocol.getStatus()) return;
        
        int time = CommonState.getIntTime();
        short nextSucc = (short) (protocol.getCurrentId() + indexSucc + 1);
        if(nextSucc >= Network.size()) nextSucc = (short) (nextSucc - Network.size());
        //System.out.println("Nodo "+protocol.getCurrentId()+" enviando ping para o sucess "+indexSucc+" que é o nodo "+nextSucc);
        EDSimulator.add(1, new Ping(protocol.getCurrentId(), time+1, indexSucc), Network.get(nextSucc), Utils.pid);
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}
