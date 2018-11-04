package com.kk.avrotoxml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import org.json.JSONArray;
import org.json.XML;
import org.apache.log4j.Logger;

public class AvroToXMLConvertor
{
    private static Logger log = Logger.getLogger(AvroToXMLConvertor.class);

    public static void main(String[] args) throws IOException
    {
        if (args.length != 2)
        {
            log.error(" Invalid argument usage ");
            log.info(
                    " Correct usage (2 arguments) :  AvroinputFile  XMLOutput File ");
            System.exit(1);

        }

        String inputpath = args[0];
        String outputpath = args[1];
        FileInputStream fin = new FileInputStream(inputpath);
        Stack<Character> stack;
        stack = new Stack<>();
        int ch;
        @SuppressWarnings("unused")
        char c;
        boolean startwriting2 = false;
        String word = new String();

        while ((ch = fin.read()) != -1)
        {

            if ((char) ch == '[')
            {

                if (stack.isEmpty())
                {
                    startwriting2 = true;
                }
                stack.push((char) ch);
                word = word + (char) ch;
                continue;
            }

            if (startwriting2)
            {
                word = word + (char) ch;
            }

            if ((char) ch == ']')
            {
                c = stack.pop();
                if (stack.isEmpty())
                {
                    fin.close();
                    break;
                }

            }
        }

        JSONArray json = new JSONArray(word);
        String xmlstring = XML.toString(json);

        log.info(" XML  version ");
        xmlstring = xmlstring.replace("fields", "field");
        xmlstring = xmlstring.replace("<array>", "");
        xmlstring = xmlstring.replace("</array>", "");
        xmlstring = "<fieldslist>" + xmlstring + "</fieldslist>";

        String formattedXml = new XmlFormatter().format(xmlstring);
        log.info(formattedXml);
        FileWriter fw = new FileWriter(outputpath);

        fw.write(xmlstring);
        fw.close();
    }

}
