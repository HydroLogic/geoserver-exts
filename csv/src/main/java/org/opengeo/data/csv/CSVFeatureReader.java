package org.opengeo.data.csv;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.geotools.data.FeatureReader;
import org.geotools.data.Query;
import org.opengeo.data.csv.parse.CSVIterator;
import org.opengeo.data.csv.parse.CSVStrategy;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class CSVFeatureReader implements FeatureReader<SimpleFeatureType, SimpleFeature> {

    private SimpleFeatureType featureType;

    private CSVIterator iterator;

    public CSVFeatureReader(CSVStrategy csvStrategy) throws IOException {
        this(csvStrategy, Query.ALL);
    }

    public CSVFeatureReader(CSVStrategy csvStrategy, Query query)
            throws IOException {
        this.featureType = csvStrategy.getFeatureType();
        this.iterator = csvStrategy.iterator();
    }

    @Override
    public SimpleFeatureType getFeatureType() {
        return featureType;
    }

    @Override
    public void close() throws IOException {
        iterator.close();
    }

    @Override
    public SimpleFeature next() throws IOException, IllegalArgumentException,
            NoSuchElementException {
        return iterator.next();
    }

    @Override
    public boolean hasNext() throws IOException {
        return iterator.hasNext();
    }

}
