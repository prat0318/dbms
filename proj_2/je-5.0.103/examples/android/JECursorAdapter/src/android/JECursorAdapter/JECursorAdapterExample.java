/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002, 2014 Oracle and/or its affiliates.  All rights reserved.
 *
 * $Id$
 */
 
package android.JECursorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class JECursorAdapterExample extends ListActivity {
    private final int RECORDNUM = 5000;
    private final int KEYRANGEMIN = 0;
    private final int KEYRANGEMAX = 4999;
    private final String DBPATH = "data/tmp/JECursorAdapterDemo";
    
    private File myDbEnvPath = new File(DBPATH);
    private DataAccessor da;
    private static MyDbEnv myDbEnv = new MyDbEnv();
    private EntityCursor<Data> valueCursor;
    private EntityCursor<Integer> keyCursor;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myDbEnv.setup(myDbEnvPath, false /*read-only*/);  
        da = new DataAccessor(myDbEnv.getEntityStore());
        
        /* If there is no data, then load the data into JE DB. */
        if (da.dataByKey.count() == 0) {
            LoadJEData();
        }
        ReadJEDataFromCursor();
        setListAdapter(new JECursorAdapter<Integer, Data>
                       (this, android.R.layout.simple_list_item_1, 
                        keyCursor, valueCursor, da.dataByKey));
                       
        /* Jump to a specific position. */
        final Button buttonJump = (Button) findViewById(R.id.jump);
        buttonJump.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    final EditText editText =
                        (EditText) findViewById(R.id.position);
                    int position = Integer.
                        valueOf(editText.getText().toString());
                    position = position < KEYRANGEMIN ? 
                               KEYRANGEMIN : 
                               position;
                    position = position > KEYRANGEMAX ? 
                               KEYRANGEMAX : 
                               position;
                    getListView().setSelection(position);
                }
            });
        
        /* Jump to the beginning of the database. */
        final Button buttonJumpStart = (Button) findViewById(R.id.jump_start);
        buttonJumpStart.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    getListView().setSelection(KEYRANGEMIN);
                }
            });
        
        /* Jump to the end of the database. */
        final Button buttonJumpEnd = (Button) findViewById(R.id.jump_end);
        buttonJumpEnd.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    getListView().setSelection(KEYRANGEMAX);
                }
            });
    }
    
    private void LoadJEData() 
        throws DatabaseException {
        
        Data theData = new Data();
        try {
            for (int i = 0; i < RECORDNUM; i++) {
                theData.setKey(i);
                theData.setData("Record " + i);
                da.dataByKey.put(theData);
            }
        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }
    }
    
    private void ReadJEDataFromCursor() 
        throws DatabaseException {
        
        valueCursor = 
            da.dataByKey.entities(KEYRANGEMIN, true, KEYRANGEMAX, true);
        CursorConfig cursorCon = new CursorConfig();
        cursorCon.setReadUncommitted(true);
        keyCursor = da.dataByKey.keys
            (null, KEYRANGEMIN, true, KEYRANGEMAX, true, cursorCon);
    }
}