package joozey.games.spaceportexo;

import java.util.ArrayList;

import joozey.games.spaceportexo.buildings.RunwayObject;
import joozey.libs.powerup.game.GameData;

/**
 * Created by joozey on 4/1/14.
 */
public class ExoData extends GameData
{
    private final ArrayList<RunwayObject> requestingRunways;
    private ArrayList<RunwayObject> runways;

    public ExoData()
    {
        super( 0, 0 );
        this.runways = new ArrayList<RunwayObject>();
        this.requestingRunways = new ArrayList<RunwayObject>();
    }

    public void requestShip( RunwayObject runway )
    {
        requestingRunways.remove( runway );
        if( requestingRunways.contains( runway ) == false )
        {
            requestingRunways.add( runway );
        }
    }

    public ArrayList<RunwayObject> cloneRequestingRunways()
    {
        return (ArrayList<RunwayObject>) requestingRunways.clone();
    }

    public void removeRequestingRunway( RunwayObject runway )
    {
        this.requestingRunways.remove( runway );
    }

    public void addRunway( RunwayObject runway )
    {
        runways.add( runway );
    }

    public ArrayList<RunwayObject> getRunways()
    {
        return runways;
    }
}
