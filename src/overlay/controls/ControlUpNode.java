/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import java.util.Random;
import overlay.Utils;
import overlay.actions.Action;
import overlay.actions.FindEmptyVertex;
import overlay.chord.ChordProtocol;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class ControlUpNode implements Control{
    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlUpNode(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {                
        if(Utils.countUpQueue <= Network.size()) {
            Utils.countUpQueue++;
            System.out.println(""+Utils.countUpQueue);
            int size = Network.size();
            Node target = null;
            ChordProtocol protocol;
            do {
                Random r = new Random();  
                target = Network.get(r.nextInt(size));                
                protocol = (ChordProtocol) target.getProtocol(Utils.pid);                                
            } while (target == null || !protocol.getStatus());        
            EDSimulator.add(1, new FindEmptyVertex(), target, Utils.pid);
        }
        return false;
    }
}
