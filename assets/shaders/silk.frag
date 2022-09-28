#define HIGHP

#define C2 vec3(109.0, 114.0, 107.0) / 255.0
#define C1 vec3(84.0, 91.0, 80.0) / 255.0
#define NSCALE 50.0

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

//yes, this is copied from the slag shader
void main() {
    vec2 c = v_texCoords.xy;
    vec2 coords = vec2(c.x * u_resolution.x + u_campos.x, c.y * u_resolution.y + u_campos.y);

    float btime = u_time / 3000.0;
    float noise = (texture2D(u_noise, vec2(coords.x, coords.y / 4.0) / NSCALE + vec2(btime) * vec2(0.6, -0.4)).a + texture2D(u_noise, vec2(coords.x, coords.y / 3.0) / NSCALE + vec2(btime * 1.1) * vec2(0.5, 0.1)).a) / 2.0;
    vec4 color = texture2D(u_texture, c);

    if (noise < 0.5) {
        color.rgb = C2;
    } else if (noise < 0.6) {
        color.rgb = C1;
    }

    gl_FragColor = color;
}