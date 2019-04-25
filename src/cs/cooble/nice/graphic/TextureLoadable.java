package cs.cooble.nice.graphic;

/**
 * Created by Matej on 24.8.2017.
 * Really important to load textures after instance creation or loading from saves folder
 * Many classes need this to be called directly after constructor!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public interface TextureLoadable {
    /**
     * called by grahical thread after constructor
     * used to load images
     */
    default void loadTextures(TextureLoader loader){}
}
