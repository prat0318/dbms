/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002, 2014 Oracle and/or its affiliates.  All rights reserved.
 *
 * $Id$
 */
 
package android.JECursorAdapter;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityIndex;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JECursorAdapter<K, E> extends BaseAdapter {
    private Context context;
    private int fieldId = 0;
    private int resource;
    private LayoutInflater inflater;
    private EntityCursor<E> valueCursor;
    private EntityCursor<K> keyCursor;
    private int itemsCount;
    private EntityIndex<K, E> primaryIndex;
    private int currentCursorPosition;
    
    public JECursorAdapter(Context context, 
                           int textViewResourceId, 
                           EntityCursor<K> keyCursor,
                           EntityCursor<E> valueCursor,
                           EntityIndex<K, E> primaryIndex) {

        this.context = context;
        this.inflater = (LayoutInflater)
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = textViewResourceId;
        this.fieldId = 0;
        this.valueCursor = valueCursor;
        this.valueCursor.first();
        this.currentCursorPosition = 0;
        this.keyCursor = keyCursor;
        this.keyCursor.first();
        this.primaryIndex = primaryIndex;
        this.itemsCount = -1;
    }
    
    public Context getContext() {
        return context;
    }
    
    /* 
     * Return the total number of records pointing by the cursor. Use 
     * dirty-read key-only scan to calculate the result. The result only need
     * to be calculated once.
     */
    public int getCount() {
        if (itemsCount < 0) {
            int count = 0;
            keyCursor.first();
            for (K item : keyCursor) {
                count++;
            }
            keyCursor.first();     
            itemsCount = count;
        }
        return itemsCount;
    }
    
    /* 
     * Use dirty-read key-only scan and the shortest jump distance to get the 
     * item given a specific position.
     */    
    public E getItem(int position) {
        /* The distance from the destination to the begining. */
        int toFirst = position;
        
        /* The distance from the destination to the end. */
        int toLast = itemsCount - position;
        
        /* The distance from the destination to the current position. */
        int toCurrent = Math.abs(position - currentCursorPosition);
        
        /* Case 1: jumping to the destination from the current position. */
        if (toCurrent <= toFirst && toCurrent <= toLast) {
            /* If the destination is after the current position. */
            if (currentCursorPosition <= position) {
                for (K key = keyCursor.current(); 
                     key != null; 
                     key = keyCursor.next()) {
                    if (currentCursorPosition == position) {
                        E item = primaryIndex.get(key);
                        return item;
                    }
                    currentCursorPosition++;
                }
            } else {
                /* If the destination is before the current position. */
                for (K key = keyCursor.current(); 
                     currentCursorPosition >= 0; 
                     key = keyCursor.prev()) {
                    if (currentCursorPosition == position) {
                        E item = primaryIndex.get(key);
                        return item;
                    }
                    currentCursorPosition--;
                }
            }
        }
        
        /* Case 2: jumping to the destination from the begining position. */
        if (toFirst <= toCurrent && toFirst <= toLast) {
            currentCursorPosition = 0;
            for (K key = keyCursor.first(); 
                 key != null; 
                 key = keyCursor.next()) {
                if (currentCursorPosition == position) {
                    E item = primaryIndex.get(key);
                    return item;
                }
                currentCursorPosition++;
            }
        }
        
        /* Case 3: jumping to the destination from the end position. */
        if (toLast <= toCurrent && toLast <= toFirst) {
            currentCursorPosition = itemsCount - 1;
            for (K key = keyCursor.last(); 
                 currentCursorPosition >= 0; 
                 key = keyCursor.prev()) {
                if (currentCursorPosition == position) {
                    E item = primaryIndex.get(key);
                    return item;
                }
                currentCursorPosition--;
            }
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFroresource(position, convertView, parent, resource);
    }
    
    private View createViewFroresource(int position, 
                                       View convertView, 
                                       ViewGroup parent,
                                       int resource) {
        View view;
        TextView text;
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }
        
        try {
            if (fieldId == 0) {
            
                /* 
                 * If no custom field is assigned, assume the whole resource is 
                 * a TextView.
                 */
                text = (TextView) view;
            } else {
                /* Otherwise, find the TextView field within the layout. */
                text = (TextView) view.findViewById(fieldId);
            }
        } catch (ClassCastException e) {
            Log.e("JECursorAdapter", 
                  "You must supply a resource ID for a TextView");
            throw new IllegalStateException
                ("JECursorAdapter requires the resource ID to be a TextView", 
                 e);
        }
        
        E item = getItem(position);
        if (item instanceof CharSequence) {
            text.setText((CharSequence) item);
        } else {
            text.setText(item.toString());
        }
        return view;
    }
}
