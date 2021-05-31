package lab5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.*;

public class AnimationDog implements ActionListener, KeyListener {
    private Button go;
    private TransformGroup wholeDog;
    private Transform3D translateTransform;
    private Transform3D rotateTransformX;
    private Transform3D rotateTransformY;
    private Transform3D rotateTransformZ;

    private JFrame mainFrame;

    private float sign = 1.0f;
    private float zoom = 1.0f;
    private float xloc = -1.0f;
    private float yloc = -1.2f;
    private float zloc = -0.5f;
    private Timer timer;

    public AnimationDog(TransformGroup wholeDog, Transform3D trans, JFrame frame){
        go = new Button("Go");
        this.wholeDog
 = wholeDog;
        this.translateTransform=trans;
        this.mainFrame=frame;

        rotateTransformX= new Transform3D();
        rotateTransformY= new Transform3D();
        rotateTransformZ= new Transform3D();

        MainClass.canvas.addKeyListener(this);
        timer = new Timer(50, this);

        Panel p =new Panel();
        p.add(go);
        mainFrame.add("North",p);
        go.addActionListener(this);
        go.addKeyListener(this);
    }

    private void initialDogState(){
        xloc = -1f;
        yloc = -1.20f;
        zloc = 0f;
        zoom = 1.0f;
        sign =1.0f;

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
                go.setLabel("Go");
            }
        }
        else {
            Move();
            translateTransform.setScale(new Vector3d(zoom, zoom, zoom));
            translateTransform.setTranslation(new Vector3f(xloc, yloc, zloc));
            wholeDog.setTransform(translateTransform);
        }
    }

    private void Move(){
        xloc += 0.05 * sign;
        if (Math.abs(xloc) >= 1 ) {
            sign = -1.0f * sign;
            rotateTransformZ.rotZ(2*Math.PI);
            translateTransform.mul(rotateTransformZ);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Invoked when a key has been typed.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar()=='a') {
            rotateTransformX.rotX(Math.PI/2);
            translateTransform.mul(rotateTransformX);
        }
        if (e.getKeyChar()=='s') {
            rotateTransformY.rotY(Math.PI/2);
            translateTransform.mul(rotateTransformY);
        }
        if (e.getKeyChar()=='d') {
            rotateTransformZ.rotZ(Math.PI/2);
            translateTransform.mul(rotateTransformZ);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }
}
