
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;

public class MyTent extends JFrame{

  private Canvas3D myCanvas3D;
  private final float a = 1.0f;
  private final float b = 0.4f; 
  private final float c = 0.2f;

  private final Point3f[] coordinates = {
      new Point3f( -a / 2, 0, -c / 2),
      new Point3f( -a / 2, 0, c / 2),
      new Point3f( -a / 2, b, -c / 2),
      new Point3f(a / 2, 0, c / 2),
      new Point3f(a / 2, b, -c / 2),
      new Point3f(a / 2, 0, -c / 2)
  };

  private final int indices[] = {
      0, 1, 2,
      2, 1, 3,
      3, 4, 2,
      2, 4, 0,
      0, 4, 5,
      5, 4, 3,
      3, 1, 5,
      5, 1, 0
  };



  public MyTent(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

    SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
    simpUniv.getViewingPlatform().setNominalViewingTransform();
    createSceneGraph(simpUniv);
    addLight(simpUniv);

    OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

    setTitle("GDV Aufgabe 5");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);
  }

  public void createSceneGraph(SimpleUniverse su)  {
    //Створюємо білий фон
    Background bg = new Background(new Color3f(1.0f,1.0f,1.0f));
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),1000.0);
    bg.setApplicationBounds(bounds);

    BranchGroup theScene = new BranchGroup();
    this.init(theScene);
    theScene.addChild(bg);
    theScene.compile();
    su.addBranchGraph(theScene);
  }

  public void init(BranchGroup scene){
    Color3f ambientColour = new Color3f(0.2f, 0.2f, 0.2f);
    Color3f emissiveColour = new Color3f(0.1f, 0.1f, 0.1f);
    Color3f diffuseColour = new Color3f(0.3f, 0.3f, 0.3f);
    Color3f specularColour = new Color3f(0.4f, 0.4f, 0.4f);
    float shininess = 100.0f;
    Appearance app = new Appearance();
    app.setMaterial(new Material(ambientColour, emissiveColour, diffuseColour,
                                 specularColour, shininess));

    GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);
    gi.setCoordinates(coordinates);
    gi.setCoordinateIndices(indices);
    NormalGenerator ng = new NormalGenerator();
    ng.generateNormals(gi);
    scene.addChild(new Shape3D(gi.getGeometryArray(),app));
  }

  public void addLight(SimpleUniverse su){
    BranchGroup bgLight = new BranchGroup();
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);
    bgLight.addChild(light1);

    Vector3f lightDir2  = new Vector3f(1.0f,0.0f,0.5f);
    DirectionalLight light2 = new DirectionalLight(lightColour1, lightDir2);
    light2.setInfluencingBounds(bounds);

    bgLight.addChild(light2);
    su.addBranchGraph(bgLight);

  }


  public static void main(String[] args)  {
    new MyTent();
  }

}
