package org.opengeo.data.importer.transform;

import java.io.Serializable;

/**
 * Transformation to apply at some stage of the import.
 * 
 * @author Justin Deoliveira, OpenGeo
 */
public interface ImportTransform extends Serializable {

    /**
     * Should this transform stop on an error.
     * @todo example of why it shouldn't?
     * @param e The error in question
     * @return true if processing should stop, false otherwise
     */
    boolean stopOnError(Exception e);
    
    /**
     * Initialize any transient or temporary state.
     * This should be called prior to invoking any other methods on the transform.
     */
    void init();
}
