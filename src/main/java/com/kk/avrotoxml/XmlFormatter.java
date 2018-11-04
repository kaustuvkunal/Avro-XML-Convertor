package com.kk.avrotoxml;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@SuppressWarnings("deprecation")
public class XmlFormatter
{

    private static Logger log = Logger.getLogger(XmlFormatter.class);

    public XmlFormatter()
    {
    }

    public String format(String unformattedXml) throws IOException
    {
        final Document document = parseXmlFile(unformattedXml);
        OutputFormat format = new OutputFormat(document);
        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(document);
        return out.toString();
    }

    private Document parseXmlFile(String in)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        }
        catch (ParserConfigurationException e)
        {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        catch (SAXException e)
        {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
