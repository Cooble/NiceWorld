package cs.cooble.nice.event;

import java.util.ArrayList;

/**
 * Tøída, sídlící v NiceWorld starjící se o updatování všech objektù implementujících Updateable.
 */
public class UpdateableObserver implements Updateable{

    public UpdateableObserver(){}

    private ArrayList<Updateable> list = new ArrayList<>();

    public void addUpdateable(Updateable updateable){
        list.add(updateable);
    }

    void clearAllUpdateables(){
        list.clear();
    }

    /**
     * shouldnt be used, updatrable should remove by itself by isAlive()
     * @param updateable
     */
    public void removeUpdateable(Updateable updateable){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(updateable))
                list.remove(i);
        }
    }

    @Override
    public void update() {
        for (int i = list.size() - 1; i >= 0; i--) {
            Updateable updateable = list.get(i);
            if(updateable.isAlive()){
                updateable.update();
            }
            else list.remove(i);
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }

}
