package cs.cooble.nice.input;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.game_stats.components.CudlikMap;
import cs.cooble.nice.core.*;
import cs.cooble.nice.entity.KillerBot;
import cs.cooble.nice.entity.Matej;
import cs.cooble.nice.entity.Sysel;
import cs.cooble.nice.game_stats.CurrectScreenStatManager;
import cs.cooble.nice.game_stats.GamePauseStat;
import cs.cooble.nice.game_stats.IntroStat;
import cs.cooble.nice.game_stats.WorldChoiceStat;
import cs.cooble.nice.graphic.lighting.LightingMap;
import cs.cooble.nice.inventory.Inventory;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Input;

/**
 * Created by Matej on 24.6.2015.
 */
public class UserInput {

    public static MouseInput mouseInput = new MouseInput();
    public static KeyInput keyInput = new KeyInput();
    public static Input input;

    //==============GameStat Actions==================================
    public static int GO_DOWN = Input.KEY_DOWN;
    public static int GO_UP = Input.KEY_UP;
    public static int GO_RIGHT = Input.KEY_RIGHT;
    public static int GO_LEFT = Input.KEY_LEFT;
    public static int OPEN_INVENTORY = Input.KEY_I;
    public static int OPEN_CRAFTING = Input.KEY_C;
    public static int OPEN_CREATIVE = Input.KEY_O;
    public static int BOOST = Input.KEY_R;
    public static int SET_LIGHTS = Input.KEY_F11;
    public static int OPEN_FACTORY_SCREEN = Input.KEY_F3;
    public static int OPEN_CHAT = Input.KEY_T;

    public static boolean CLICK_LEFT = false;//normálnì pro funkce klikání levým tlaèítkem se bude používat levé tlaèítko myši (false==levy)
    public static boolean CLICK_RIGHT = true;

    //pomocný atribut aby se to nemuselo porad psat do vsech moznejch metod
    private static CurrectScreenStatManager screenStatManager = NiceWorld.getNiceWorld().getCurrectScreenStatManager();


    public static void onProgramStarted() {
    }


    public static void checkInput(World world, WorldsProvider worldsProvider) {
        keyInput.tick();
        mouseInput.tick();
        checkKeyInput(world, worldsProvider);
        checkMouseInput(world, worldsProvider);
    }

    private static void checkKeyInput(@Nullable World world, WorldsProvider worldsProvider) {
        if (NC.enteringText)
            return;

        Matej matej = null;
        if (world != null)
            matej = world.getMatej();

        switch (screenStatManager.getScreenStatName()) {
            case CurrectScreenStatManager.GAME:
                matej.down(keyInput.isPressed(GO_DOWN));
                matej.up(keyInput.isPressed(GO_UP));
                matej.right(keyInput.isPressed(GO_RIGHT));
                matej.left(keyInput.isPressed(GO_LEFT));


                if (keyInput.isfreshedPressed(OPEN_INVENTORY)) {
                    matej.refreshMatejInventory();
                }

                if (keyInput.isfreshedPressed(OPEN_CRAFTING))
                    matej.getMatejScreensControler().refreshMatejCrafting();


                if (keyInput.isfreshedPressed(BOOST))
                    matej.refreshBoost();

                if (keyInput.isfreshedPressed(OPEN_CREATIVE))
                    matej.getMatejScreensControler().refreshMatejCreative();

                if (keyInput.isfreshedPressed(SET_LIGHTS)) {
                    LightingMap.allowLight(!LightingMap.isLightAllowed());
                }
                if (keyInput.isfreshedPressed(OPEN_FACTORY_SCREEN)) {
                    F3.setActive(!F3.isActive());
                }
                if (keyInput.isfreshedPressed(Input.KEY_B)) {
                    KillerBot bot = new KillerBot();
                    bot.setLocation(matej.getX(), matej.getY());
                    world.getSpawner().spawnEntity(bot);
                }
                if (keyInput.isfreshedPressed(Input.KEY_S)) {
                    Sysel sysel = new Sysel();
                    sysel.setLocation(matej.getX(), matej.getY());
                    world.getSpawner().spawnEntity(sysel);
                }
                if (keyInput.isfreshedPressed(OPEN_CHAT)) {
                    matej.getMatejScreensControler().refreshMatejChat();

                }
                if (keyInput.isfreshedPressed(Input.KEY_ESCAPE))
                    screenStatManager.setSubSreenStat(new GamePauseStat());
                break;

            case CurrectScreenStatManager.GAME_PAUSE:
                if (keyInput.isfreshedPressed(Input.KEY_ESCAPE))
                    screenStatManager.removeSubGameStat();

                break;

            case CurrectScreenStatManager.INTRO:
                if (keyInput.isPressed(Input.KEY_DELETE) && keyInput.isPressed(Input.KEY_LSHIFT) && keyInput.isPressed(Input.KEY_LCONTROL)) {//Ctrl Alt Del = smazaní vseho
                    Log.println("============Ukoncuji násilím program a mažu slozku NiceWorld ============");
                    worldsProvider.removeEverything();
                    System.exit(1);
                }

                break;

            case CurrectScreenStatManager.WORLD_CHOICE:
                if (keyInput.isPressed(Input.KEY_DELETE) && keyInput.isPressed(Input.KEY_LCONTROL)) {//Ctrl Del = smazaní vsech svetù
                    String[] svety = worldsProvider.getWorldNames();
                    for (String svet : svety) {
                        worldsProvider.deleteSvet(svet);
                    }
                    screenStatManager.setScreenStat(new WorldChoiceStat());

                } else if (keyInput.isPressed(Input.KEY_ESCAPE))
                    screenStatManager.setScreenStat(new IntroStat());
                break;
        }
    }

    private static void checkMouseInput(@Nullable World world, WorldsProvider worldsProvider) {
        Matej matej = null;
        if (world != null)
            matej = world.getMatej();
        Location mouseLocation = mouseInput.getMouseLocation();

        if (mouseInput.isFreshlyPressed(CLICK_LEFT)) {//leve tlacitko
            if (!CudlikMap.getInstance().onPressed(mouseLocation))
                if (screenStatManager.getScreenStatName().equals(CurrectScreenStatManager.GAME) && !NC.isCudlikMode())
                    if (!Inventory.getInstance().click(world, mouseLocation, false))
                        if (!ActionRectangles.getInstance().click(mouseLocation, false))
                            if (matej.canClick(mouseLocation))
                                if (!world.onClickedTo(Location.getTrueWorldLocation(mouseLocation), false))
                                    Inventory.getInstance().clickToNothing(world, mouseLocation, false);
        } else if (mouseInput.isFreshlyReleased(CLICK_LEFT)) {//leve tlacitko pustit
            CudlikMap.getInstance().onReleased(mouseLocation);
        }

        if (mouseInput.isFreshlyPressed(CLICK_RIGHT)) {//prave tlacitko

            if (screenStatManager.getScreenStatName().equals(CurrectScreenStatManager.GAME) && !NC.isCudlikMode())
                if (!Inventory.getInstance().click(world, mouseLocation, true))
                    if (!ActionRectangles.getInstance().click(mouseLocation, true))
                        if (matej.canClick(mouseLocation))
                            if (!world.onClickedTo(Location.getTrueWorldLocation(mouseLocation), true))
                                Inventory.getInstance().clickToNothing(world, mouseLocation, true);
        }
        if (mouseInput.isFreshlyReleased(CLICK_LEFT)) {
            CudlikMap.getInstance().onReleased(mouseLocation);
        }
        CudlikMap.getInstance().onMouseMoved(mouseLocation);
        Inventory.getInstance().onMouseMoved(mouseLocation);

        int mouseWheeled = mouseInput.getWheelMoved();
        if (mouseWheeled != 0) {
            CudlikMap.getInstance().onMouseWheeled(mouseLocation, mouseWheeled);
        }
    }
}
