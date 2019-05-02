/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import overlay.Utils;
import overlay.chord.ChordCreate;
import overlay.chord.ChordProtocol;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;

/**
 *
 * @author elixandre
 */
public class ControlDownNode implements Control{
    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlDownNode(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {
        if(ChordCreate.scenario == 3 || ChordCreate.scenario == 4) {
            if(!Utils.flagDown) return false;
            int index = Network.size() / 2;
            ChordProtocol target = (ChordProtocol) Network.get(index).getProtocol(pid);
            target.setStatus(false, target.getCurrentId());
            Utils.flagDown = false;
        } else {
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
