package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.*;

public class AnimationPenguin implements ActionListener, KeyListener {
    private Button go;
    private TransformGroup wholePenguin;
    private Transform3D translateTransform;
    private Transform3D rotateTransformX;
    private Transform3D rotateTransformY;
    private Transform3D rotateTransformZ;

    private JFrame mainFrame;

    private float sign=1.0f;
    private float zoom=0.5f;
    private float xloc=-1.5499996f;
    private float yloc=-2.1499991f;
    private float zloc=0.0f;
    private int moveType=1;
    private int sideSign = 1;
    private int direction = 1;
    private int speedCoff = 1;
    private Timer timer;

    int side = 0;

    public AnimationPenguin(TransformGroup wholePenguin, Transform3D trans,JFrame frame){
        go = new Button("Go");
        this.wholePenguin=wholePenguin;
        this.translateTransform=trans;
        this.mainFrame=frame;

        rotateTransformX= new Transform3D();
        rotateTransformY= new Transform3D();
        rotateTransformZ= new Transform3D();
        rotateTransformY.rotY(Math.PI/2);
        translateTransform.mul(rotateTransformY);


        FirstMainClass.canvas.addKeyListener(this);
        timer = new Timer(100, this);

        Panel p =new Panel();
        p.add(go);
        mainFrame.add("North",p);
        go.addActionListener(this);
        go.addKeyListener(this);
    }

    private void initialPenguinState(){
        xloc=-1.5499996f;
        yloc=-2.1499991f;
        zloc=0.0f;
        zoom=0.2f;
        moveType=1;
        sign=1.0f;
        if(timer.isRunning()){timer.stop();}
        go.setLabel("Go");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // start timer when button is pressed
        if (e.getSource()==go){
            if (!timer.isRunning()) {
                timer.start();
                go.setLabel("Stop");
            }
            else {
                timer.stop();
                System.out.println("zoom: " + zoom);
                System.out.println("x:" + xloc + "y:" + yloc + "z:" + zloc);
                go.setLabel("Go");
            }
        }
        else {
            Move(moveType);
            translateTransform.setScale(new Vector3d(zoom,zoom,zoom));
            translateTransform.setTranslation(new Vector3f(xloc,yloc,zloc));
            wholePenguin.setTransform(translateTransform);
        }
    }

    private void Move(int mType){
            if(side == 0) {
                rotateTransformZ.rotZ(Math.PI/16);
                translateTransform.mul(rotateTransformZ);
                side = 1;
                sideSign = -1;
            }else if(side == 2){
                rotateTransformZ.rotZ(-Math.PI/16);
                translateTransform.mul(rotateTransformZ);
                side = 1;
                sideSign = 1;
            } else{
                rotateTransformZ.rotZ(sideSign*Math.PI/16);
                translateTransform.mul(rotateTransformZ);
                side = 2;
            }
            if(xloc >= 1.5499996f && direction == 1 && sign == 1) {
                rotateTransformY.rotY(Math.PI / 2);
                translateTransform.mul(rotateTransformY);
                direction = 2;
            }
            if(zoom <= 0.34000027 && direction == 2 && sign == 1){
                rotateTransformY.rotY(Math.PI / 2);
                translateTransform.mul(rotateTransformY);
                direction = 1;
                sign = -1;
            }
            if(xloc <= -1.5499996f && direction == 1 && sign == -1){
                rotateTransformY.rotY(Math.PI / 2);
                translateTransform.mul(rotateTransformY);
                rotateTransformZ.rotZ(Math.PI/16);
                translateTransform.mul(rotateTransformZ);
                direction = 2;
            }
            if(zoom >= 0.5 && direction == 2 && sign == -1){
                rotateTransformY.rotY(Math.PI / 2);
                translateTransform.mul(rotateTransformY);
                rotateTransformX.rotX(-Math.PI/16);
                translateTransform.mul(rotateTransformX);
                direction = 1;
                sign = 1;
            }
            if(direction == 1){
                xloc = xloc + speedCoff*sign*.05f;
            }else{
                zoom= zoom - speedCoff*sign*0.006f;
                zloc= zloc + sign*0.015f;
            }
        }

    @Override
    public void keyTyped(KeyEvent e) {
        //Invoked when a key has been typed.
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }

}
