import processing.core.*; 
import processing.data.*; 
import processing.opengl.*; 

import ddf.minim.analysis.*; 
import ddf.minim.*; 

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

public class particlevisualiser extends PApplet {

/**
  * This sketch demonstrates how to use an FFT to analyze an 
  * AudioBuffer and draw the resulting spectrum. <br />
  * It also allows you to turn windowing on and off, 
  * but you will see there is not much difference in the spectrum.<br />
  * Press 'w' to turn on windowing, press 'e' to turn it off.
  */




Minim minim;
AudioPlayer jingle;
FFT fft;
ParticleSystem ps;

String windowName;

public void setup()
{
  size(1280, 800, P3D);
  textMode(SCREEN);
  
  minim = new Minim(this);
  ps = new ParticleSystem(new PVector(width/2,height/2));
  jingle = minim.loadFile("scott.mp3", 2048);
  jingle.loop();
  
  // create an FFT object that has a time-domain buffer 
  // the same size as jingle's sample buffer
  // note that this needs to be a power of two 
  // and that it means the size of the spectrum
  // will be 512. see the online tutorial for more info.
  fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
  fft.logAverages(440,1);
  print(fft.avgSize());
  
  textFont(createFont("Arial", 16));
  
  windowName = "None";
}

public void draw()
{
  int c = color(255);
  float d;
  background(0);
  ps.origin.x = random(0,width);
  ps.origin.y = random(0,height);
  // perform a forward FFT on the samples in jingle's left buffer
  // note that if jingle were a MONO file, 
  // this would be the same as using jingle.right or jingle.left
  fft.forward(jingle.mix);
  for(int i = 0; i < fft.avgSize(); i++)
  {
    d = fft.getAvg(i);
    // draw the line for frequency band i, scaling it by 4 so we can see it a bit better
    switch(i){
      case  0 :
        c = color(255,0,0);
        break;
      
      case 1:
        c = color(0,0,255);
      break;
      case 2:
        c = color(0,255,0);
      break;
      case 3:
        c = color(0,255,255);
      break;
      case 4:
        c = color(255,255,0);
      break;
      case 5:
        c = color(255,0,255);
      break;
      
    }
    ps.addParticle(d,c);
  }
  fill(255);
  // keep us informed about the window being used
  background(0);
 
  ps.run();
}



public void stop()
{
  // always close Minim audio classes when you finish with them
  jingle.close();
  minim.stop();
  
  super.stop();
}
// A class to describe a group of Particles
// An ArrayList is used to manage the list of Particles 

class ParticleSystem {
  ArrayList<Particle> particles;
  PVector origin;
  

  ParticleSystem(PVector location) {
    origin = location.get();
    particles = new ArrayList<Particle>();
  }

  public void addParticle(float d, int c) {
    particles.add(new Particle(origin,d,c));
  }

  public void run() {
    Iterator<Particle> it = particles.iterator();
    while (it.hasNext()) {
      Particle p = it.next();
      p.run();
      if (p.isDead()) {
        it.remove(); 
      }
    }
  }
}
// A simple Particle class

class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float diameter;
  float lifespan;
  int rgb;

  Particle(PVector l,float d, int c) {
    acceleration = new PVector(0,0);
    velocity = new PVector(random(-2,2),random(-2,2));
    location = l.get();
    diameter = d;
    rgb = c;
    lifespan = 127;
  }

  public void run() {
    update();
    display();
  }

  // Method to update location
  public void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifespan -= 1.0f;
  }

  // Method to display
  public void display() {
    stroke(rgb);
    fill(rgb,lifespan*2);
    ellipse(location.x,location.y,diameter*3,diameter*3);
  }
  
  // Is the particle still useful?
  public boolean isDead() {
    if (lifespan < 0.0f) {
      return true;
    } else {
      return false;
    }
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "particlevisualiser" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
