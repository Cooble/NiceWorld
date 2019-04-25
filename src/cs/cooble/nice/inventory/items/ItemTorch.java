package cs.cooble.nice.inventory.items;

import cs.cooble.nice.core.World;
import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 17.6.2015.
 */
public class ItemTorch extends Item {
    public ItemTorch() {
        super("torch");
    }

    @Override
    public void onItemHeldInHand(World world,int holdStat) {
        Location matejloc;
        try {
            matejloc=new Location((int) (world.getMatej().getLocation().X+world.getMatej().getShape().getWidth()/2),world.getMatej().getLocation().Y+40);
        }
        catch (Exception e){
            matejloc=Location.blank();
        }
       /* switch (holdStat){
            case HoldStatEQUIPPED:
                light.setLocation(matejloc);
                LightingMap.addLight(light);
                break;
            case HoldStatHELD:
                light.setLocation(matejloc);
                break;
            case HoldStatUNEQUIPPED:
                LightingMap.removeLight(light);
                break;
        }*/
    }
}
