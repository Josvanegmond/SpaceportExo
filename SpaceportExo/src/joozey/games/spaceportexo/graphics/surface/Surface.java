package joozey.games.spaceportexo.graphics.surface;

import java.util.ArrayList;

import joozey.libs.powerup.graphics.DefaultModel;

public class Surface
{
    ArrayList<DefaultModel> terrainModelList;

	private int getMapValue( int[][] map, int x, int z )
	{
		if( x < 0 || x >= map.length || z < 0 || z >= map[0].length )
		{
			return 1;
		}

		else
		{
			return map[x][z];
		}
	}

	// a surface from a map
	public Surface( int[][] map )
	{
		terrainModelList = new ArrayList<DefaultModel>();

		int width = map.length;
		int length = map[0].length;

		DefaultModel model = null;

		for( int x = 0; x < width; x++ )
		{
			for( int z = 0; z < length; z++ )
			{
				int currentValue = getMapValue( map, x, z );
				int values[] = {
						getMapValue( map, x, z - 1 ),
						getMapValue( map, x - 1, z ),
						getMapValue( map, x, z + 1 ),
						getMapValue( map, x + 1, z ),
						
						getMapValue( map, x - 1, z - 1 ),
						getMapValue( map, x - 1, z + 1 ),
						getMapValue( map, x + 1, z + 1 ),
						getMapValue( map, x + 1, z - 1 ) };

				int posX = x - width / 2;
				int posY = currentValue - 1 ;
				int posZ = z - length / 2;

				
				// flat surfaces
				if( currentValue == 0 )
				{
					model = getTerrainPiece( "ter_flat", posX, posY + 1, posZ,
							( int ) ( Math.random() * 4 ) * 360 );
				}
				
				else if( values[0] == 1 && values[1] == 1 && values[2] == 1 &&
						 values[3] == 1 && values[4] == 1 && values[5] == 1 &&
					 	 values[6] == 1 && values[7] == 1 )
				{
					model = getTerrainPiece( "ter_flat", posX, posY + 1, posZ,
							( int ) ( Math.random() * 4 ) * 360 );
				}
				

				// outside corners
				else if( values[0] == 1 && values[1] == 1 && values[2] == 0 && values[3] == 0 ) {
					model = getTerrainPiece( "ter_corner_out", posX, posY, posZ, 90 );
				}
				
				else if (values[0] == 0 && values[1] == 1 && values[2] == 1 && values[3] == 0 ) {
					model = getTerrainPiece( "ter_corner_out", posX, posY, posZ, 180 );
				}
				
				else if (values[0] == 0 && values[1] == 0 && values[2] == 1 && values[3] == 1 ) {
					model = getTerrainPiece( "ter_corner_out", posX, posY, posZ, 270 );
				}

				else if (values[0] == 1 && values[1] == 0 && values[2] == 0 && values[3] == 1 ) {
					model = getTerrainPiece( "ter_corner_out", posX, posY, posZ, 0 );
				}
				
				
				// walls
				else if( values[0] == 0 && values[1] == 1 && values[2] == 1 && values[3] == 1 ) {
					model = getTerrainPiece( "ter_wall", posX, posY, posZ, 180 );
				}

				else if( values[0] == 1 && values[1] == 0 && values[2] == 1 && values[3] == 1 ) {
					model = getTerrainPiece( "ter_wall", posX, posY, posZ, 270 );
				}

				else if( values[0] == 1 && values[1] == 1 && values[2] == 0 && values[3] == 1 ) {
					model = getTerrainPiece( "ter_wall", posX, posY, posZ, 0 );
				}

				else if( values[0] == 1 && values[1] == 1 && values[2] == 1 && values[3] == 0 ) {
					model = getTerrainPiece( "ter_wall", posX, posY, posZ, 90 );
				}
				

				// inside corners
				else if( values[4] == 1 && values[5] == 1 && values[6] == 1 && values[7] == 0 ) {
					model = getTerrainPiece( "ter_corner_in", posX, posY, posZ, 90 );
				}
				
				else if (values[4] == 0 && values[5] == 1 && values[6] == 1 && values[7] == 1 ) {
					model = getTerrainPiece( "ter_corner_in", posX, posY, posZ, 180 );
				}
				
				else if (values[4] == 1 && values[5] == 0 && values[6] == 1 && values[7] == 1 ) {
					model = getTerrainPiece( "ter_corner_in", posX, posY, posZ, 270 );
				}

				else if (values[4] == 1 && values[5] == 1 && values[6] == 0 && values[7] == 1 ) {
					model = getTerrainPiece( "ter_corner_in", posX, posY, posZ, 0 );
				}

				else {
					model = getTerrainPiece( "ter_flat", posX, posY + 1, posZ,
							( int ) ( Math.random() * 4 ) * 360 );
				}
				terrainModelList.add( model );
			}
		}
	}

	// just a standard square surface
	public Surface()
	{
		terrainModelList = new ArrayList<DefaultModel>();

		for( int x = -10; x < 10; x++ )
		{
			for( int z = -10; z < 10; z++ )
			{
				DefaultModel model = null;

				if( x == -10 || x == 9 || z == -10 || z == 9 )
				{
					if( ( x == -10 || x == 9 ) && ( z == -10 || z == 9 ) )
					{
						if( x == -10 && z == -10 ) model = getTerrainPiece(
								"ter_corner_in", x, 0, z, 0 );
						else if( x == -10 && z == 9 ) model = getTerrainPiece(
								"ter_corner_in", x, 0, z, 90 );
						else if( x == 9 && z == 9 ) model = getTerrainPiece(
								"ter_corner_in", x, 0, z, 180 );
						else if( x == 9 && z == -10 )
							model = getTerrainPiece( "ter_corner_in", x, 0, z,
									270 );
					}

					else
					{
						if( x == -10 ) model = getTerrainPiece( "ter_wall", x,
								0, z, 90 );
						else if( z == 9 ) model = getTerrainPiece( "ter_wall",
								x, 0, z, 180 );
						else if( x == 9 ) model = getTerrainPiece( "ter_wall",
								x, 0, z, 270 );
						else if( z == -10 )
							model = getTerrainPiece( "ter_wall", x, 0, z, 0 );
					}
				}

				else
				{
					model = getTerrainPiece( "ter_flat", x, 0, z,
							( int ) ( Math.random() * 4 ) * 90 );
				}

				terrainModelList.add( model );
			}
		}
	}

	public ArrayList<DefaultModel> getModels()
	{
		return this.terrainModelList;
	}

	private DefaultModel getTerrainPiece( String name, int x, int y, int z,
			int rotation )
	{
		DefaultModel model = new DefaultModel( name );
		model.transform.translate( x * 6, y * 6, z * 6 );
		model.transform.rotate( 0, 1, 0, rotation );

		return model;
	}
}
