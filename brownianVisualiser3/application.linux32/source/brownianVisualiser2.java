import processing.core.*; 
import processing.data.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class brownianVisualiser2 extends PApplet {

/**
 * Brownian motion. 
 * 
 * Recording random movement as a continuous line. 
 */



Minim minim;
FFT fft;
AudioPlayer jingle;

Runner r1;
Runner r2;
Runner r3;
Runner r4;
Runner r5;
Runner r6;

float back = 0;
boolean decrease;

public void setup() 
{
  size(1280,800,P3D);
  minim = new Minim(this);
  jingle = minim.loadFile("Impaction.mp3", 2048);
  fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
  fft.logAverages(440,1);
  jingle.loop();
  frameRate(30);
  r1 = new Runner(255,0,255,0);
  r2 = new Runner(255,255,0,1);
  r3 = new Runner(0,255,255,2);
  r4 = new Runner(0,0,255,3);
  r5 = new Runner(0,255,0,4);
  r6 = new Runner(255,0,0,5);
  
}

public void draw() 
{
  if(back==0){decrease=false;}
  else if(back==5000){decrease=true;}
  
  if(decrease){back-=.5f;}
  else{back+=.5f;}
  
  fft.forward(jingle.mix);
  background(map(back,0,5000,0,50));
  r1.run();
  r2.run();
  r3.run();
  r4.run();
  r5.run();
  r6.run();
  
}
class Runner {
  int num = 2000;
  int range = 6;
  int freq;
  int rgb;
  float[] ax = new float[num];
  float[] ay = new float[num];

  Runner(int r,int g,int b, int f) {
    for (int i = 0; i < num; i++) {
      ax[i] = width/2;
      ay[i] = height/2;
    }
    rgb = color(r,g,b);
    freq = f;
  }

  public void run() {
    // Shift all elements 1 place to the left
    for (int i = 1; i < num; i++) {
      ax[i-1] = ax[i];
      ay[i-1] = ay[i];
    }

    // Put a new value at the end of the array
    ax[num-1] += random(-range, range)*PApplet.parseInt((fft.getAvg(freq)));
    ay[num-1] += random(-range, range)*PApplet.parseInt((fft.getAvg(freq)));

    // Constrain all points to the screen
    ax[num-1] = constrain(ax[num-1], 0, width);
    ay[num-1] = constrain(ay[num-1], 0, height);

    // Draw a line connecting the points
    for (int i = 1; i < num; i++) { 
      float j = i*(1/(pow((PApplet.parseFloat(num-i)),0.3f)));
      float val = map(j,0,2000,0,255);
      stroke(rgb,PApplet.parseInt(val));
      line(ax[i-1], ay[i-1], ax[i], ay[i]);
    }
  }
}




  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "brownianVisualiser2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
