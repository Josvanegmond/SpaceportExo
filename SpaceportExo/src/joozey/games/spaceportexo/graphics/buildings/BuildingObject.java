package joozey.games.spaceportexo.graphics.buildings;

import joozey.games.spaceportexo.buildings.BuildingData;
import joozey.libs.powerup.graphics.DefaultModel;
import joozey.libs.powerup.object.GameObject3D;

public class BuildingObject extends GameObject3D
{
	protected BuildingData buildingData;
	
	public BuildingObject( DefaultModel model, BuildingData buildingData )
	{
		super( model, buildingData );
		this.buildingData = buildingData;
	}

}
