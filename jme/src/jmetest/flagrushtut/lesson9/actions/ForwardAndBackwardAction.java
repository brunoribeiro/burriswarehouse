/*
 * Copyright (c) 2003-2009 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jmetest.flagrushtut.lesson9.actions;

import jmetest.flagrushtut.lesson9.Vehicle;

import com.jme.input.action.InputActionEvent;
import com.jme.input.action.KeyInputAction;

/**
 * AccelerateAction defines the action that occurs when the key is pressed to 
 * speed the Vehicle up. It obtains the velocity of the vehicle and 
 * translates the vehicle by this value.
 * @author Mark Powell
 *
 */
public class ForwardAndBackwardAction extends KeyInputAction {
    public static final int FORWARD = 0;
    public static final int BACKWARD = 1;
    //the node to manipulate
    private Vehicle node;
    private int direction;

    /**
     * The vehicle to accelerate is supplied during construction.
     * @param node the vehicle to speed up.
     * @param direction Constant either FORWARD or BACKWARD
     */
    public ForwardAndBackwardAction(Vehicle node, int direction) {
        this.node = node;
        this.direction = direction;
    }

    /**
     * the action calls the vehicle's accelerate or brake command which adjusts its velocity.
     */
    public void performAction(InputActionEvent evt) {
        if(direction == FORWARD) {
            node.accelerate(evt.getTime());
        } else if(direction == BACKWARD){
            node.brake(evt.getTime());
        }
    }
}