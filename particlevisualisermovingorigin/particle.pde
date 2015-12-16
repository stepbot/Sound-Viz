// A simple Particle class

class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float diameter;
  float maxD;
  float lifespan;
  color rgb;

  Particle(PVector l,float d, color c, float m) {
    acceleration = new PVector(0,0);
    location = l.get();
    diameter = d;
    maxD = m;
    velocity = new PVector(random(-2,2),random(-2,2));
    rgb = c;
    lifespan = 128;
  }

  void run() {
    update();
    display();
  }

  // Method to update location
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifespan -= 1.0;
  }

  // Method to display
  void display() {
    stroke(rgb);
    fill(rgb,lifespan*2);
    ellipse(location.x,location.y,diameter*3,diameter*3);
  }
  
  // Is the particle still useful?
  boolean isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}

