package cs.cooble.nice.entity.inventory;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.*;
import cs.cooble.nice.input.ActionRectangles;
import cs.cooble.nice.input.IActionRectangle;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.inventory.items.Item;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;


public class MatejContainerEntity extends ContainerEntity {

    private int sirkaziv, vyskaziv;
    private int mezirka;
    private Rectangle statsrectangle;
    private IIcon hotbar;
    private IIcon erb,stomachEmpty,stomachFull;


    public MatejContainerEntity() {
        super(null, "matej_container_entity", 9);
        sirkaziv = 200;
        vyskaziv = 50;
        mezirka = 10;
        statsrectangle = new Rectangle(NC.WIDTH - sirkaziv - mezirka, mezirka, sirkaziv, vyskaziv);
        hasPreHeldItem = false;
        defaultItemStackSave=true;

        int mezera = 20;
        int velikost_kontejneru = 70;
        posX = NC.WIDTH / 2 - 415;
        posY = NC.HEIGHT - (mezera * 4 + velikost_kontejneru);

        for (int i = 0; i < containers.length; i++) {
            containers[i] = new Container(posX + i * (velikost_kontejneru + mezera) + mezera, posY + mezera, velikost_kontejneru, getID(), itemStacks, i);
        }
        rectangle = new Rectangle(posX, posY, velikost_kontejneru * (velikost_kontejneru + mezera) + mezera, velikost_kontejneru);
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        hotbar = loader.getIcon(ImageManager.GUI +"hotbar");
        erb = loader.getIcon(ImageManager.GUI + "erb");
        stomachEmpty = loader.getIcon(ImageManager.GUI + "zaludek_empty");
        stomachFull = loader.getIcon(ImageManager.GUI + "zaludek_full");
    }

    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        gg.drawIcon(hotbar,posX,posY);

        int posx = NC.WIDTH - 116;
        int posy = 4;

        ///ZALUDEK///////////////////////////////////////////////////////////////////////////////////////////////////////////
        IIcon pod = stomachEmpty;
        IIcon nad = stomachFull;

        int width = pod.getWidth();
        int height = pod.getHeight();

        int final_height = (int) (height * matej.getStats().getHungerD() * 1.0);

        gg.drawIcon(erb, posx, posy);
        gg.drawIcon(pod, posx + 15, posy + 9);
        if (final_height > 0) {
            gg.drawIcon(nad, posx + 15, height - final_height + posy + 9);

        }
/*
        posx = posx;
        posy = 104;
        ///SKLENICKA//////////////////////////////////////////////////////////////////////////////////////////////////////////
        pod = ImageManager.getIIcon(ImageManager.GUI + "sklenicka_empty");
        nad = ImageManager.getIIcon(ImageManager.GUI + "sklenicka_full");

        width = pod.getWidth(null);
        height = nad.getHeight(null);

        final_height = (int) (height * matej.getStats().getWaterD() * 1.0);

        g.drawImage(erb, posx, posy, null);
        g.drawImage(pod, posx + 8, posy + 14, null);
        if (final_height > 0) {
            nad = NCPainter.orezejObrazek(nad, 0, height - final_height, width, final_height);
            g.drawImage(nad, posx + 8, height - final_height + posy + 14, null);
        }

        posx = posx;
        posy = 204;
        ///ZIVOTY/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        pod = ImageManager.getIIcon(ImageManager.GUI + "srdce_empty");
        nad = ImageManager.getIIcon(ImageManager.GUI + "srdce_full");

        width = pod.getWidth(null);
        height = nad.getHeight(null);

        final_height = (int) (height * matej.getStats().getHealthD() * 1.0);

        g.drawImage(erb, posx, posy, null);
        g.drawImage(pod, posx + 11, posy + 18, null);
        if (final_height > 0) {
            nad = NCPainter.orezejObrazek(nad, 0, height - final_height, width, final_height);
            g.drawImage(nad, posx + 11, height - final_height + posy + 18, null);
        }*/
        g.setColor(Color.green);
        gg.setFont(NC.fontName,Font.PLAIN,15);
        //g.drawString("Time "+ (int)World.getInstance().getTimeManager().getHours()  +"hod", Platno.WIDTH-g.getFontMetrics().stringWidth("Time "+ (int)World.getInstance().getTimeManager().getHours()+"hod")-10,posy+120);
        //g.drawString("Time "+ World.getInstance().getTimeManager().getMinut()       +"min", Platno.WIDTH-g.getFontMetrics().stringWidth("Time "+ World.getInstance().getTimeManager().getMinut()+"min")-10,posy+120+g.getFontMetrics().getHeight());

    }

    @Override
    public void onGuiEntityStartDrawing(Renderer renderer) {
        ActionRectangles.getInstance().registerActionRectangle(new IActionRectangle() {
            @Override
            public Rectangle getRectangle() {
                return statsrectangle;
            }

            @Override
            public void click(Location location, boolean prave_tlacitko) {
            }
        });

    }

    @Override
    public void onGuiEntityStopDrawing(Renderer renderer) {
        ActionRectangles.getInstance().removeActionRectangle(statsrectangle);
    }

    boolean hasPreHeldItem;
    ItemStack preHeldInHand;

    @Override
    public void onUpdate(World world) {
        //volání onItemHeldInHand
        if (this.getItemStackInHand() != null) {
            if (getItemStackInHand().equals(preHeldInHand)) {
                this.getItemStackInHand().onItemHeldInHand(world, hasPreHeldItem ? Item.HoldStatHELD : Item.HoldStatEQUIPPED);
                preHeldInHand = getItemStackInHand();
            } else {
                if (hasPreHeldItem)
                    preHeldInHand.onItemHeldInHand(world, Item.HoldStatUNEQUIPPED);
                getItemStackInHand().onItemHeldInHand(world, Item.HoldStatEQUIPPED);
                preHeldInHand = getItemStackInHand();
            }
            hasPreHeldItem = true;
        } else if (preHeldInHand != null) {
            preHeldInHand.onItemHeldInHand(world, Item.HoldStatUNEQUIPPED);
            preHeldInHand = null;
            hasPreHeldItem = false;
        }
    }
}
