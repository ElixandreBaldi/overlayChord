/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;
import overlay.chord.ChordProtocol;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public interface Action {
    void run(Node node, ChordProtocol protocol);
    
    int getStartTime();
}
