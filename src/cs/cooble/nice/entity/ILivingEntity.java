package cs.cooble.nice.entity;

import cs.cooble.nice.core.World;

/**
 * Created by Matej on 1.2.2015.
 */
public interface ILivingEntity extends IEntity {

    /**
     * called when mob is hitted
     * @param kdo
     * @param damage
     */
    void causeDamage(World world,IEntity kdo,int damage);

    /**
     * called upon death
     */
    default void onDeath(World world,IEntity vrah){}

}
