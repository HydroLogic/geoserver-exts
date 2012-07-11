package org.geoserver.printng.resource;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.geoserver.printng.io.PrintngReader;
import org.geoserver.printng.io.PrintngReaderFactory;
import org.geoserver.printng.io.PrintngWriter;
import org.geoserver.printng.io.PrintngWriterFactory;
import org.geoserver.rest.RestletException;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.Variant;
import org.w3c.dom.Document;

public class PrintResource extends Resource {
    private final PrintngReaderFactory readerFactory;

    private final PrintngWriterFactory writerFactory;

    public PrintResource(Request request, Response response, Variant variant,
            PrintngReaderFactory readerFactory, PrintngWriterFactory writerFactory) {
        super(null, request, response);
        this.readerFactory = readerFactory;
        this.writerFactory = writerFactory;
        List<Variant> allVariants = getVariants();
        allVariants.add(variant);
    }

    @Override
    public boolean allowGet() {
        return false;
    }

    @Override
    public boolean allowPost() {
        return true;
    }

    @Override
    public void handlePost() {
        getResponse().setEntity(getRepresentation(getPreferredVariant()));
    }

    @Override
    public Representation getRepresentation(Variant variant) {
        PrintngReader printngReader = readerFactory.printngReader(getRequest());
        Document document;
        try {
            Reader reader = printngReader.reader();
            PrintngRestDocumentParser documentParser = new PrintngRestDocumentParser(reader);
            document = documentParser.parse();
        } catch (IOException e) {
            throw new RestletException("Error reading input", Status.SERVER_ERROR_INTERNAL, e);
        }
        PrintngWriter writer = writerFactory.printngWriter(document);
        return new PrintRepresentation(variant.getMediaType(), writer);
    }
}
