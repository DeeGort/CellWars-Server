package com.cellwars.xml.jaxb;

import com.cellwars.scene.Rules;
import com.cellwars.xml.RulesCreator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by DeeGort on 2016-05-16.
 */
public class JaxbCreateRules implements RulesCreator {

    private String output;

    public JaxbCreateRules(String output) {
        this.output = output;
    }

    @Override
    public void save() {

        Rules.getRules().setMap(0, 0, 1024, 768);
        Rules.getRules().setCellRadius(25.0f);
        Rules.getRules().setPackageRadius(10.0f);
        Rules.getRules().setMaxCooky(20);
        Rules.getRules().setMaxMine(10);
        Rules.getRules().setIncSize(2.5f);

        File file = new File(output);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Rules.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // output pretty printed
            jaxbMarshaller.marshal(Rules.getRules(), file);
            jaxbMarshaller.marshal(Rules.getRules(), System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
