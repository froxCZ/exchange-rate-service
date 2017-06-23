package com.avrios.sample.exchange.service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public static final String URL_90_DAYS = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
    private SimpleDateFormat ECB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Fetches exchange rates from ECB api.
     */
    @NotNull
    public DayExchangeRates fetchExchangeRates() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                URL_90_DAYS,
                String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("Received " + response.getStatusCode().value() +
                    " status code from ECB api.");
        }
        return parse90DaysXml(response.getBody());
    }

    /**
     * Parses XML from ECB service with data for 90 days.
     */
    @NotNull
    private DayExchangeRates parse90DaysXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = null;
        document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes())));
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
