#version 330

in  vec3 outColor;
out vec4 fragColor;

void main()
{
    // Calculate the distance from the center of the point
    vec2 coord = gl_PointCoord - vec2(0.5);
    float distance = length(coord);

    float alpha = 0;
    float edgeSize = 0.05;
    float pointSize = 0.4;
    // Discard the fragment if it's outside the circle's radius
    if (distance > pointSize + edgeSize) {
        alpha = 0;
    } else if (distance > pointSize - edgeSize) {
        alpha = (pointSize + edgeSize - distance) / edgeSize;
    } else {
        alpha = 1;
    }

    // Set the fragment color
    fragColor = vec4(1.0, 0.5, 0.5, alpha);
}