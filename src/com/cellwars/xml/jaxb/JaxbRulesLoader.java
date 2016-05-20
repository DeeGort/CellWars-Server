package com.cellwars.xml.jaxb;

import com.cellwars.scene.Rules;
import com.cellwars.xml.RulesLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by DeeGort on 2016-05-16.
 */
public class JaxbRulesLoader implements RulesLoader {
    private String input;

    public JaxbRulesLoader(String input) {
        this.input = input;
    }

    @Override
    public void load() {
        File file = new File(input);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Rules.class);
            Unmarshaller jaxbUnmMarshaller = jaxbContext.createUnmarshaller();

            Rules.initRules((Rules) jaxbUnmMarshaller.unmarshal(file));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
