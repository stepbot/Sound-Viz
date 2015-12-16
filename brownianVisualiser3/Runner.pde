class Runner {
  int num = 2000;
  int range = 6;
  int freq;
  color rgb;
  float[] ax = new float[num];
  float[] ay = new float[num];
  float[] az = new float[num];

  Runner(int r,int g,int b, int f) {
    for (int i = 0; i < num; i++) {
      ax[i] = width/2;
      ay[i] = height/2;
      
    }
    rgb = color(r,g,b);
    freq = f;
  }

  void run() {
    // Shift all elements 1 place to the left
    for (int i = 1; i < num; i++) {
      ax[i-1] = ax[i];
      ay[i-1] = ay[i];
    }

    // Put a new value at the end of the array
    ax[num-1] += random(-range, range)*int((fft.getAvg(freq)));
    ay[num-1] += random(-range, range)*int((fft.getAvg(freq)));

    // Constrain all points to the screen
    ax[num-1] = constrain(ax[num-1], 0, width);
    ay[num-1] = constrain(ay[num-1], 0, height);

    // Draw a line connecting the points
    for (int i = 1; i < num; i++) { 
      float j = i*(1/(pow((float(num-i)),0.3)));
      float val = map(j,0,2000,0,255);
      stroke(rgb,int(val));
      line(ax[i-1], ay[i-1], ax[i], ay[i]);
    }
  }
}




