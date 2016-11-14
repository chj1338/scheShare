package com.share.calendar.rss_weather.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RssWeaFeedParser {
    	static final String DATA = "data";
		static final String HOUR = "hour";
		static final String DAY = "day";
		static final String TEMP = "temp";
		static final String TMX = "tmx";
		static final String TMN = "tmn";
		static final String SKY = "sky";
		static final String PTY = "pty";
		static final String POP = "pop";
		static final String WS = "ws";
		static final String WD = "wd";
		static final String REH = "reh";
		static final String R12 = "r12";
		static final String S12 = "s12";
		static final String R06 = "r06";
		static final String S06 = "s06";
		static final String WFKOR = "wfKor";
		static final String WFEN = "wfEn";
		static final String WDKOR = "wdKor";
		static final String WDEN = "wdEn";
        static final String PUBDATE = "pubDate";

        final URL url;

        public RssWeaFeedParser(String feedUrl) {
                try {
                        this.url = new URL(feedUrl);
                } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                }
        }

        public RssWeaFeed readFeed() {
                RssWeaFeed feed = null;
                try {
                        boolean isFeedHeader = true;
                        // Set header values intial to the empty string
                        String hour = "";
                        String day = "";
                        String temp = "";
                        String tmx = "";
                        String tmn = "";
                        String sky = "";
                        String pty = "";
                        String pop = "";
                        String ws = "";
                        String wd = "";
                        String reh = "";
                        String r12 = "";
                        String s12 = "";
                        String r06 = "";
                        String s06 = "";
                        String wfKor = "";
                        String wfEn = "";
                        String wdKor = "";
                        String wdEn = "";
                        String pubDate = "";

                        // First create a new XMLInputFactory
                        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                        // Setup a new eventReader
                        InputStream in = read();
                        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
                        // read the XML document
                        while (eventReader.hasNext()) {
                                XMLEvent event = eventReader.nextEvent();
                                if (event.isStartElement()) {
                                        String localPart = event.asStartElement().getName().getLocalPart();
                                        switch (localPart) {
                                        case DATA:
                                                if (isFeedHeader) {
                                                        isFeedHeader = false;
                                                        feed = new RssWeaFeed(hour, day, temp, tmx, tmn, sky, pty, pop, ws, wd, reh, r12, s12, r06, s06, wfKor, wfEn, wdKor, wdEn, pubDate);
                                                }
                                                event = eventReader.nextEvent();
                                                break;
                                        case HOUR:
                                            hour = getCharacterData(event, eventReader);
                                            break;
                                      case DAY:
                                            day = getCharacterData(event, eventReader);
                                            break;
                                      case TEMP:
                                            temp = getCharacterData(event, eventReader);
                                            break;
                                      case TMX:
                                            tmx = getCharacterData(event, eventReader);
                                            break;
                                      case TMN:
                                            tmn = getCharacterData(event, eventReader);
                                            break;
                                      case SKY:
                                            sky = getCharacterData(event, eventReader);
                                            break;
                                      case PTY:
                                            pty = getCharacterData(event, eventReader);
                                            break;
                                      case POP:
                                            pop = getCharacterData(event, eventReader);
                                            break;
                                      case WS:
                                            ws = getCharacterData(event, eventReader);
                                            break;
                                      case WD:
                                            wd = getCharacterData(event, eventReader);
                                            break;
                                      case REH:
                                            reh = getCharacterData(event, eventReader);
                                            break;
                                      case R12:
                                            r12 = getCharacterData(event, eventReader);
                                            break;
                                      case S12:
                                            s12 = getCharacterData(event, eventReader);
                                            break;
                                      case R06:
                                            r06 = getCharacterData(event, eventReader);
                                            break;
                                      case S06:
                                            s06 = getCharacterData(event, eventReader);
                                            break;
                                      case WFKOR:
                                            wfKor = getCharacterData(event, eventReader);
                                            break;
                                      case WFEN:
                                            wfEn = getCharacterData(event, eventReader);
                                            break;
                                      case WDKOR:
                                            wdKor = getCharacterData(event, eventReader);
                                            break;
                                      case WDEN:
                                            wdEn = getCharacterData(event, eventReader);
                                            break;
                                      case PUBDATE:
                                            pubDate = getCharacterData(event, eventReader);
                                            break;
                                        }
                                } else if (event.isEndElement()) {
                                        if (event.asEndElement().getName().getLocalPart() == (DATA)) {
                                                RssWeaFeedMessage message = new RssWeaFeedMessage();

                                                message.setHour(hour);
                                                message.setDay(day);
                                                message.setTemp(temp);
                                                message.setTmx(tmx);
                                                message.setTmn(tmn);
                                                message.setSky(sky);
                                                message.setPty(pty);
                                                message.setPop(pop);
                                                message.setWs(ws);
                                                message.setWd(wd);
                                                message.setReh(reh);
                                                message.setR12(r12);
                                                message.setS12(s12);
                                                message.setR06(r06);
                                                message.setS06(s06);
                                                message.setWfKor(wfKor);
                                                message.setWfEn(wfEn);
                                                message.setWdKor(wdKor);
                                                message.setWdEn(wdEn);
                                                message.setPubDate(pubDate);
                                                
                                                feed.getMessages().add(message);
                                                event = eventReader.nextEvent();
                                                continue;
                                        }
                                }
                        }
                } catch (XMLStreamException e) {
                        throw new RuntimeException(e);
                }
                return feed;
        }

        private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
                        throws XMLStreamException {
                String result = "";
                event = eventReader.nextEvent();
                if (event instanceof Characters) {
                        result = event.asCharacters().getData();
                }
                return result;
        }

        private InputStream read() {
                try {
                        return url.openStream();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }
}