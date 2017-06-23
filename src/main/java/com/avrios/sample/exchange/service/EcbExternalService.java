package com.avrios.sample.exchange.service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.avrios.sample.exchange.model.DayExchangeRates;

import static com.avrios.sample.exchange.util.XmlUtil.asList;

@Service
public class EcbExternalService {
    private SimpleDateFormat ECB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;

    public DayExchangeRates fetchExchangeRates() throws Exception {
        String resource = restTemplate.getForObject(
                "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml",
                String.class);
        return parseXmlToExchangeMap(resource);
    }

    private DayExchangeRates parseXmlToExchangeMap(String string) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = null;
        document = builder.parse(new InputSource(new ByteArrayInputStream(string.getBytes())));
        document.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        NodeList dayNodes = (NodeList) xpath.evaluate("//Cube[@time]", document, XPathConstants.NODESET);
        DayExchangeRates exchangeTable = new DayExchangeRates();
        for (Node dayNode : asList(dayNodes)) {
            String time = dayNode.getAttributes().getNamedItem("time").getNodeValue();
            Date date = ECB_DATE_FORMAT.parse(time);
            NodeList exchanges = (NodeList) xpath.evaluate(
                    "Cube[@currency and @rate]",
                    dayNode,
                    XPathConstants.NODESET);

            Map<String, Double> dayExchangeRates = new HashMap<>();
            for (Node exchange : asList(exchanges)) {
                NamedNodeMap attributes = exchange.getAttributes();
                String currency = attributes.getNamedItem("currency").getNodeValue().toUpperCase().trim();
                Double rate = Double.valueOf(attributes.getNamedItem("rate").getNodeValue());
                dayExchangeRates.put(currency, rate);
            }
            exchangeTable.put(date, dayExchangeRates);
        }
        return exchangeTable;
    }
}
