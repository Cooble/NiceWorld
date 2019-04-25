package cs.cooble.nice.inventory;

import cs.cooble.nice.blocks.*;
import cs.cooble.nice.game_stats.components.ColorizedText;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.inventory.items.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Matej on 22.3.2015.
 */
public final class CraftingRegistry implements Serializable {
    private static final ArrayList<Crafting> list = new ArrayList<>();
    private static final ArrayList<CraftingTab> listTab = new ArrayList<>();

    public static final CraftingTab FURNITURE=new CraftingTab(  ColorizedText.build("Furniture"), ImageManager.BLOCK+"chest_0"   ,0).add();
    public static final CraftingTab TOOLS=new CraftingTab(      ColorizedText.build("Tools")    , ImageManager.ITEM+"tools"   ,1).add();
    public static final CraftingTab MATERIALS=new CraftingTab(  ColorizedText.build("Materials"), ImageManager.BLOCK+"klacek"  ,2).add();

    public static void registerCrafting(CraftingTab craftingTab, ItemStack output, ItemStack... parametres){
        list.add(new Crafting(parametres,output,craftingTab));
    }

    public static ArrayList<Crafting> getCraftings(CraftingTab craftingTab){
        ArrayList<Crafting> list = new ArrayList<>();
        for (int i = 0; i < CraftingRegistry.list.size(); i++) {
            if(CraftingRegistry.list.get(i).CRAFTINGTAB.equals(craftingTab))
                list.add(CraftingRegistry.list.get(i));
        }
        return list;
    }

    public static CraftingRegistry.CraftingTab[] getCraftingTabs(){
        return listTab.toArray(new CraftingTab[listTab.size()]);
    }

    public static class Crafting implements Serializable{
        public final ItemStack[] RESOURCES;
        public final ItemStack OUTPUT;
        public final CraftingTab CRAFTINGTAB;
        public Crafting(ItemStack[] itemStacks, ItemStack output, CraftingTab craftingtab){
            OUTPUT = output;
            RESOURCES = itemStacks;
            CRAFTINGTAB = craftingtab;
        }
    }

    public static class CraftingTab implements Serializable{
        public final ColorizedText NAME;
        public final int ID;
        public final String textureName;
        public CraftingTab(ColorizedText name,String textureName, int id){
            NAME = name;
            this.ID = id;
            this.textureName=textureName;
        }

        @Override
        public String toString() {
            return NAME.getText(0);
        }
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof CraftingTab)
                return ((CraftingTab) obj).ID==this.ID;
            return false;
        }

        /**
         * Adds instacne to list to craftingregistry
         * @return
         */
        public CraftingTab add(){
            CraftingRegistry.listTab.add(this);
            return this;
        }
    }

    /**
     * called on event gamestarted
     */
    public static void registerRecipes(){
        registerCrafting(CraftingRegistry.FURNITURE,Blocks.chest.getItemFromBlock(),new ItemStack(Items.poleno,10));
       // registerCrafting(CraftingRegistry.FURNITURE,Blocks.pec.getItemFromBlock(),new ItemStack(Items.kaminek,20));
        registerCrafting(CraftingRegistry.FURNITURE,Blocks.kos.getItemFromBlock(),new ItemStack(Items.poleno,20),new ItemStack(Items.zeleznyIngot,6));
      // registerCrafting(CraftingRegistry.TOOLS,new ItemStack(Items.spicakklacek),Blocks.klacek.getItemFromBlock(),new ItemStack(new ItemStone(),2));
        registerCrafting(CraftingRegistry.TOOLS,new ItemStack(Items.krumpac),Blocks.klacek.getItemFromBlock(),new ItemStack(Items.zeleznyIngot,6));
        registerCrafting(CraftingRegistry.TOOLS,new ItemStack(Items.sekera),Blocks.klacek.getItemFromBlock(),new ItemStack(Items.zeleznyIngot,10));
    }
}

