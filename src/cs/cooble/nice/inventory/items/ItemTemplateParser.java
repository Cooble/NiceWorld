package cs.cooble.nice.inventory.items;

import cs.cooble.nice.blocks.Template;
import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.gameloading.Saver;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.logger.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Matej on 13.4.2018.
 * Loads attributes for existing items,
 * creates new items if the specified itemclass does not exist
 */
public class ItemTemplateParser {

    public static void load() {
        File[] files = Saver.getResourceFolderFiles("xml/item/");

        for (File f : files) {
            try {
                readXML(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readXML(InputStream xml) {
        if (xml == null) {
            Log.println("xml is null!", Log.LogType.ERROR);
            Log.printStackTrace("xml is called from");
            return;
        }
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(xml);
            Element doc = dom.getDocumentElement();
            String id = doc.getAttribute("id");
            Item item = GameRegistry.getInstance().getItem(id);
            String hp = getTextContent(doc, "hp");
            if (item != null) {
                parse(doc, item);
            } else {
                if (hp != null)
                    item = new ItemTool(id);
                else
                    item = new Item(id);
                parse(doc, item);
                GameRegistry.getInstance().registerItem(item);
            }
        } catch (Exception pce) {
            pce.printStackTrace();
        }
    }

    private static void parse(Node node, Item item) {
        String s = getTextContent(node, "toolType");
        if (s != null)
            item.toolType = ToolType.valueOf(s);
        s = getTextContent(node, "stackSize");
        if (s != null)
            item.maxStackSize = Integer.parseInt(s);
        s = getTextContent(node, "maxMeta");
        if (s != null)
            item.maxMeta = Integer.parseInt(s);
        s = getTextContent(node, "texture");
        if (s != null)
            item.textureName = s;
        s = getTextContent(node, "hp");
        if (s != null)
            ((ItemTool)item).maxDamage = Integer.parseInt(s);
    }

    private static Node getNode(Node src, String nodeName) {
        for (int i = 0; i < src.getChildNodes().getLength(); i++) {
            Node child = src.getChildNodes().item(i);
            if (child.getNodeName().equals(nodeName)) {
                return child;
            }
        }
        return null;
    }

    private static String getTextContent(Node src, String nodeName) {
        Node n = getNode(src, nodeName);
        if (n == null)
            return null;
        return n.getTextContent();
    }
}
