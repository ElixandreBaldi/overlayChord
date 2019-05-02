/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.ArrayList;
import java.util.List;
import overlay.Responsables;
import overlay.Utils;
import overlay.chord.ChordProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class FindEmptyVertex implements Action{
    
    @Override
    public void run(Node node, ChordProtocol protocol) {        
        short fuller = Utils.findFuller(protocol.getTimestamp().clone(), protocol.getCurrentId());
        EDSimulator.add(1, new FindMostAppropriate(protocol.getCurrentId(), CommonState.getIntTime()), Network.get(fuller), Utils.pid);        ;
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
    
}
