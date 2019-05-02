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
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandrebaldi
 */
public class PongError implements Action{
    private int sender;
    private short[] timestampSender;
    private int startTime;    
    private int indexSucc;

    public PongError(int sender, short[] succList, int startTime, int indexSucc) {
        this.sender = sender;
        this.timestampSender = succList;
        this.startTime = startTime;        
        this. indexSucc = indexSucc;
    }

    @Override
    public void run(Node node, ChordProtocol protocol) {        
        //System.out.println("Nodo: "+protocol.getCurrentId()+" detectou falha no nodo "+sender+"       no start: "+startTime+"      no tempo: "+CommonState.getIntTime()+ "      "+this.timestampSender.length);
        if(Utils.isPair(protocol.getTimestamp()[sender])){
            protocol.getTimestamp()[sender]++;            
            Utils.countDiagnostic++;
                        
            
            if(Network.size() == (Utils.countDiagnostic + 1)) {
                Utils.timeDiagnostic = CommonState.getIntTime(); 
                if( (ChordCreate.scenario == 3 || ChordCreate.scenario == 4) && (Utils.countPuts >= Utils.nPuts || Utils.countLookup == Utils.nLookups)){
                    Utils.finish(CommonState.getIntTime());
                }
            }                        
        }
        
        this.indexSucc++;
        if(this.indexSucc >= this.timestampSender.length) this.indexSucc = 0;
        EDSimulator.add(0, new ExecutePing(indexSucc), node, Utils.pid);
        
        
        //protocol.printTimestamp();
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}