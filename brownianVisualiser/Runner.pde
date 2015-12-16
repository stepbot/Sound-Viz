
class runner {
  PVector[] history ;
  PVector location;
  PVector velocity;
  color rgb;
 


  runner(int locX, int locY, int r, int g, int b) {
    history = new PVector[255];
    location = new PVector(locX, locY);
    velocity = new PVector(0, 0);
    rgb = color(r, g, b);
    for (int i = 0;i<history.length;i++) {
      history[i] = location;
    }
   
  }

  void update() { 
    float magnitude = fft.getAvg(4)*10;
    
    int m = int(magnitude);
    velocity.x = random(-1, 1);
    velocity.y = random(-1, 1);
    velocity.normalize();
    velocity.mult(m);
    location.add(velocity);
    print(m);
    print(" ");
    print(velocity);
    print(" ");
    print(location);
    print("\n");
    
   

    for (int i = 1; i < history.length; i++) {
      history[i-1] = history[i];
    }

    history[history.length-1] = location;
    
  
    for (int i = 1; i < history.length; i++) {
      stroke(rgb);
      line(int(history[i-1].x),int(history[i-1].y),int(history[i].x),int(history[i].y));
     point(int(location.x),(location.y));
    }
     
  }

}

