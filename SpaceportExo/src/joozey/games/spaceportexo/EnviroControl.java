package joozey.games.spaceportexo;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import joozey.libs.powerup.control.GameRunnable;
import joozey.libs.powerup.control.GameThread;
import joozey.libs.powerup.game.GameData;

/**
 * Created by joozey on 4/1/14.
 */
public class EnviroControl implements GameRunnable
{
    private float planetRotation = 0f;
    private DirectionalLight sunlight, moonlight;
    private Environment environment;
    private boolean initialised = false;

    public EnviroControl()
    {
        this.environment = new Environment();
        this.environment.set( new ColorAttribute( ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.8f, 1f ) );
        this.sunlight = new DirectionalLight();
        this.moonlight = new DirectionalLight();
        this.environment.add( this.sunlight );
        this.environment.add( this.moonlight );

        this.initialised = true;
    }

    @Override
    public void run(GameThread gameThread) {

        this.planetRotation += 0.02f * GameData.getSpeed();

        float factor1 = ((float)Math.sin(this.planetRotation)+1f) / 2f;
        float factor2 = ((float)Math.sin(this.planetRotation+Math.PI)+1f) / 2f;

        this.sunlight.set( 1f*factor1, 0.7f*factor1, 0.5f*factor1, (float)Math.sin(this.planetRotation), -0.8f*(factor1*1.1f-0.2f), (float)Math.cos(this.planetRotation) );
        this.moonlight.set( 0.4f*factor2, 0.7f*factor2, 1.0f*factor2, (float)Math.sin(this.planetRotation+Math.PI), -0.8f*(factor2*1.1f-0.2f), (float)Math.cos(this.planetRotation+Math.PI) );
    }

    public float getPlanetRotation()
    {
        return this.planetRotation;
    }

    public DirectionalLight getSunlight()
    {
        return this.sunlight;
    }

    public DirectionalLight getMoonlight()
    {
        return this.moonlight;
    }

    public Environment getEnvironment()
    {
        return this.environment;
    }


    @Override
    public boolean isInitialised() {
        return this.initialised;
    }
}
