package com.safee.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class AnimationsActivity extends AppCompatActivity {

    private CardView cardExpand;
    private ImageView imageCardExpand;
    private Scene sceneOrigin, sceneFinal;
    private CardView cardScene, cardLoadingPanel;
    private FrameLayout contentContainer;


    private Transition fadeTransition = new Fade();
    private boolean firstClick = true;


    private ViewGroup.LayoutParams originalParams;
    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);
        setUpSharedElementsTransition();

        makeFadeTransition();



    }
    private void crossFade(final View loadIn, View loadOut) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        loadOut.setAlpha(1f);
        loadOut.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        loadIn.setVisibility(View.VISIBLE);
        loadOut.animate()
                .alpha(0.3f)
                .setListener(null).start();


        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        loadIn.animate()
                .alpha(1f)
                .setListener(null
                ).start();
        loadIn.setFocusable(true);
        loadIn.setClickable(true);

    }

    private void makeFadeTransition() {
        cardScene = (CardView) findViewById(R.id.cardSceneRoot);

        sceneOrigin = Scene.getSceneForLayout(cardScene, R.layout.scene_card_origin, this);
        sceneFinal = Scene.getSceneForLayout(cardScene, R.layout.scene_card_final, this);
        cardScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstClick){
                    TransitionManager.go(sceneFinal, fadeTransition);
                    firstClick = false;
                }else{
                    TransitionManager.go(sceneOrigin, fadeTransition);
                    firstClick = true;
                }
            }
        });

    }



    private void setUpSharedElementsTransition() {
        cardExpand = (CardView) findViewById(R.id.cardExpand);
        imageCardExpand = (ImageView) findViewById(R.id.imageCardExpand);


        cardExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnimationsActivity.this,
                        TransitionActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(
                        AnimationsActivity.this,
                        imageCardExpand,
                        ViewCompat.getTransitionName(imageCardExpand));
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void collapseCardView() {
        cardExpand.setLayoutParams(originalParams);
        clicked = false;
    }

    private void expandCard() {
        clicked = true;
        // save original layout params
        originalParams = cardExpand.getLayoutParams();
        // set new params
        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        cardExpand.setLayoutParams(newParams);

    }

    public void explodeTransition(View view) {
        Intent i = new Intent(this, TransitionActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
    public void crossFadeButton(View view){
        cardLoadingPanel = (CardView) findViewById(R.id.cardLoadingPanel);
        contentContainer = (FrameLayout) findViewById(R.id.contentContainer);

        cardLoadingPanel.setAlpha(0f);
        contentContainer.animate().alpha(0.3f);
//        contentContainer.setForeground();
        cardLoadingPanel.setVisibility(View.VISIBLE);
        cardLoadingPanel.animate().alpha(1f);




//        crossFade(cardLoadingPanel, contentContainer);
    }
}
