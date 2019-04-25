package cs.cooble.nice.blocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.xml.parsers.*;

import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.ItemstackUtil;
import org.w3c.dom.*;

/**
 * Created by Matej on 6.4.2018.
 */
public class BlockTemplateParser {

    private static Map<String, Template> templateMap = new HashMap<>();

    public static Template get(String id, int meta) {
        return templateMap.get(id + "_" + meta);
    }

    public static Map<String, Template> getTemplateMap() {
        return templateMap;
    }

    public static void readXML(InputStream xml) {
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
            String blockId = doc.getAttribute("id");
            Template t = parse(doc, new Template());
            HashMap<Integer, Template> templates = new HashMap<>();
            for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
                Node n = doc.getChildNodes().item(i);
                if (n.getNodeName().equals("meta")) {
                    Template c = t.copy();
                    int meta = Integer.parseInt(n.getAttributes().getNamedItem("meta").getTextContent());
                    c = parse(n, c);
                    templates.put(meta, c);
                }
            }
            if (templates.size() == 0) {
                templates.put(0, t);
            }
            templates.forEach(new BiConsumer<Integer, Template>() {
                @Override
                public void accept(Integer integer, Template template) {
                    templateMap.put(blockId + "_" + integer, template);
                }
            });
        } catch (Exception pce) {
            pce.printStackTrace();
        }
    }
    public static void load() {
        File[] files = getResourceFolderFiles("/xml/block/");
        for(File f:files){
            try {
                readXML(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    private static File[] getResourceFolderFiles (String folder) {
        URL url = BlockTemplateParser.class.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }


    private static Template parse(Node node, Template out) {
        String material = null;
        String toolType = null;
        int hardness = Integer.MIN_VALUE;
        ItemStack[] itemstacks = null;
        String[] chances = null;

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node n = node.getChildNodes().item(i);
            switch (n.getNodeName()) {
                case "material":
                    material = n.getTextContent();
                    break;
                case "tooltype":
                    toolType = n.getTextContent();
                    break;
                case "hardness":
                    hardness = Integer.parseInt(n.getTextContent());
                    break;
                case "digged_items":
                    int itemsNUmber=0;
                    for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                        if (n.getChildNodes().item(j).getNodeName().equals("item")) {
                           itemsNUmber++;
                        }
                    }
                    itemstacks = new ItemStack[itemsNUmber];
                    chances = new String[itemsNUmber];
                    int currIndex = 0;
                    for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                        Node item = n.getChildNodes().item(j);
                        if (item.getNodeName().equals("item")) {
                            chances[currIndex] = item.getAttributes().getNamedItem("number").getTextContent();
                            itemstacks[currIndex] = ItemstackUtil.parseXML(item);
                            currIndex++;
                        }
                    }
                    break;
            }
        }
        if (hardness != Integer.MIN_VALUE)
            out.hardness = hardness;
        if (material != null)
            out.material = Block.toSound(material);
        if (toolType != null)
            out.toolType = ToolType.valueOf(toolType);
        if (itemstacks != null) {
            out.diggedItems = itemstacks;
            out.numberChances = chances;
        }
        return out;
    }
}
