package com.teamgy.wakeonlan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.teamgy.wakeonlan.utils.Tools;

/**
 * Created by Jakov on 01/11/2015.
 */
public class EditPCActivity extends AppCompatActivity  {

    private EditText editMac;
    private EditText editSSID;
    private boolean editMode;
    private int positon;

    private AppCompatActivity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.add_new_pc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        int mode = bundle.getInt("mode");
        String macAdress = bundle.getString("macAdress");
        String ssid = bundle.getString("ssid");
        PCInfo pcinfo = new PCInfo(macAdress, ssid);

        editMac = (EditText) findViewById(R.id.edit_mac);
        editSSID = (EditText) findViewById(R.id.edit_ssid);
        //Log.d("d", "meme");

        if (mode == MainActivity.REQUEST_ADD) {

            getSupportActionBar().setTitle("Add New PC");
            //we are creating a new pc then
            //layout is fine since we have hints there
            editMode = false;
        } else {
            //its edit
            getSupportActionBar().setTitle("Edit PC");
            editMac.setText(pcinfo.getMacAdress());
            editSSID.setText(pcinfo.getPcName());
            positon = bundle.getInt("position");
            editMode = true;
            toolbar.setNavigationIcon(R.drawable.ic_delete_white_24dp);
        }
        initializeCircularAnimation();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setReturnTransition(null);
        }

    }


    private void initializeCircularAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds);

            getWindow().setSharedElementEnterTransition(transition);

            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    View v = findViewById(R.id.edit_pc_backgroud);
                    v.setVisibility(View.GONE);
                    //setting this drawable with 2 colors to transition between them after circualr reveal
                    v.setBackground(getDrawable(R.drawable.transition_drawable));

                    v = findViewById(R.id.app_bar_layout);
                    v.setVisibility(View.GONE);
                    v = findViewById(R.id.toolbar);
                    v.setVisibility(View.GONE);


                }

                @Override
                public void onTransitionEnd(Transition transition) {

                    View v = findViewById(R.id.edit_pc_backgroud);
                    v.setVisibility(View.VISIBLE);
                    Tools.backgroudTransition(v);
                    v = findViewById(R.id.toolbar);
                    Tools.circularRevealShow(v);
                    v = findViewById(R.id.app_bar_layout);
                    Tools.circularRevealShow(v);

                    //fading the color fast to white
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

    public void addOnPCInfoAddedListener(onPCInfoAddedListener lst) {
        onPCInfoAddedListener listener = lst;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("edit", "called it");
        if (item.getItemId() == R.id.menu_pc_check) {
            String mac = editMac.getText().toString();

            if (!Tools.isMacValid(mac)) {
                editMac.setError("Invalid MAC");
                editMac.requestFocus();
            } else {
                applyResult();
                finish();//very sketch, why do you do this android, just call the method in main :((
                return true;
            }

        }
        if (item.getItemId() == android.R.id.home) {

            if (editMode) {
                //in edit mode back button is a trash icon...
                Intent data = new Intent();
                data.putExtra("position", positon);
                setResult(MainActivity.RESULT_DELETE, data);
                finish();
            } else {
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyResult() {
        Intent data = new Intent();
        Log.d("", editMac.getText().toString());
        data.putExtra("macAdress", editMac.getText().toString());
        data.putExtra("ssid", editSSID.getText().toString());
        data.putExtra("position", positon); //TODO PLEASE CHANGE THIS ITS DUMB
        setResult(RESULT_OK, data);

    }

    @Override
    public void onBackPressed() {
        finish();

    }


    public interface onPCInfoAddedListener {

        void onPcInfoAdded(PCInfo pcInfo, boolean editMode);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_pc_activity, menu);
        return true;
    }


}
