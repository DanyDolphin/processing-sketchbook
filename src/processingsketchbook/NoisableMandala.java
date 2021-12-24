/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingsketchbook;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Nosiable Mandala
 * @author dany
 */
public class NoisableMandala extends PApplet {

  // Framerate
  final float FRAMERATE = 60;
  // Eco? adds alpha to background repaint
  final boolean ECO = true;
  // Control points list: 2 symmetric axis = 1 petal
  ControlPoints[] controlPoints = new ControlPoints[2];
  // Sections: 12 sections == 6 petals
  Section[] sections = new Section[12];

  /**
   * Canvas settings and declarations
   */
  public void settings() {
    size(600, 600);

    controlPoints[0] = new ControlPoints();
    controlPoints[1] = new ControlPoints();

    PVector direction = new PVector(0f, 1f);
    for (int i = 0; i < 12; i++) {
      PVector secondDirection = direction.copy();
      secondDirection.rotate(PI * 2 / 12);
      sections[i] = new Section(
        i,
        controlPoints[i % 2],
        controlPoints[(i + 1) % 2],
        direction,
        secondDirection
      );
      direction = secondDirection;
    }
  }

  /**
   * Canvas modifiers
   */
  public void setup() {
    colorMode(HSB, 360, 100, 100);
    frameRate(FRAMERATE);

    stroke(0);
  }

  /**
   * Draw function
   */
  public void draw() {
    if (ECO) {
      fill(0, 0, 100, 20);
      stroke(0, 0, 100);
      rect(0, 0, width, height);
      stroke(0);
    } else {
      background(0, 0, 100);
    }
    noFill();
    translate(width / 2, height / 2);
    
    for (ControlPoints control: controlPoints) {
      control.alterPoints();
    }
    for (Section section: sections) {
      section.connectAxis();
    }
    
    // Record
    if (frameCount > FRAMERATE * 5 && frameCount < FRAMERATE * 17) {
      saveFrame("noisableMandala2/nm-####.png");
    }
  }

  /*
   * Class that describes the control points
   * used for each mandalla axis
   */
  class ControlPoints {

    // Control points count
    final int POINTS_COUNT = 4;

    // Control points
    float[] points;
    // Control velocities
    float[] velocities;

    ControlPoints() {
      points = new float[POINTS_COUNT];
      velocities = new float[POINTS_COUNT];
      for (int i = 0; i < POINTS_COUNT; i++) {
        velocities[i] = random(-2, 2);
        points[i] = random(0, 300);
      }
      alterPoints();
    }

    /*
     * Funcion that alter the control points
     * using velocities
     */
    void alterPoints() {
      for (int i = 0; i < POINTS_COUNT; i++) {
        float value = points[i] + velocities[i];
        if (value < 0 || value > 300) {
          value = value < 0 ? 0 : 300;
          velocities[i] = random(-2, 2);
        }
        points[i] = value;
      }
    }
  }

  // 2 control points, 12 axis
  // = 12 sections

  /**
   * Sections of the mandala
   */
  class Section {

    // Control points for first axis
    ControlPoints firstControlPoints;
    // Control points for second axis
    ControlPoints secondControlPoints;
    // Direction of first axis
    PVector firstDirection;
    // Direction of second axis
    PVector secondDirection;
    // Section index
    int index;

    /**
     * Constructor
     * @param index
     * @param firstControlPoints
     * @param secondControlPoints
     * @param firstDirection
     * @param secondDirection
     */
    Section(
        int index,
        ControlPoints firstControlPoints,
        ControlPoints secondControlPoints,
        PVector firstDirection,
        PVector secondDirection) {
      this.index = index;
      this.firstDirection = firstDirection;
      this.secondDirection = secondDirection;
      this.firstControlPoints = firstControlPoints;
      this.secondControlPoints = secondControlPoints;
    }

    /**
     * Get the points of the given axis using
     * the control points and directon
     * @param controlPoints the control points of the axis
     * @param direction direction of the axis
     * @return array of points
     */
    float[][] getPointsForAxis(
        ControlPoints controlPoints,
        PVector direction) {
      float[][] points = new float[controlPoints.POINTS_COUNT][2];
      for (int i = 0; i < controlPoints.POINTS_COUNT; i++) {
        PVector dir = direction.copy();
        dir.mult(controlPoints.points[i]);
        points[i][0] = dir.x;
        points[i][1] = dir.y;
      }
      return points;
    }

    /**
     * Connect each points of the two axis in the section
     */
    void connectAxis() {
      float[][] points1 = getPointsForAxis(firstControlPoints, firstDirection);
      float[][] points2 = getPointsForAxis(secondControlPoints, secondDirection);

      for (int i = 0; i < points1.length; i++) {
        for (int j = 0; j < points2.length; j++) {
          stroke(index % 2 == 0 ? (i * points1.length + j) * 90 : (j * points1.length + i) * 90, 100, 50);
          bezier(
            points1[i][0], points1[i][1],
            points1[i][0], points1[i][1],
            (points1[i][0] + points2[j][0] + points2[j][1] - points1[i][1]) / 2, (points1[i][1] + points2[j][1] + points1[i][0] - points2[j][0]) / 2,
            points2[j][0], points2[j][1]
          );
        }
      }
    }
  }

  public static void main(String[] args) {
    String[] appletArgs = new String[] { "processingsketchbook.NoisableMandala" };
    PApplet.main(appletArgs);
  }
}
