attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;
 
uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;
uniform vec3 u_dirSunlight;
uniform vec3 u_dirMoonlight;

varying vec2 v_texCoord0;
varying vec3 v_dirNormal;

varying float v_sunlightFactor;
varying float v_moonlightFactor;



void main() {
    v_texCoord0 = a_texCoord0;
    v_dirNormal = a_normal;
    v_sunlightFactor = clamp(dot(vec3(v_dirNormal.x,float(-1.0),v_dirNormal.z), -u_dirSunlight), 0.1, 1.0);
    v_moonlightFactor = clamp(dot(vec3(v_dirNormal.x,float(-1.0),v_dirNormal.z), -u_dirMoonlight), 0.1, 1.0);

    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);
}
