package joozey.games.spaceportexo.buildings;

import com.badlogic.gdx.math.Vector3;

import joozey.libs.powerup.object.GameObject3DData;

public class BuildingData extends GameObject3DData
{
	private BuildingType buildingType;
	
	public BuildingData( BuildingType buildingType )
	{
		this.buildingType = buildingType;
	}

    public BuildingData( BuildingType buildingType, Vector3 position )
    {
        super( position );
        this.buildingType = buildingType;
    }
	
	public BuildingType getType()
	{
		return this.buildingType;
	}
}
