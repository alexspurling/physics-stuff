package org.example;

public class Ball {

    private Vector2D pos;
    private Vector2D vel;
    private double radius;

    public Ball(Vector2D pos, double radius) {
        this(pos, new Vector2D(0, 0), radius);
    }

    public Ball(Vector2D pos, Vector2D vel, double radius) {
        this.pos = pos;
        this.vel = vel;
        this.radius = radius;
    }

    public Vector2D getPos() {
        return pos;
    }

    public Vector2D getVel() {
        return vel;
    }

    public void setPos(Vector2D pos) {
        this.pos = pos;
    }

    public void setVel(Vector2D vel) {
        this.vel = vel;
    }

    public double getRadius() {
        return radius;
    }

    public void invertVel() {
        this.vel = vel.invert();
    }

    // Method to handle collision with another ball
    public void handleCollision(Ball other) {
        Vector2D deltaPos = other.getPos().subtract(this.getPos());
        double distance = deltaPos.magnitude();

        if (distance < this.getRadius() + other.getRadius()) {
            // Simplified collision handling for equal masses
            Vector2D tempVel = this.getVel();
            this.setVel(other.getVel());
            other.setVel(tempVel);
        }
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
