package univ.rouen.cv24.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;
import univ.rouen.cv24.entity.Cv;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;

public class XML {

    public static boolean validateXMLSchema(String xmlContent) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(XML.class.getResource("/static/cv24.xsd"));

            JAXBContext jaxbContext = JAXBContext.newInstance(Cv.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.unmarshal(new StringReader(xmlContent));
            return true;
        } catch (SAXException | JAXBException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static Cv convertXmlToCv(String xmlContent) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Cv.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Cv) unmarshaller.unmarshal(new StringReader(xmlContent));
    }
}