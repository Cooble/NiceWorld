package cs.cooble.nice.graphic;

import java.util.List;

/**
 * Creates array of icons
 * Used for sprites (when animal has more images for movement)
 */
public class PolyIcon {
    private final int size;
    private final String id;
    private List<IIcon> icons;

    /**
     *
     * @param id prefix of name
     * @param size maximum suffix of name+1
     *
     */
    public PolyIcon(String id, int size) {
        this.size = size;
        this.id = id;
    }

    public IIcon getIcon(int currentIndex) {
        if(currentIndex>=icons.size())
            return null;
        return icons.get(currentIndex);
    }

    public void setList(List<IIcon> icons){
        this.icons = icons;
    }

    public int getSize() {
        return size;
    }

    public String getId() {
        return id;
    }
}
