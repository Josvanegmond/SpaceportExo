package joozey.games.spaceportexo.buildings;

import joozey.games.spaceportexo.graphics.buildings.BuildingObject;
import joozey.libs.powerup.graphics.DefaultModel;

/**
 * Created by joozey on 3/25/14.
 */
public class DepotObject extends BuildingObject
{
    public DepotObject()
    {
        super( new DefaultModel( "cylinder" ), new BuildingData( BuildingType.CONTROLTOWER ) );
    }

}
