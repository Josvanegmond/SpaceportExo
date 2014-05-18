package joozey.games.spaceportexo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import joozey.games.spaceportexo.buildings.BuildingData;
import joozey.games.spaceportexo.buildings.BuildingType;
import joozey.games.spaceportexo.buildings.DepotObject;
import joozey.games.spaceportexo.buildings.RunwayObject;
import joozey.games.spaceportexo.graphics.buildings.BuildingObject;
import joozey.games.spaceportexo.graphics.surface.Surface;
import joozey.libs.powerup.control.GameThread;
import joozey.libs.powerup.graphics.DefaultModel;
import joozey.libs.powerup.graphics.shaders.LightShader;
import joozey.libs.powerup.object.GameObject3D;
import joozey.libs.powerup.object.GameObject3DData;

public class ExoActivity implements ApplicationListener {

    private PerspectiveCamera cam;
    private CameraInputController camController;
    private ModelBatch modelBatch;
    private ArrayList<DefaultModel> instances = new ArrayList<DefaultModel>();
    private RenderContext renderContext;
    private ExoData exoData;
    private EnviroControl enviroControl;
    Environment environment;
    private boolean loading = false;

    @Override
    public void create()
    {
        Texture.setEnforcePotImages(false);

        this.modelBatch = new ModelBatch();

        cam = new PerspectiveCamera( 67, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() );
        cam.position.set( 10f, 20f, 20f );
        cam.lookAt( 0, 0, 0 );
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController( cam );
        Gdx.input.setInputProcessor( camController );

        this.cam = new PerspectiveCamera( 75, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() );
        this.cam.position.set(70f, 20f, 20f);
        this.cam.lookAt( 0, 0, 0 );
        this.cam.near = 0.1f;
        this.cam.far = 300f;
        this.cam.update();

        this.camController = new CameraInputController( this.cam );
        Gdx.input.setInputProcessor( this.camController );

        this.exoData = new ExoData();
        this.exoData.setCamera( this.cam );
        GameThread gameThread = new GameThread( exoData );

        this.enviroControl = new EnviroControl();
        gameThread.register( this.enviroControl );

        environment = this.enviroControl.getEnvironment();

        DefaultModel.prepare();
        loading = true;

        gameThread.register(new ShipControl(this.exoData));
    }


    public GameObject3D createGlider()
    {
        GameObject3DData gliderData = new GameObject3DData( new Vector3( 150, 20, 8 ) );
        gliderData.setRotation( new Vector3( 0, 1, 0 ), 180 );
        DefaultModel gliderModel = new DefaultModel( "glider" );
        gliderModel.setLightTexture("glider_light");
        GameObject3D gliderObject = new GameObject3D( gliderModel, gliderData );
        new LightShader( this.enviroControl.getEnvironment(), gliderModel );

        return gliderObject;
    }

    @Override
    public void dispose()
    {
        modelBatch.dispose();
        DefaultModel.dispose();
    }


    private void createModels()
    {
        if( loading == true && DefaultModel.isPrepared() )
        {
            //control tower
            BuildingData towerData = new BuildingData( BuildingType.CONTROLTOWER );
            DefaultModel towerModel = new DefaultModel("controltower");
            towerModel.setLightTexture("controltower_light");
            BuildingObject towerObject = new BuildingObject( towerModel, towerData );
            new LightShader( this.enviroControl.getEnvironment(), towerModel );
            this.instances.add( towerModel );



            //fuel depots
            for( int i = 0; i < 2; i++ )
            {
                DepotObject fuelObject = new DepotObject();
                fuelObject.getData().setPosition(new Vector3(-8 + i*8, 0, 14));
                this.instances.add( fuelObject.getModel() );
            }

            for( int i = 0; i < 2; i++ )
            {
                //runway
                RunwayObject runwayObject = new RunwayObject();
                runwayObject.getData().setPosition(new Vector3(8, 0, -8 + i*16));
                new LightShader( environment, runwayObject.getModel() );
                this.instances.add(runwayObject.getModel());
                this.exoData.addRunway( runwayObject );


                //hangar
                BuildingData hangarData = new BuildingData( BuildingType.HANGAR );
                hangarData.setPosition( new Vector3( -8, 1, -8 + i*16 ) );
                hangarData.setRotation(new Vector3(0, 1, 0), 180);
                DefaultModel hangarModel = new DefaultModel( "hangar" );
                hangarModel.setLightTexture("hangar_light");
                hangarModel.transform.rotate( 0, 1, 0, 180 );
                BuildingObject hangarObject = new BuildingObject( hangarModel, hangarData );
                new LightShader( environment, hangarModel );
                this.instances.add(hangarModel);
            }

            this.renderContext = new RenderContext( new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1) );

            int[][] map = {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                    {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                    {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                    {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
            };

            this.instances.addAll(new Surface(map).getModels());

            loading = false;
        }
    }

    @Override
    public void render()
    {
        createModels();

        ArrayList<RunwayObject> runwayList = this.exoData.cloneRequestingRunways();

        for( RunwayObject runway : runwayList )
        {
            if( runway != null )
            {
                GameObject3D gliderObject = runway.getShip();
                if( gliderObject != null )
                {
                    this.instances.remove( gliderObject.getModel() );
                }

                gliderObject = createGlider();
                runway.addShip( gliderObject );
                this.instances.add( gliderObject.getModel() );

                this.exoData.removeRequestingRunway( runway );
            }
        }

        this.cam.rotateAround( new Vector3(0,0,0), new Vector3(0,1,0), 0.5f);
        this.cam.update();

        Gdx.gl.glViewport( 0, 0, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        modelBatch.begin( cam );

        for( DefaultModel instance : this.instances )
        {
            Shader shader = instance.getShader();
            if( shader != null )
            {
                shader.begin( cam, renderContext );
                modelBatch.render( instance, shader );
                shader.end();
            }

            else
            {
                this.modelBatch.render( instance, environment );
            }
        }

        this.modelBatch.end();
    }

    @Override
    public void resize( int width, int height )
    {
        this.cam.viewportWidth = width;
        this.cam.viewportHeight = height;
        this.cam.update();
    }

    @Override
    public void pause()
    {}

    @Override
    public void resume()
    {}

    public static void sleep( int millis )
    {
        try
        {
            Thread.sleep( millis );
        }

        catch( InterruptedException e )
        {
            e.printStackTrace();
        }
    }
}
