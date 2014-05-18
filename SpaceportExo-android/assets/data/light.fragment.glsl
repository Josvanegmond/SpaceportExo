#ifdef GL_ES
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

varying vec2 v_texCoord0;
varying vec3 v_dirNormal;
varying float v_sunlightFactor;
varying float v_moonlightFactor;

uniform vec3 u_dirCam;
uniform vec4 u_diffuseSunlight;
uniform vec4 u_diffuseMoonlight;

uniform sampler2D u_texture;
uniform sampler2D u_lightmap;

void main() {
    vec4 texColor = texture2D( u_texture, v_texCoord0 );
    vec4 lightColor = texture2D( u_lightmap, v_texCoord0 );

    //vec3 reflect = 2.0f * (sunLamb + moonLamb) * v_dirNormal + u_dirSunlight + u_dirMoonlight;
    //float Is = pow( max( dot( reflect, u_dirCam ), 0.0f ), 0.5f ) * 0.1f;

    vec4 dayColor = (texColor + texColor * u_diffuseSunlight * float(0.4)) * v_moonlightFactor;
    vec4 nightColor = lightColor * (lightColor + texColor * u_diffuseMoonlight * float(0.4)) * v_sunlightFactor;

    vec4 totalColor = dayColor + nightColor;
    gl_FragColor = totalColor;
}
