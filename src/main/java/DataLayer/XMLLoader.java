package DataLayer;

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

public class XMLLoader implements Loader {

    public static void main(String[] args){
        Loader l = new XMLLoader("C:\\Users\\Juraj\\IdeaProjects\\cardframework\\src\\main\\resources\\testcards.xml");
        LoadedCards c = l.loadCards();
        Deck d = c.createDeck();
    }

    private String path;
    public XMLLoader(String path){
        this.path = path;
    }

    public LoadedCards loadCards() {
        Document xmlDoc = getDocument(path);
        String deckName = xmlDoc.getDocumentElement().getAttribute("name");
        NodeList listOfCards = xmlDoc.getElementsByTagName("card");
        List<LoadedCard> list = new ArrayList<>();
        for(int i = 0; i < listOfCards.getLength(); i++){
            Node item = listOfCards.item(i);
            if(item.getNodeType() == Node.ELEMENT_NODE){
                Element card = (Element) item;
                int count = Integer.parseInt(card.getAttribute("count"));
                Map<String,String> map = new HashMap<>();
                NodeList properties= card.getChildNodes();
                for (int j = 0; j < properties.getLength(); j++){
                    Node property = properties.item(j);
                    if(property.getNodeType() == Node.ELEMENT_NODE) {
                        Element p = (Element) property;
                        map.put(p.getTagName(), p.getTextContent());
                    }
                }
                list.add(new LoadedCard(new Card(map), count));
            }
        }
        return new LoadedCards(list, deckName);
    }

    private Document getDocument(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(path));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}