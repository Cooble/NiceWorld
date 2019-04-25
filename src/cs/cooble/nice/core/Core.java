package cs.cooble.nice.core;

import cs.cooble.nice.game_stats.components.CurrectTextCudlik;
import cs.cooble.nice.event.CEventBus;
import cs.cooble.nice.event.Event;
import cs.cooble.nice.event.Events;
import cs.cooble.nice.game_stats.CurrectScreenStatManager;
import cs.cooble.nice.input.UserInput;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.*;


/**
 * Created by Magnus on 24/07/2015.
 */
public class Core extends BasicGame {

    private boolean initialized;

    public CEventBus EVENT_BUS;

    private Event gameLoadEvent;


    public static Core create(String title) throws SlickException {
        Core core = new Core(1920,1080,title);
        AppGameContainer container = new AppGameContainer(core);
        container.setAlwaysRender(true);
        container.setDisplayMode(core.WIDTH, core.HEIGHT, core.FULLSCREEN);
        int tps = 1000 / core.TARGET_TPS;
        container.setMinimumLogicUpdateInterval(tps);
        container.setMaximumLogicUpdateInterval(tps);
        container.setTargetFrameRate(60);
        container.setVerbose(false);
        // container.setVSync(true);

        core.container = container;
        return core;
    }

    public int TARGET_TPS;
    public final int WIDTH, HEIGHT;
    private boolean FULLSCREEN;

    private AppGameContainer container;

    private Core(int WIDTH, int HEIGHT, String title) {
        super(title);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        TARGET_TPS = 60;
        EVENT_BUS=new CEventBus();
    }

    public void start(Event event) {
        gameLoadEvent = event;
        try {
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    private void render(Graphics g) {
        NiceWorld.getNiceWorld().getRenderer().render(g);
    }

    private void tick() {
        F3.FPS=container.getFPS();
        NiceWorld.getNiceWorld().checkInput();
        if(NC.shouldOnUpdate) {
            if(NiceWorld.getNiceWorld().getCurrectScreenStatManager().getScreenStatName().equals(CurrectScreenStatManager.GAME)) {
                NiceWorld.getNiceWorld().onUpdate();
                F3.mouseWorldLoc= Location.getTrueWorldLocation(F3.mouseLoc);//refresh world mouse location

            }

        }
    }

    public void stop() {
        container.destroy();
    }

    /**
     * preprepreInit
     *
     * @param gameContainer
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        //inputs
        container.getInput().addKeyListener(UserInput.keyInput);
        container.getInput().addMouseListener(UserInput.mouseInput);
        container.getInput().addKeyListener(CurrectTextCudlik.getInstance());
        UserInput.input=container.getInput();
        Log.println("Added listeners!");
        gameLoadEvent.dispatchEvent();
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        tick();
        EVENT_BUS.proccessEvents();
    }

    @Override
    public void render(GameContainer gameContainer, org.newdawn.slick.Graphics graphics) throws SlickException {
            render(graphics);
    }
    @Override
    public boolean closeRequested() {
        Events.onGameQuited();
        return false;
    }
}

