/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingsketchbook;

import java.util.Random;
import java.util.LinkedList;
import processing.core.PApplet;

/**
 *
 * @author dany
 */
public class AutonomousNoise extends PApplet {

  final int DISTANCE = 20;
  final int ECHO_APPERTURE = 40;
  final float VELOCITY_LIMIT = 60;
  final int ECHO_COUNT = 50;
  final int FRAMERATE = 60;

    Noise noise;
    LinkedList<Float> echoes;

    public void settings() {
        size(600, 600);
    }

    public void setup() {
      frameRate(FRAMERATE);
      noise = new Noise(radians(30));
      echoes = new LinkedList<>();
      for (int i = 0; i < ECHO_COUNT; i++)
        echoes.add(0f);
      noFill();
    }

    public void draw() {
      translate(width/2, height/2);
      background(255);
      noise.show();
      echoes.addFirst(noise.rotation);
      echoes.pollLast();
      int i = 1;
      for (Float echo: echoes) {
        drawEcho(echo, i++);
      }

      if (frameCount > FRAMERATE * 5 && frameCount < FRAMERATE * 10) {
          saveFrame("autonomousNoise/#######.png");
      }
    }

    public void drawEcho(float rotation, int position) {
      pushMatrix();
      rotate(rotation);
      int distance = position * DISTANCE + DISTANCE;
      arc(0, 0, distance, distance, -radians(180 - ECHO_APPERTURE / 2), -radians(ECHO_APPERTURE / 2));
      arc(0, 0, distance, distance, radians(ECHO_APPERTURE / 2), radians(180 - ECHO_APPERTURE / 2));
      popMatrix();
    }

    class Noise {
      float rotation;
      float velocity;
      float aceleration;

      public Noise(float rotation) {
        this.rotation = rotation;
        this.velocity = 0;
        this.aceleration = 0;
      }

      public void automove() {
        // randomly change aceleration
        Random random = new Random();
        float delta = random.nextFloat() * 8 - 4;
        aceleration = delta;
        // apply aceleration
        velocity += aceleration;
        // limit velocity
        if (velocity > VELOCITY_LIMIT) velocity = VELOCITY_LIMIT;
        if (velocity < -VELOCITY_LIMIT) velocity = -VELOCITY_LIMIT;
        // apply velocity
        rotation += radians(velocity / 10);
      }

      public void show() {
        automove();
        drawEcho(rotation, 0);
      }
    }

    public static void main(String[] args) {
        String[] appletArgs = new String[] { "processingsketchbook.AutonomousNoise" };
        PApplet.main(appletArgs);
    }
}
