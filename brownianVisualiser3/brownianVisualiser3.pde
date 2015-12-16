/**
 * Brownian motion. 
 * 
 * Recording random movement as a continuous line. 
 */
import ddf.minim.*;
import ddf.minim.analysis.*;

Minim minim;
FFT fft;
AudioInput jingle;

Runner r1;
Runner r2;
Runner r3;
Runner r4;
Runner r5;
Runner r6;

float back = 0;
boolean decrease;

void setup() 
{
  size(1280,800,P3D);
  minim = new Minim(this);
  jingle = minim.getLineIn(Minim.STEREO,2048);
  fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
  fft.logAverages(440,1);
  
  frameRate(30);
  r1 = new Runner(95,189,206,0);
  r2 = new Runner(55,182,206,1);
  r3 = new Runner(255,128,115,2);
  r4 = new Runner(255,82,64,3);
  r5 = new Runner(255,211,115,4);
  r6 = new Runner(255,196,64,6);
  r7 = new Runner(166,81,0,7);
  r8 = new Runner(191,118,48,8);
  
  
}

void draw() 
{
  if(back==0){decrease=false;}
  else if(back==5000){decrease=true;}
  
  if(decrease){back-=.5;}
  else{back+=.5;}
  
  fft.forward(jingle.mix);
  background(map(back,0,5000,0,50));
  r1.run();
  r2.run();
  r3.run();
  r4.run();
  r5.run();
  r6.run();
  r7.run();
  r8.run();
  
  
}
