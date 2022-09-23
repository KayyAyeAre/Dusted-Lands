#define HIGHP

#define step 2.0

uniform sampler2D u_texture;
uniform vec2 u_texsize;
uniform vec2 u_invsize;
uniform float u_time;
uniform vec2 u_offset;

varying vec2 v_texCoords;

void main() {
    vec2 coords = (v_texCoords.xy * u_texsize) + u_offset;

    vec4 color = texture2D(u_texture, v_texCoords.xy);
    vec2 v = u_invsize;

    vec4 maxed = max(max(max(texture2D(u_texture, v_texCoords.xy + vec2(0.0, step) * v), texture2D(u_texture, v_texCoords.xy + vec2(0.0, -step) * v)), texture2D(u_texture, v_texCoords.xy + vec2(step, 0.0) * v)), texture2D(u_texture, v_texCoords.xy + vec2(-step, 0.0) * v));

    if (texture2D(u_texture, v_texCoords.xy).a < 0.9 && maxed.a > 0.9) {
        gl_FragColor = vec4(maxed.rgb, maxed.a * 100.0);
    } else {
        if (color.a > 0.0) {
            if (mod(coords.x + (u_time / 4.0), 5.0) < 1.0 || mod(coords.y + (u_time / 4.0), 5.0) < 1.0) {
                color.a = 0.2 + (sin(coords.x / 5.0 + coords.y / 3.0 + u_time / 6.0) + 1.0) * 0.1;
            } else {
                color.a = 0.15;
            }
        }

        gl_FragColor = color;
    }
}