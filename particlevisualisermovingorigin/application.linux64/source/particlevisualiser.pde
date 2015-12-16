/**
  * This sketch demonstrates how to use an FFT to analyze an 
  * AudioBuffer and draw the resulting spectrum. <br />
  * It also allows you to turn windowing on and off, 
  * but you will see there is not much difference in the spectrum.<br />
  * Press 'w' to turn on windowing, press 'e' to turn it off.
  */

import ddf.minim.analysis.*;
import ddf.minim.*;

Minim minim;
AudioPlayer jingle;
FFT fft;
ParticleSystem ps;

String windowName;

void setup()
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

void draw()
{
  color c = color(255);
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



void stop()
{
  // always close Minim audio classes when you finish with them
  jingle.close();
  minim.stop();
  
  super.stop();
}
