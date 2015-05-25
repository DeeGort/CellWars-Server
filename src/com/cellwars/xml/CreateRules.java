package com.cellwars.xml;

import com.cellwars.actor.*;
import com.cellwars.scene.Player;
import com.cellwars.scene.Rules;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by Tamás on 2015-04-27.
 */
public class CreateRules {
    private String output;

    private final Rectangle MAP = new Rectangle(0, 0, 1024, 768);
    private final float CELLRADIUS = 25.0f;
    private final float PACKAGERADIUS = 10.0f;
    private final int MAXCOOKY = 20;
    private final int MAXMINE = 10;
    private final float INCSIZE = 2.5f;

    public CreateRules(String output) {
        this.output = output;
    }

    public void save() {
        Document doc = DocumentFactory.newDocument();

        Element rootElem = doc.createElement("CellWars");
        doc.appendChild(rootElem);

        saveRules(doc, rootElem);

        writeXML(doc);
    }

    private void saveRules(Document doc, Element rootElem) {
        Element rulesElem = doc.createElement("Rules");
        rootElem.appendChild(rulesElem);

        Element mapElem = doc.createElement("MAP");

        Element xPosElem = doc.createElement("x");
        xPosElem.setTextContent(Double.toString(MAP.getX()));
        mapElem.appendChild(xPosElem);

        Element yPosElem = doc.createElement("y");
        yPosElem.setTextContent(Double.toString(MAP.getY()));
        mapElem.appendChild(yPosElem);

        Element wSizeElem = doc.createElement("w");
        wSizeElem.setTextContent(Double.toString(MAP.getWidth()));
        mapElem.appendChild(wSizeElem);

        Element hSizeElem = doc.createElement("h");
        hSizeElem.setTextContent(Double.toString(MAP.getHeight()));
        mapElem.appendChild(hSizeElem);

        rulesElem.appendChild(mapElem);

        Element cellRadius = doc.createElement("CELLRADIUS");
        cellRadius.setTextContent(Float.toString(CELLRADIUS));
        rulesElem.appendChild(cellRadius);

        Element packageRadius = doc.createElement("PACKAGERADIUS");
        packageRadius.setTextContent(Float.toString(PACKAGERADIUS));
        rulesElem.appendChild(packageRadius);

        Element maxCooky = doc.createElement("MAXCOOKY");
        maxCooky.setTextContent(Integer.toString(MAXCOOKY));
        rulesElem.appendChild(maxCooky);

        Element maxMine = doc.createElement("MAXMINE");
        maxMine.setTextContent(Integer.toString(MAXMINE));
        rulesElem.appendChild(maxMine);

        Element incSize = doc.createElement("INCSIZE");
        incSize.setTextContent(Float.toString(INCSIZE));
        rulesElem.appendChild(incSize);
    }

    private void writeXML(Document d) {

        System.out.println("itt");
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();

            transformer.transform(new DOMSource(d),
                    new StreamResult(new File(output)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
