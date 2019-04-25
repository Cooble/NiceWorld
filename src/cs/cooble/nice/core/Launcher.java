package cs.cooble.nice.core;

import cs.cooble.nice.TestClass;
import cs.cooble.nice.event.Events;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.event.Event;
import cs.cooble.nice.core.Core;
import org.newdawn.slick.SlickException;

public final class Launcher {

    public static void main(String[] args) throws SlickException {

       //-Dsun.java2d.opengl=true
       // System.setProperty("sun.java2d.opengl","true");
       // System.setProperty("sun.java2d.d3d","false");
      //  System.setProperty("sun.java2d.noddraw", "false");

        Core core = Core.create("NiceWorld");
        NC.core = core;
        core.start(new Event() {
            @Override
            public void dispatchEvent() {
                TestClass.test();

                Events.onGameStarted();

            }
        });



    }
}
