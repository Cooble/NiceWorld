package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.entity.IControllable;
import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.music.MPlayer;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.awt.Font;
import java.util.ArrayList;

/**
 * Created by Matej on 21.2.2015.
 */
public final class BoxCudlik extends Cudlik {
    private ArrayList<IInfoWrapper> list = new ArrayList<>();
    private ArrayList<IInfoWrapper> showList = new ArrayList<>();
    private final Jezdec jezdec;
    private static final int slider_size=15;
    private int stav;

    private IInfoWrapper selected;

    public BoxCudlik(int posX, int posY, int sirka, int vyska, String name, ICudlik cudlik) {
        super(posX, posY, sirka, vyska, name, "", cudlik);
        this.jezdec = new Jezdec(posX+sirka-slider_size,posY,slider_size,vyska,this);
        setStav(0);
        refreshShowList();
    }
    public void addWordInfo(Infos info){
        ArrayList<IInfoWrapper> ostatni = (ArrayList<IInfoWrapper>)list.clone();
        list.clear();
        list.add(new IInfoWrapper(info,new Rectangle(0,0,0,0)));
        list.addAll(1,ostatni);
        refreshShowList();
        if(list.size()-10<1)
            jezdec.setKousky(1);
        else
            jezdec.setKousky(list.size()-10);
    }
    public void setWorldInfo(ArrayList<Infos> list){
        this.list.clear();
        for (int i = 0; i < list.size(); i++) {
            this.list.add(new IInfoWrapper(list.get(i),new Rectangle(0,0,0,0)));
        }
        refreshShowList();
        if(list.size()-10<1) {
            jezdec.setKousky(1);
        }
        else {
            jezdec.setKousky(list.size() - 9);

        }
    }
    public void addWordInfo(String[] info){
       addWordInfo(new Infos(info));
    }
    public void removeWordInfo(String name){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).info.getInfos()[0].equals(name)){
                list.remove(i);
                refreshRectangles();
                return;
            }
        }

    }
    void setStav(int stav){
        this.stav=stav;
        refreshShowList();
    }
    void refreshShowList(){
        showList.clear();
        int o =0;
        for (int i = stav; i < list.size(); i++) {
            if(o<10) {
                showList.add(list.get(i));
                o++;
            }
            else break;
        }
        refreshRectangles();
    }

    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        g.setColor(Color.black);
        int okraje=5;
        g.fillRoundRect(posX-okraje,posY-okraje,sirka+okraje*2,vyska+okraje*2,20,20);
        g.setColor(Color.darkGray);
        g.fillRoundRect(posX+okraje,posY+okraje,sirka-slider_size-okraje*2,vyska-okraje*2,15,15);
        for (int i = 0; i < showList.size(); i++) {
            showList.get(i).render(x, y, gg);
        }
        jezdec.render(x, y, gg);
    }

    @Override
    public void pressed(Location location) {
        if(jezdec.getRectangle().contains(location.X,location.Y)) {
            jezdec.pressed(location);
        }
        else
            for (int i = 0; i < showList.size(); i++) {
                if(showList.get(i).rectangle.contains(location.X,location.Y)) {
                    selected = showList.get(i);
                    if(!selected.isSelected) {
                        IInfoWrapper selectedinfo=selected;
                        deselectAll();
                        selected=selectedinfo;
                        selected.isSelected = true;
                    }
                    else
                        cudlik.clickedOnComponent(selected.info);

                    MPlayer.playSound(MPlayer.CUDLIK+"click");
                    return;
                }
            }
    }

    @Override
    public void moved(Location location) {
        jezdec.moved(location);
    }

    @Override
    public void released(Location location) {
        jezdec.released(location);
    }

    @Override
    public void wheeled(Location location, int pocet) {
        jezdec.wheeled(location, pocet);
    }

    private void refreshRectangles(){
        for (int i = 0; i < showList.size(); i++) {
            IInfoWrapper wrapper = showList.get(i);
            wrapper.rectangle.x=posX;
            wrapper.rectangle.y= posY + i * vyska / 10;
            wrapper.rectangle.width=sirka-slider_size-5;
            wrapper.rectangle.height=vyska/10;
        }
    }
    private void deselectAll(){
        selected=null;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).isSelected=false;
        }
    }

    public Infos getSelected() {
        if(selected!=null)
            return selected.info;
        else return null;
    }

    //Inertni classy
    final class IInfoWrapper implements IRenderable {
        public final Infos info;
        public final Rectangle rectangle;
        public boolean isSelected=false;

        IInfoWrapper(Infos info, Rectangle rectangle) {
            this.info = info;
            this.rectangle = rectangle;
        }

        public void render(int x, int y, NGraphics gg) {
            Graphics g = gg.g();
            g.setColor(Color.black);
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            g.setColor(isSelected?Color.white:Color.black);
            if(isSelected)
                g.fillRoundRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height,10,10);
            g.setColor(new Color(0x04DE09));
            int okraje=5;
            Rectangle actualrec = new Rectangle((int)rectangle.getX()+okraje,(int)rectangle.getY()+okraje,(int)rectangle.getWidth()-okraje*2,(int)rectangle.getHeight()-okraje*2);
            g.fillRoundRect(actualrec.x,actualrec.y,actualrec.width,actualrec.height,10,10);


            g.setColor(Color.black);

            gg.setFont(NC.fontGUIName, Font.BOLD,25);
            g.drawString(info.getInfos()[0], actualrec.x+5, actualrec.y/*+g.getFont().getLineHeight()*/);

            gg.setFont(NC.fontGUIName, Font.BOLD,15);
            g.drawString(info.getInfos()[1],actualrec.x+5,actualrec.y+actualrec.height-g.getFont().getLineHeight());

          //  gg.setFont(NC.fontGUIName, Font.BOLD,15);
            g.drawString(info.getInfos()[2], actualrec.x + actualrec.width - g.getFont().getWidth(info.getInfos()[2]) - 5, actualrec.y /*+ g.getFont().getLineHeight()*/);

            g.drawString(info.getInfos()[3],actualrec.x+actualrec.width-g.getFont().getWidth(info.getInfos()[3])-5,actualrec.y+actualrec.height-5);

        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }
    }
    final class Jezdec implements IRenderable,ICudlik,IControllable{
        private int posX,posY;
        private final int sirka,vyska;
        private int delkaJezdce=40;
        private final int MAX, MIN;
        private int rozdil;
        private Rectangle sloupec;
        private boolean pressed=false;
        private int stav,oldStav;
        BoxCudlik majitel;

        public Jezdec(int posX, int posY, int sirka, int vyska,BoxCudlik majitel) {
            this.posX = posX;
            this.posY = posY;
            this.sirka = sirka;
            this.vyska = vyska;
            this.MAX = posY+vyska;
            this.MIN = posY;
            this.majitel=majitel;
            sloupec = new Rectangle(posX,posY,sirka,vyska);
        }

        @Override
        public void pressed(Location location) {
            rozdil = location.Y - posY;
            pressed = true;
        }

        @Override
        public void moved(Location location) {
            if(pressed) {
                int asiY = location.Y-rozdil;
                if(asiY>=sloupec.y&&asiY+delkaJezdce<=sloupec.y+sloupec.height)
                    posY = asiY;
                else if(asiY<sloupec.y)
                    posY=sloupec.y;
                else if(asiY+delkaJezdce>sloupec.y+sloupec.height)
                    posY=sloupec.y+sloupec.height-delkaJezdce;
                stav=(int)Math.floor((double)((posY-sloupec.y)/delkaJezdce));
                refreshStav();
            }
        }

        @Override
        public void released(Location location) {
            pressed=false;
        }

        @Override
        public void wheeled(Location location,int pocet) {
            if(majitel.getRectangle().contains(location.X,location.Y)&&!pressed) {
                int kousky=sloupec.height/delkaJezdce;
                if(stav+pocet<kousky&&stav+pocet>=0) {
                    stav += pocet;
                    pressed = true;
                    posY=sloupec.y + stav * delkaJezdce;
                    rozdil = 0;
                    moved(new Location(posX, sloupec.y + stav * delkaJezdce));
                    pressed = false;
                }
            }
        }

        @Override
        public void render(int x, int y, NGraphics g) {
            g.g().setColor(Color.cyan);
            g.g().fillRoundRect(posX,sloupec.y+stav*delkaJezdce,sirka,delkaJezdce,10,10);
        }
        public Rectangle getRectangle(){
            return sloupec;
        }
        public void setKousky(int pocetKousku){
            delkaJezdce=sloupec.height/pocetKousku;
            pressed=true;
            moved(new Location(posX, posY));
            pressed=false;
        }
        private void refreshStav(){
            if(stav!=oldStav)
                majitel.setStav(stav);
            oldStav=stav;
        }

        public int getStav() {
            return stav;
        }
    }

}
