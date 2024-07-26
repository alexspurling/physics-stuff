#version 330

in  vec3 outColor;
out vec4 fragColor;

void main()
{
    // Calculate the distance from the center of the point
    vec2 coord = gl_PointCoord - vec2(0.5);
    float distance = length(coord);

    // Discard the fragment if it's outside the circle's radius
    if (distance > 0.5)
    discard;

    // Set the fragment color
    fragColor = vec4(1.0, 0.5, 0.2, 1.0);
}