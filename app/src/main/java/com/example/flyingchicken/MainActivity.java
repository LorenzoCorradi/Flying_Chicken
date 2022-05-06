package com.example.flyingchicken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.gms.maps.MapFragment;

import java.util.concurrent.CompletionService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentManager fragmentManager=getSupportFragmentManager();
    PauseFragment pausefragment=new PauseFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm=new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH=dm.widthPixels;
        Constants.SCREEN_HEIGHT=dm.heightPixels;
        setContentView(R.layout.activity_main);
        ImageButton pause=(ImageButton) findViewById(R.id.pauseButton);
        pause.setOnClickListener(this);

        Constants.PAUSED=false;

    }

    public void resumePressed(){
        Constants.PAUSED=(!Constants.PAUSED);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.remove(pausefragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View view) {

        if(view==findViewById(R.id.pauseButton)){
            Constants.PAUSED=(!Constants.PAUSED);
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            if(Constants.PAUSED) {
                fragmentTransaction.add(R.id.fragmentContainerView2, pausefragment, "PauseFragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }else{
                fragmentTransaction.remove(pausefragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            }

            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    //exit app after back button pressed
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    System.exit(0);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        //pause if back button is pressed
        if(!Constants.PAUSED){
            Constants.PAUSED=(!Constants.PAUSED);
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerView2, pausefragment, "PauseFragment");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }
}