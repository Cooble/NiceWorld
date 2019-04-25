package cs.cooble.nice.blocks;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.inventory.items.Items;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


/**
 * Created by Matej on 31.1.2015.
 */
public class BlockTree extends Block {

    //private Polygon dynamickeTextury;
   // int[] polex;
   // int[] poley;
    //int npoints;
    private int buchcount;
    private double ohnout_angle;



    public BlockTree() {
        super("tree");
        this.setMaterial(Block.WOOD);
        this.hardness=100;
        toolType= ToolType.AXE;
        buchcount=-1;
        textureName = ImageManager.BLOCK+"strom";
        maxMeta=3;
        canput=false;
        this.sirka=366/2;
        this.vyska=572/2;

//         polex = new int[]{tileBlock.getX(),tileBlock.getX()+tileBlock.sirka,tileBlock.getX()+tileBlock.sirka,tileBlock.getX()};
 //        poley = new int[]{tileBlock.getY(),tileBlock.getY(),tileBlock.getY()+tileBlock.vyska,tileBlock.getY()+tileBlock.vyska};
        // npoints=4;

    //    dynamickeTextury=new Polygon(polex,poley,npoints);

    }

    int lastAngle;
    @Override
    public void render(int x, int y, NGraphics g) {
        g.g().setColor(new Color(2));

        //todo otaceni stromu se bohuzel ignoruje zmenu velikosti obrázku(je to orezane dle puvodniho obrázku), upravit NCPaniter.otocHo()
        if(buchcount>-1) {
            Image image = NiceWorld.getNiceWorld().getRenderer().getAtlas().getTexture(getIIcon());
            int posx = getX()-x;
            int posy = getY()-y;
            g.g().rotate(image.getWidth()/2,image.getHeight()/2-25, (float) ohnout_angle);
            g.g().drawImage(image, posx-sirka/2,posy-vyska,posx+ sirka/2,posy,0,0,image.getWidth(),image.getHeight());
            g.g().resetTransform();
        }
        else
            g.drawIcon(image.getIcon(getMetadata()),getX()-sirka/2-x,getY()-y-vyska,sirka,vyska);


    }

    @Override
    public ItemStack getItemFromBlock() {
        return new ItemStack(Items.poleno);
    }

    /*
    @Override
    public void buch(IEntity entity, boolean b, int maxBuch,Block tileBlock) {
        if(b) {
            buchcount++;
            if (buchcount >= maxBuch - 1 && buchcount != maxBuch + 1) {
                ohnout_angle += Math.PI / 4;
                pada=true;
            }
            else {
                ohnout_angle=0.35;
            }
        }
        else if(!pada){
            if(ohnout_angle>0)
                ohnout_angle-=0.05;
        }
    }
*/
}
