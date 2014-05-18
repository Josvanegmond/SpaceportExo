package joozey.games.spaceportexo;

import java.util.ArrayList;

import joozey.games.spaceportexo.buildings.RunwayObject;
import joozey.libs.powerup.control.GameRunnable;
import joozey.libs.powerup.control.GameThread;

/**
 * Created by joozey on 4/1/14.
 */
public class ShipControl implements GameRunnable
{
    private ExoData exoData;
    private ArrayList<RunwayObject> runways;

    public ShipControl( ExoData exoData )
    {
        this.exoData = exoData;
        this.runways = exoData.getRunways();
    }


    @Override
    public void run(GameThread gameThread) {

        for( int i = 0; i < runways.size(); i++ )
        {
            RunwayObject runwayObject = runways.get(i);

            if( runwayObject.isClear() )
            {
                exoData.requestShip(runwayObject);
            }

            runwayObject.operate();
        }
    }

    @Override
    public boolean isInitialised() {
        return true;
    }
}
