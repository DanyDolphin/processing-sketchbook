/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingsketchbook;

import java.util.LinkedList;
import java.util.Random;
import processing.core.PApplet;

/**
 *
 * @author dany
 */
public class RotatedLines extends PApplet {
  final float FRAMERATE = 60;

  Random random = new Random();

  LinkedList<RotatedCircle> circles;

  public void settings() {
    size(600, 600);
    circles = new LinkedList<>();
    float radius = 50;
    circles.add(new RotatedCircle(1000));
  }

  public void setup() {
    stroke(255, 0, 0);
    frameRate(FRAMERATE);
  }

  public void draw() {
    background(0);
    translate(width / 2, height / 2);
    // filter(BLUR, 6)
    if (frameCount > 0 && frameCount % 200 == 0)
      circles.addFirst(new RotatedCircle(1000));

    if (circles.getLast().radius < 0)
      circles.removeLast();

    for (RotatedCircle circle : circles) {
      circle.reduce();
      circle.show();
    }

    if (frameCount > FRAMERATE * 15 && frameCount < FRAMERATE * 35) {
      saveFrame("rotatedlines/#######.tif");
    }
  }

  class RotatedCircle {
    final int LINE_COUNT = 30;
    final float REDUCE_VELOCITY = 1f;

    float radius;
    RotatedLine[] lines;

    public RotatedCircle(float radius) {
      this.radius = radius;

      lines = new RotatedLine[LINE_COUNT];
      for (int i = 0; i < LINE_COUNT; i++) {
        lines[i] = new RotatedLine(radius);
      }
    }

    public void reduce() {
      radius = radius - REDUCE_VELOCITY;
      for (int i = 0; i < LINE_COUNT; i++) {
        lines[i].radius = radius;
      }
    }

    public void show() {
      for (RotatedLine l : lines) {
        l.rotate();
        l.show();
      }
    }
  }

  class RotatedLine {
    final float MAX_VELOCITY = 0.1f;

    float origin;
    float dest;
    float radius;
    float[] color;
    float velocity;

    public RotatedLine(float radius) {
      origin = random.nextFloat() * 360;
      dest = random.nextFloat() * 360;
      velocity = random.nextFloat() * MAX_VELOCITY;

      this.radius = radius;
      this.color = new float[] { random.nextInt(255), random.nextInt(255), random.nextInt(255) };
    }

    public void rotate() {
      origin += velocity;
      dest += velocity;
    }

    public void show() {
      stroke(color[0], color[1], color[2]);
      line((float) Math.cos(origin) * radius, (float) Math.sin(origin) * radius, (float) Math.cos(dest) * radius,
          (float) Math.sin(dest) * radius);
    }

  }

  public static void main(String[] args) {
    String[] appletArgs = new String[] { "processingsketchbook.RotatedLines" };
    PApplet.main(appletArgs);
  }
}
