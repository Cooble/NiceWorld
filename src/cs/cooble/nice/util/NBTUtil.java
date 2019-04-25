package cs.cooble.nice.util;

import org.w3c.dom.Node;

/**
 * Created by Matej on 27.2.2018.
 */
public class NBTUtil {
    public static NBT parseXML(Node node){
        if(!node.getNodeName().equals("nbt"))
            return null;
        NBT nbt = new NBT();
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node n = node.getChildNodes().item(i);
            try {


                String id = n.getAttributes().getNamedItem("id").getTextContent();
                String value = n.getTextContent();
                if (value == null) {
                    value = n.getAttributes().getNamedItem("value").getTextContent();
                }
                switch (n.getNodeName()) {
                    case "int":
                        nbt.setIntenger(id, Integer.parseInt(value));
                        break;
                    case "double":
                        nbt.setDouble(id, Double.parseDouble(value));
                        break;
                    case "string":
                        nbt.setString(id, value);
                        break;
                    case "bool":
                        nbt.setBoolean(id, Boolean.parseBoolean(value));
                        break;
                    case "nbt":
                        nbt.setNBT(id, parseXML(n));
                        break;
                }
            }catch (Exception ignored){}
        }
        return nbt;
    }

}
