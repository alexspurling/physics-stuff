#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;

out vec3 outColor;

void main()
{
    gl_Position = vec4(position, 1.0);
    outColor = vec3(0.0, 1.0, 0.0);
    gl_PointSize = 32;
}