package org.cneng.dom4j;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-23
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates
 */
public class WritingDocumentToFile {
    public void write(Document document) throws IOException {

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter( "output.dom4j" ));
        writer.write( document );
        writer.close();

        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter( System.out, format );
        writer.write( document );

        // Compact format to System.out
        format = OutputFormat.createCompactFormat();
        writer = new XMLWriter( System.out, format );
        writer.write( document );
    }
}
