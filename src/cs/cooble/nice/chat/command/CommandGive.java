package cs.cooble.nice.chat.command;

import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.chat.SlovoManager;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.entity.ItemEntity;

/**
 * Created by Matej on 5.4.2015.
 */
public class CommandGive extends Command {

    private String[] types= {"block","item"};

    public CommandGive() {
        super("give");
    }

    @Override
    public void action(String s) {
        boolean isnumber=false;
        for (int i = 0; i < 9; i++) {
            if(s.endsWith(i+"")){
                isnumber=true;
                break;
            }
        }
        int pocet=1;
        if(isnumber)
        pocet = Integer.parseInt(SlovoManager.getSlovoFrom(s,2));
        s=SlovoManager.getSlovoFrom(s,1);


        if(GameRegistry.getInstance().getItem(s)!=null){
            world.getSpawner().spawnEntity(ItemEntity.build(world.getMatej().getX(), world.getMatej().getY(), new ItemStack(GameRegistry.getInstance().getItem(s), pocet)));
        }
        else if(GameRegistry.getInstance().getBlock(s)!=null){
            world.getSpawner().spawnEntity(ItemEntity.build(world.getMatej().getX(), world.getMatej().getY(), GameRegistry.getInstance().getBlock(s).getItemFromBlock().setPocet(pocet)));
        }
    }

    @Override
    public String dopln(int index, String veta) {

        if(SlovoManager.getPocetSlov(veta)==1||(SlovoManager.getPocetSlov(veta)==2&&!veta.endsWith(" "))) {//pokud jsem napsal "/give" nebo pokud "/give it"
            String slovo = "";
            String cistaveta=SlovoManager.removeKolemMezery(veta);
            if (SlovoManager.getPocetSlov(veta) != 1) {
                slovo = SlovoManager.getSlovoFrom(veta, 1);//ziskavam posledni slovo pokud je veta 1slovna- slovo="";
                cistaveta=SlovoManager.removeSlovoFromVeta(veta,1);
                cistaveta=SlovoManager.removeKolemMezery(cistaveta);
            }
            int nalezeno = 0;

            index = index % types.length;
            index++;
            for (String type : types) {
                if (type.startsWith(slovo))
                    nalezeno++;
                if (nalezeno == index)
                    return cistaveta + " " + type;
            }
        }
        //pokud jsem naspal "/give item kamin"

        String slovo = "";
        String cistaveta="";
        int nalezeno=0;
        if(SlovoManager.getPocetSlov(veta)==2) {//pokud jsem napsal "/give item"
            slovo = "";
            cistaveta=SlovoManager.removeKolemMezery(veta);
        }
        else {
            slovo=SlovoManager.getSlovoFrom(veta,2);//get "kamin"
            cistaveta=SlovoManager.removeSlovoFromVeta(veta,2);
        }

        boolean isItem = SlovoManager.getSlovoFrom(veta, 1).equals(types[1]);
        if (isItem) {
            int pocetitemu = 0;
            for (int i = 0; i < GameRegistry.getInstance().getItems().size(); i++) {
               /* String command = GameRegistry.getInstance().getItems().get(i).getName();
                if (command.startsWith(slovo)) {
                    pocetitemu++;
                }*/
            }
            index = index % pocetitemu;
            index++;

        } else {
            int pocetbloku = 0;
            for (int i = 0; i < GameRegistry.getInstance().getBlocks().size(); i++) {
                String command = GameRegistry.getInstance().getBlocks().get(i).getID();
                if (command.startsWith(slovo)) {
                    pocetbloku++;
                }
            }
            index = index % pocetbloku;
            index++;
        }


            nalezeno = 0;
            if (isItem) {//pokud hledam item
                for (int i = 0; i < GameRegistry.getInstance().getItems().size(); i++) {
                   /* String command = GameRegistry.getInstance().getItems().get(i).getName();
                    if (command.startsWith(slovo)) {
                        nalezeno++;
                        if (nalezeno == index)
                            return cistaveta + " " + command;
                    }*/
                }
            } else
                for (int i = 0; i < GameRegistry.getInstance().getBlocks().size(); i++) {
                    String command = GameRegistry.getInstance().getBlocks().get(i).getID();
                    if (command.startsWith(slovo)) {
                        nalezeno++;
                        if (nalezeno == index)
                            return cistaveta + " " + command;
                    }
                }
            return "nic";
    }

}
