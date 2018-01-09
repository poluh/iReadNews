package com.application.rss.read;

import com.application.file.WorkFile;
import com.application.rss.model.RSSFeed;
import com.application.rss.model.RSSMessage;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class RSSParser {
    private static final String TITLE = "title", DESCRIPTION = "description", LINK = "link",
            ITEM = "item", DATE = "pubDate", IMAGE = "url";

    private URL url;

    public RSSParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RSS not found. (" + feedUrl + ")");
            alert.setContentText("Sorry.\nUnfortunately, we could not find the specified RSS-feed.");
            alert.showAndWait();
            try {
                WorkFile.deleteRSSLink(feedUrl);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void catchRuntimeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Runtime error.");
        alert.setContentText("Sorry.\nServer is not responding\n");
        alert.showAndWait();
    }

    public RSSFeed readFeed() {
        RSSFeed feed = null;
        try {

            String description = "", title = "", link = "", date = "";
            Image image = null;


            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            InputStream portalRSS = read();
            XMLEventReader reader = inputFactory.createXMLEventReader(portalRSS);

            while (reader.hasNext()) {
                XMLEvent rawXml = reader.nextEvent();
                if (rawXml.isStartElement()) {
                    String tags = rawXml.asStartElement().getName().getLocalPart();
                    //System.out.println(tags);
                    switch (tags) {
                        case IMAGE:
                            image = new Image(getCharacterData(reader));
                            System.out.println(IMAGE);
                            break;
                        case ITEM:
                            if (feed == null) {
                                feed = new RSSFeed(title, description, link, date, image);
                            }
                            break;
                        case TITLE:
                            title = getCharacterData(reader);
                            break;
                        case DESCRIPTION:
                            description = getCharacterData(reader);
                            break;
                        case LINK:
                            link = getCharacterData(reader);
                            break;
                        case DATE:
                            date = getCharacterData(reader);
                            break;
                    }
                } else if (rawXml.isEndElement()) {
                    if (Objects.equals(rawXml.asEndElement().getName().getLocalPart(), ITEM)) {
                        RSSMessage message = new RSSMessage();
                        message.setLink(link);
                        message.setTitle(title);
                        message.setDate(date);
                        message.setDescription(description);
                        if (feed != null) {
                            feed.getMessages().add(message);
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            catchRuntimeError();
        }
        return feed;
    }

    private String getCharacterData(XMLEventReader reader)
            throws XMLStreamException {
        String result = "";
        XMLEvent event = reader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            catchRuntimeError();
        }
        return null;
    }

}
