size(800, 800);
background(255);
colorMode(HSB, 360, 100, 100);

// draw 1 000 000 lines
for (int i = 0; i < 10000; i++) {
  stroke(int(random(360)), int(random(100)), int(random(100)));
  float currentX = 0;
  float currentY = random(800);
  // moves 100 000 times
  for (int j = 0; j < 200; j++) {
    float newX = currentX;
    float newY = currentY;
    int direction = int(random(3));
    newX += 5;
    if (direction == 0)
      newY -= 5;
    else if (direction == 2)
      newY += 5;
    line(currentX, currentY, newX, newY);
    currentX = newX;
    currentY = newY;
  }
}

save("thousandMountains.png");
