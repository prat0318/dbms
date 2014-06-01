/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002, 2014 Oracle and/or its affiliates.  All rights reserved.
 *
 * $Id$
 */
 
package android.JECursorAdapter;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class Data {
    @PrimaryKey
    private Integer key;
    
    private String data;
    
    public void setKey(Integer key) {
        this.key = key;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public Integer getKey() {
        return key;
    }

    public String getData() {
        return data;
    }
    
    public String toString() {
        return new String(String.valueOf(key) + ". " + data);
    }
}
