package com.lostntkdgmail.workout;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lostntkdgmail.workout.database.LiftTableAccessor;

/**
 * The activity for Selecting a lift
 */
public class LiftSelection extends Activity {
    private static final String TAG = "LiftSelection";
    private LiftTableAccessor liftTable;
    private ListView liftList;

    /**
     * Creates the activity and sets up the data
     * @param savedInstanceState The last savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Launching Activity LiftSelection");
        setContentView(R.layout.lift_selection);
        TextView text = findViewById(R.id.selectLiftText);
        text.setText(getIntent().getStringExtra("TYPE"));
        liftTable = new LiftTableAccessor(this);
        setUpListView();

    }
    /**
     * Cleans up the Activity and closes the database accessors
     */
    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy() called for LiftSelection");
        liftTable.close();
        super.onDestroy();
    }
    /**
     * Sets up the list view which shows all of the different types
     */
    public void setUpListView() {
        String[] lifts = liftTable.getLifts(getIntent().getStringExtra("TYPE"));
        liftList = findViewById(R.id.liftList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item,R.id.listEntry,lifts);
        liftList.setAdapter(adapter);

        liftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Determines what happens when one of the Items is selected
             * @param adapterView The adapter view
             * @param view The ListView
             * @param position The position of the view in the adapter
             * @param id The row id of the selected item
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String lift = (String)liftList.getItemAtPosition(position);
                Log.d(TAG,"Selected: "+lift);
                Intent intent = new Intent(getBaseContext(),WeightSelection.class);
                intent.putExtra("LIFT",lift);
                intent.putExtra("TYPE",getIntent().getStringExtra("TYPE"));
                startActivity(intent);

            }
        });
    }
}