package cs.cooble.nice.graphic;

/**
 * Created by Matej on 22.9.2017.
 */
public interface TextureLoader {
    IIcon getIcon(String imagePath);

    /**
     *
     * @param imagePath
     * @param size
     * @return polyIcon with icons like:{imagePath+_0,imagePath+_1,imagePath+......,imagePath+_(size-1)};
     */
    PolyIcon getIcon(String imagePath,int size);
}
