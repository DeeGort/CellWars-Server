package com.cellwars.xml;

import com.cellwars.scene.Rules;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Created by Tam�s on 2015-05-25.
 */
public class RulesLoader {
    private String input;

    public RulesLoader(String input) {
        this.input = input;
    }

    public void load() {
        Document doc = DocumentFactory.openDocument(input);

        Element rootElem = doc.getDocumentElement();
        Element rulesElem = (Element) rootElem.getElementsByTagName("Rules").item(0);
        Element mapElem = (Element) rulesElem.getElementsByTagName("MAP").item(0);
        Element xElem = (Element) mapElem.getElementsByTagName("x").item(0);
        Element yElem = (Element) mapElem.getElementsByTagName("y").item(0);
        Element wElem = (Element) mapElem.getElementsByTagName("w").item(0);
        Element hElem = (Element) mapElem.getElementsByTagName("h").item(0);
        Element cellradiusElem = (Element) rulesElem.getElementsByTagName("CELLRADIUS").item(0);
        Element packageradiusElem = (Element) rulesElem.getElementsByTagName("PACKAGERADIUS").item(0);
        Element maxcookyElem = (Element) rulesElem.getElementsByTagName("MAXCOOKY").item(0);
        Element maxmineElem = (Element) rulesElem.getElementsByTagName("MAXMINE").item(0);
        Element incsizeElem = (Element) rulesElem.getElementsByTagName("INCSIZE").item(0);

        double x = Double.parseDouble(xElem.getTextContent());
        double y = Double.parseDouble(yElem.getTextContent());
        double w = Double.parseDouble(wElem.getTextContent());
        double h = Double.parseDouble(hElem.getTextContent());
        float cellradius = Float.parseFloat(cellradiusElem.getTextContent());
        float packageradius = Float.parseFloat(packageradiusElem.getTextContent());
        int maxcooky = Integer.parseInt(maxcookyElem.getTextContent());
        int maxmine = Integer.parseInt(maxmineElem.getTextContent());
        float incsize = Float.parseFloat(incsizeElem.getTextContent());

        Rules.MAP = new Rectangle(x, y, w, h);
        Rules.CELLRADIUS = cellradius;
        Rules.PACKAGERADIUS = packageradius;
        Rules.MAXCOOKY = maxcooky;
        Rules.MAXMINE = maxmine;
        Rules.INCSIZE = incsize;
    }
}
