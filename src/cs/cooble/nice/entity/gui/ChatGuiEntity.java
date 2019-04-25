package cs.cooble.nice.entity.gui;

import cs.cooble.nice.chat.SlovoManager;
import cs.cooble.nice.chat.command.Commands;
import cs.cooble.nice.game_stats.components.CudlikMap;
import cs.cooble.nice.game_stats.components.CurrectTextCudlik;
import cs.cooble.nice.game_stats.components.ICudlik;
import cs.cooble.nice.game_stats.components.OnlyTextCudlik;
import cs.cooble.nice.graphic.Renderer;

/**
 * Created by Matej on 5.4.2015.
 */
public class ChatGuiEntity extends GuiEntity {
    String currectTypedText;
    int kolikratTab=0;
    OnlyTextCudlik cudlik;

    public ChatGuiEntity(){
        super(null,"chat_gui_entity");
        cudlik = new OnlyTextCudlik(0, 900, 300, 50, "chattext", "nic",false, new ICudlik() {
            @Override
            public void onSetData(Object data) {
                if(data=="tab"){
                    if(currectTypedText.startsWith("/")){
                        kolikratTab++;
                        if(currectTypedText.indexOf(' ')==-1/*neni tam zadna mezera*/) {
                            int koliktabzbyva = kolikratTab;
                            String com = currectTypedText.substring(1);

                            int pocetmoznosti = 0; //zjistuji kollik moznosti je
                            for (int i = 0; i < Commands.getInstance().getCommands().size(); i++) {
                                if (Commands.getInstance().getCommands().get(i).NAZEV.startsWith(com)) {
                                    pocetmoznosti++;
                                }
                            }

                            if (kolikratTab > pocetmoznosti) {
                                kolikratTab = 1;
                                koliktabzbyva = 1;
                            }

                            for (int i = 0; i < Commands.getInstance().getCommands().size(); i++) {
                                if (Commands.getInstance().getCommands().get(i).NAZEV.startsWith(com)) {

                                    if (koliktabzbyva == 1) {
                                        cudlik.setText("/" + Commands.getInstance().getCommands().get(i).NAZEV);
                                        break;
                                    } else koliktabzbyva--;
                                }
                            }
                        }
                        else {
                            cudlik.setText(Commands.getInstance().getCommand(SlovoManager.getSlovoFrom(currectTypedText, 0).substring(1)).dopln(kolikratTab,currectTypedText));
                        }
                    }
                }
                else if(data=="enter"){
                    if(cudlik.getText().startsWith("/")) {
                        Commands.getInstance().action(cudlik.getText().substring(1));
                    }
                }
                else if(data=="backspace"){
                    if(kolikratTab!=0) {
                        cudlik.setText(currectTypedText);
                        kolikratTab=0;
                    }
                }
                else if(((String)data).endsWith(" ")){
                    currectTypedText=cudlik.getText();
                    kolikratTab=0;
                }
                else {
                    currectTypedText=cudlik.getText();
                }
            }
        });
        cudlik.setText("");
        rectangle=cudlik.getRectangle();
    }

    @Override
    public void onGuiEntityStartDrawing(Renderer renderer) {
        CudlikMap.getInstance().addCudlik(cudlik);
        CurrectTextCudlik.setTextCudlik(cudlik);
    }

    @Override
    public void onGuiEntityStopDrawing(Renderer renderer) {
        CudlikMap.getInstance().removeAll();
    }
}
//todo nejak vycistit tu hruzu známou jako cudlikmap, currecttextcudlik etcetera
