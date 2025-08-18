package org.pioupiou.utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {
    private Map<Integer,Integer> keymap = new HashMap<Integer, Integer>();
    private boolean[] keysPressed;
    private JFrame testJFrame = new JFrame(); //needs one to detect key events //TODO to clean and re-architecture later

    public Keyboard(){
        keymap.put(0,0x0);
        keymap.put(0,0x1);
        keymap.put(0,0x2);
        keymap.put(0,0x3);
        keymap.put(0,0x4);
        keymap.put(0,0x5);
        keymap.put(0,0x6);
        keymap.put(0,0x7);
        keymap.put(0,0x8);
        keymap.put(0,0x9);
        keymap.put(0,0xA);
        keymap.put(0,0xB);
        keymap.put(0,0xC);
        keymap.put(0,0xD);
        keymap.put(0,0xE);
        keymap.put(0,0xF);

        keysPressed = new boolean[16]; //init keys pressed to empty

        //class abstract action TO EVOLVE/MOVE SOMEWHERE //TODO move to view for keystroke detection
        class ActionAPressed extends AbstractAction{
            public ActionAPressed() {
            }
            public void actionPerformed(ActionEvent e) {
                onNextKeyPress(Integer.parseInt("A"));
            }
        }
        testJFrame.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("A"), "aPressed");
        ActionAPressed aPressed = new ActionAPressed();
        testJFrame.getRootPane().getActionMap().put("aPressed", aPressed);
        //FIN GESTION EXAMPLE KEYSTROKE

    }

    public boolean isKeyPressed(int keycode){
        return keysPressed[keycode];
    }

    public int onKeyDown(int event){
        int currentKey;
        if(keymap.containsKey(event)){
            currentKey = keymap.get(event);
            //update keyPressed array
            keysPressed[currentKey] = true;
        }
        else currentKey = -1;


        //manage onNextKeyPress and check the pressed key is mapped to ones of the chip8 keyboard //TODO
        if(onNextKeyPress(currentKey) != -1 && currentKey != -1){
            onNextKeyPress(currentKey);
        }

        return currentKey;
    }

    public int onKeyUp(int event){
        int currentKey = -1;
        if(keymap.containsKey(event)){
            currentKey = keymap.get(event);
            //update keyPressed array
            keysPressed[currentKey] = false;
        }
        return currentKey;
    }

    public int onNextKeyPress(int key){
        return key;
    }
}
