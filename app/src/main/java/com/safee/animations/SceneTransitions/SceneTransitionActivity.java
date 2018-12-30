package com.safee.animations.SceneTransitions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;

import com.safee.animations.R;

public class SceneTransitionActivity extends AppCompatActivity {

    private Scene sceneOrigin, sceneFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);


    }
}
