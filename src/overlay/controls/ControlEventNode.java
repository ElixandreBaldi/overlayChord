/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import java.util.Random;
import overlay.Utils;
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
public class ControlEventNode implements Control{
    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlEventNode(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {
        Random r = new Random();
        double range = r.nextDouble();
        
        //Utils.printNetwork();        
        if(range > 0.5) { // UP
            if(Utils.countUpQueue < Utils.countNodesOff()) {
                Utils.countUpQueue++;
                int size = Network.size();
                Node target = null;
                ChordProtocol protocol;
                do {                    
                    target = Network.get(r.nextInt(size));                
                    protocol = (ChordProtocol) target.getProtocol(Utils.pid);
                } while (target == null || !protocol.getStatus());                     
                EDSimulator.add(0, new FindEmptyVertex(), target, Utils.pid);
            }
        } else { //Down
            if(Utils.canDown()) {
                //Utils.flagDown = false;
                int size = Network.size();
                ChordProtocol target = null;
                do {                
                    target = (ChordProtocol) Network.get(CommonState.r.nextInt(size)).getProtocol(pid);
                } while (target == null || !target.getStatus());            
                target.setStatus(false, target.getCurrentId());
                //System.out.println("saiu: "+target.getCurrentId()+"    time: "+CommonState.getIntTime());            
            }            
        }
        
        return false;
    }
}
