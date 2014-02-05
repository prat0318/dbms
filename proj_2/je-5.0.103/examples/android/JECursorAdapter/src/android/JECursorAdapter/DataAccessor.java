/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002, 2014 Oracle and/or its affiliates.  All rights reserved.
 *
 * $Id$
 */
 
package android.JECursorAdapter;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class DataAccessor {
    public PrimaryIndex<Integer, Data> dataByKey;
    
    public DataAccessor(EntityStore store)
        throws DatabaseException {
        
        /* Primary key for Data classes. */
        dataByKey = store.getPrimaryIndex(Integer.class, Data.class);
    }
}
