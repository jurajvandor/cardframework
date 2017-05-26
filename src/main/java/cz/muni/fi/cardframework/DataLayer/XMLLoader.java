package cz.muni.fi.cardframework.DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * loads cards from xml file with structre:
 * <cards name="name of cards">
 *     <card count="1">
 *         <suit>spades</suit>
 *         <value>A</value>
 *     </card>
 * </cards>
 */
public class XMLLoader implements Loader {

    private static AtomicInteger idCounter = new AtomicInteger(0);

    private String path;

    /**
     * creates loader with path to XML file
     * @param path
     */
    public XMLLoader(String path){
        this.path = path;
    }

    @Override
    public LoadedCards loadCards() {
        Document xmlDoc = getDocument();
        String deckName = xmlDoc.getDocumentElement().getAttribute("name");
        NodeList listOfCards = xmlDoc.getElementsByTagName("card");
        ArrayList<LoadedCard> list = new ArrayList<>();

        for(int i = 0; i < listOfCards.getLength(); i++){
            Node item = listOfCards.item(i);
            if(item.getNodeType() == Node.ELEMENT_NODE){

                Element card = (Element) item;
                int count = Integer.parseInt(card.getAttribute("count"));
                HashMap<String,String> map = new HashMap<>();
                NodeList properties= card.getChildNodes();

                for (int j = 0; j < properties.getLength(); j++){
                    Node property = properties.item(j);
                    if(property.getNodeType() == Node.ELEMENT_NODE) {
                        Element p = (Element) property;
                        map.put(p.getTagName(), p.getTextContent());
                    }
                }
                list.add(new LoadedCard(new Card(map, idCounter.incrementAndGet()), count));
            }
        }
        return new LoadedCards(list, deckName);
    }


    /**
     * @return transformed XML file to Document object for parser
     */
    private Document getDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(XMLLoader.class.getClassLoader().getResourceAsStream(path)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}