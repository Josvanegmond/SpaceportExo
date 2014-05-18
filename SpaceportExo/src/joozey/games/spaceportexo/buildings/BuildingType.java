package joozey.games.spaceportexo.buildings;

public enum BuildingType
{
	APRON( "apron" ),
	HANGAR( "hangar" ),
	TERMINAL( "terminal" ),
	CONTROLTOWER( "controltower" ),
    RUNWAY( "runway" );
	
	private String name;
	
	private BuildingType( String name )
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
}
