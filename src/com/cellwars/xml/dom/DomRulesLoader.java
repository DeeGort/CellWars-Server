package com.cellwars.xml.dom;

import com.cellwars.scene.Rules;
import com.cellwars.xml.RulesLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by Tam�s on 2015-05-25.
 */
public class DomRulesLoader implements RulesLoader {
    private String input;

    public DomRulesLoader(String input) {
        this.input = input;
    }

    @Override
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

        Rules.getRules().setMap(x, y, w, h);
        Rules.getRules().setCellRadius(cellradius);
        Rules.getRules().setPackageRadius(packageradius);
        Rules.getRules().setMaxCooky(maxcooky);
        Rules.getRules().setMaxMine(maxmine);
        Rules.getRules().setIncSize(incsize);
    }
}
