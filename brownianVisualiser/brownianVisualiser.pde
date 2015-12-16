import ddf.minim.*;
import ddf.minim.analysis.*;

Minim minim;
FFT fft;
AudioPlayer jingle;
runner f1;
void setup()
{
  size(1280,800,P3D);
  minim = new Minim(this);
  jingle = minim.loadFile("scott.mp3", 2048);
  fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
  fft.logAverages(440,1);
  jingle.loop();
  f1 = new runner(width/2,height/2,0,0,255);
  
  
}
void draw(){
  background(0);
  fft.forward(jingle.mix);
  f1.update();

}

