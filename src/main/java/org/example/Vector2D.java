package org.example;

public record Vector2D(double x, double y) {

    // Method to add another vector
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    // Method to subtract another vector
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    // Method to multiply by a scalar
    public Vector2D scale(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    // Method to calculate the magnitude (length) of the vector
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    // Method to normalize the vector (make it a unit vector)
    public Vector2D normalize() {
        double mag = magnitude();
        if (mag == 0) {
            throw new ArithmeticException("Cannot normalize a zero vector");
        }
        return new Vector2D(x / mag, y / mag);
    }

    // Method to calculate the dot product with another vector
    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    public Vector2D invert() {
        return new Vector2D(-x, -y);
    }

    // Method to reflect the vector over a normal vector
    public Vector2D reflect(Vector2D normal) {
        double dotProduct = this.dot(normal);
        Vector2D normalScaled = normal.scale(2 * dotProduct);
        return this.subtract(normalScaled);
    }
}
