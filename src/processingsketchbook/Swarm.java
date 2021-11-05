/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingsketchbook;

import java.util.ArrayList;
import processing.core.PApplet;

/**
 *
 * @author dany
 */
public class Swarm extends PApplet{
    final int BEE_LEVELS = 4;
    final float LEVEL_DISTANCE = 40;
    final float FRAMERATE = 60;
    
    ArrayList<Bee> bees;
    
    public void settings() {
        size(600, 600);
        bees = new ArrayList<>();
        int beesInLevel = 1;
        for (int i = 0; i < BEE_LEVELS; i++) {
            beesInLevel = i * 6;
            if (beesInLevel == 0) beesInLevel = 1;
            for (int j = 0; j < beesInLevel; j++) {
                float detachX = (float)Math.sin(j * 2 * Math.PI / beesInLevel) * LEVEL_DISTANCE * i;
                float detachY = (float)-Math.cos(j * 2 * Math.PI / beesInLevel) * LEVEL_DISTANCE * i;
                bees.add(new Bee(detachX, detachY));
            }
        }
    }
    
    public void setup() {
        fill(255, 230, 0);
        stroke(255, 230, 0);
        strokeWeight(10);
        frameRate(FRAMERATE);
    }    
    
    public void draw() {
        background(34, 16, 71);
        for (Bee bee: bees) {
            bee.move();
            bee.show();
        }
        /*if (frameCount > FRAMERATE * 5 && frameCount < FRAMERATE * 10) {
            saveFrame("swarm/#######.png");
        }*/
    }
    
    class Bee {
        final float RADIUS = 150;
        final float MOVEMENT_FACTOR = 40;
        final float SIZE = 10;
        final int TRACE_LENGTH = 6;
        
        float detachX;
        float detachY;
        float[][] trace;
        
        public Bee(float detachX, float detachY) {
            this.trace = new float[TRACE_LENGTH][2];
            this.detachX = detachX;
            this.detachY = detachY;
        }
        
        public void move() {
            for (int i = 0; i < TRACE_LENGTH-1; i++) {
                trace[i][0] = trace[i+1][0];
                trace[i][1] = trace[i+1][1];
            }
            float x = (float)Math.sin((frameCount + TRACE_LENGTH-1) / MOVEMENT_FACTOR * 2) * RADIUS;
            float y = -(float)Math.sin((frameCount + TRACE_LENGTH-1) / MOVEMENT_FACTOR) * RADIUS;
            trace[TRACE_LENGTH-1][0] = x;
            trace[TRACE_LENGTH-1][1] = y;
        }
        
        public void show() {
            resetMatrix();
            translate(width / 2, height / 2);
            for (int i = 0; i < TRACE_LENGTH-1; i++) {
                line(
                        trace[i][0] + detachX, trace[i][1] + detachY, 
                        trace[i+1][0] + detachX, trace[i+1][1] + detachY
                );
            }
        }
    }
    
    public static void main(String[] args) {
        String[] appletArgs = new String[] { "processingsketchbook.Swarm" };
        PApplet.main(appletArgs);
    }
}