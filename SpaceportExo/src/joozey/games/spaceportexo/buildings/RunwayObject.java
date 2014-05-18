package joozey.games.spaceportexo.buildings;

import com.badlogic.gdx.math.Vector3;

import joozey.games.spaceportexo.graphics.buildings.BuildingObject;
import joozey.libs.powerup.game.GameData;
import joozey.libs.powerup.graphics.DefaultModel;
import joozey.libs.powerup.object.GameObject3D;

/**
 * Created by joozey on 3/25/14.
 */
public class RunwayObject extends BuildingObject
{
    private float waitTime = 0;
    private GameObject3D ship;

    private static DefaultModel createModel()
    {
        DefaultModel model = new DefaultModel( "runway" );
        model.setLightTexture( "runway_light" );
        return model;
    }

    private static BuildingData createData()
    {
        return new BuildingData( BuildingType.RUNWAY );
    }

    public RunwayObject()
    {
        super( createModel(), createData() );
    }



    private boolean clear = true;
    private int state;
    private final int ARRIVE = 0, LOAD = 1, ROTATE = 2, LEAVE = 3, LEFT = 4;

    public void operate()
    {
        float speed = GameData.getSpeed();

        if( clear == false )
        {
            if( waitTime <= 0 )
            {
                if( state == ARRIVE )
                {
                    Vector3 runwayPos = new Vector3( this.buildingData.getPosition() ).add( -12, 0, 0 );
                    Vector3 position = new Vector3( this.ship.getData().getPosition() );
                    position.sub( new Vector3( position ).sub( runwayPos ).scl( 0.2f * speed ) );
                    this.ship.getData().setPosition(position);
                    if( position.dst( runwayPos ) < 1 )
                    {
                        waitTime = 300 + (int)(Math.random()*300);
                    }
                }

                if( state == LOAD )
                {
                    waitTime = 100;
                }

                if( state == ROTATE )
                {
                    this.ship.getData().setRotation(new Vector3(0, 1, 0), this.ship.getData().getRotation() + 3f * speed);
                    if( this.ship.getData().getRotation() > 350 )
                    {
                        this.ship.getData().setRotation( new Vector3( 0, 1, 0 ), 0 );
                        waitTime = 300;
                    }
                }

                if( state == LEAVE )
                {
                    Vector3 runwayPos = new Vector3( this.buildingData.getPosition() ).add( -12, 0, 0 );
                    Vector3 position = new Vector3( this.ship.getData().getPosition() );
                    position.add( new Vector3( position ).sub( runwayPos ).scl( 0.2f * speed ) );
                    this.ship.getData().setPosition(position);
                    if( position.dst( runwayPos ) > 150 )
                    {
                        waitTime = 300 + (int)(Math.random()*500);
                    }
                }

                if( state == LEFT )
                {
                    clear = true;
                    state = 0;
                }

                this.ship.update();
            }

            if( waitTime > 0 )
            {
                waitTime -= 10f * speed;
                System.out.println("waittime " + waitTime);

                if( waitTime <= 0 ) {
                    state++;
                    state %= 5;
                    System.out.println("state " + state);
                }
            }
        }
    }

    public GameObject3D getShip()
    {
        return ship;
    }

    public boolean isClear()
    {
        return clear;
    }

    public void addShip( GameObject3D ship )
    {
        this.ship = ship;
        this.ship.getData().setPosition( new Vector3( this.getData().getPosition() ).add( 60, 20, 0 ) );
        this.clear = false;
        this.state = ARRIVE;
    }
}
