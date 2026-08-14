package com.offlinew.practica.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;

public class Animations {

    public interface Callback {
        void onComplete();
    }

    public static void fadeInAnimation(View view){
        // Fade In Animation
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(600); // duration in milliseconds
        view.startAnimation(fadeIn);
    }


    public static void fadeOutAnimation(View view,int visibilityAfter){
        // Fade Out Animation
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(600); // duration in milliseconds


        if(visibilityAfter!=-1) {
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // Do something when animation starts
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Set the view to GONE or INVISIBLE after the animation ends
                    view.setVisibility(visibilityAfter); // or View.INVISIBLE
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Do something when animation repeats
                }
            });
        }

        view.startAnimation(fadeOut);

    }



    public static void performScaleAnimation(boolean isScaled, View view) {
        if (!isScaled) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.5f, 1f, 1.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(300);
            scaleAnimation.setRepeatMode(Animation.REVERSE);
            scaleAnimation.setRepeatCount(1);


            view.setVisibility(View.VISIBLE);
            view.startAnimation(scaleAnimation);
            view.setVisibility(View.INVISIBLE);
            //showToast("Love!");
            isScaled = true;
        } else {
            // Reset the scale and state
            view.setScaleX(1f);
            view.setScaleY(1f);
            isScaled = false;
        }
    }

    public static void performScaleToInfinityAnimation(View view) {

        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f,25f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f,25f);


        // Create ObjectAnimator for alpha change
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f,0f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(900);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set an AnimatorListener to perform actions when the animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ended, perform your actions here
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
            }
        });



        view.setVisibility(View.VISIBLE);
        animatorSet.start();

    }

    public static void performWrongAnswerAnimation(View view) {

        //animation 2nd part
        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator2 = ObjectAnimator.ofFloat(view, View.SCALE_X, 5f,3.5f);
        ObjectAnimator scaleYAnimator2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 5f,3.5f);


        // Create ObjectAnimator for alpha change
        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(view, View.ALPHA, 0.5f,1f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(scaleXAnimator2, scaleYAnimator2, alphaAnimator2);

        // Set duration and interpolator for the animation
        animatorSet2.setDuration(200);
        animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set an AnimatorListener to perform actions when the animation ends
        animatorSet2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ended, perform your actions here
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
            }
        });

        //anomator 1

        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f,5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f,5f);


        // Create ObjectAnimator for alpha change
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f,0.5f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set an AnimatorListener to perform actions when the animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ended, perform your actions here
                //view.setVisibility(View.GONE);
                animatorSet2.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
            }
        });



        view.setVisibility(View.VISIBLE);
        animatorSet.start();

    }

    //public static boolean isInitAnimationGoingOn = false;
    public static void performScaleToInfinityAnimationInitScreen(View view) {

        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f,25f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f,25f);


        // Create ObjectAnimator for alpha change
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f,0f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set an AnimatorListener to perform actions when the animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
                //isInitAnimationGoingOn = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ended, perform your actions here
                view.setVisibility(View.INVISIBLE);
                //isInitAnimationGoingOn = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
                //isInitAnimationGoingOn = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
                //isInitAnimationGoingOn = true;
            }
        });



        view.setVisibility(View.VISIBLE);
        animatorSet.start();

    }

    public static void performLikeAnimation(View view) {
        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1.5f,1.5f,0.3f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1.5f,1.5f,0.3f);

        // Create ObjectAnimator for rotation
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 180f,360f);

        // Create ObjectAnimator for alpha change
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.61f, 1f,0.61f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, rotationAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(900);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set an AnimatorListener to perform actions when the animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ended, perform your actions here
                    view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
            }
        });
        view.setVisibility(View.VISIBLE);
        // Start the animation
        animatorSet.start();


    }
    public static void performSideLikeAnimation(View view) {
        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.3f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.3f, 1f);

        // Create ObjectAnimator for rotation
        //ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f,5f,-5f,5f,-5f,0f);

        // Create ObjectAnimator for alpha change
        //ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.61,1f,1f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);//,rotationAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(900);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        // Start the animation
        animatorSet.start();
    }
    public static void performSideSaveAnimation(View view) {
        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.2f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.2f, 1f);

        scaleYAnimator.setRepeatMode(ValueAnimator.RESTART);
        scaleXAnimator.setRepeatMode(ValueAnimator.RESTART);
        // Create ObjectAnimator for rotation
        //ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f,5f,-5f,5f,-5f,0f);

        // Create ObjectAnimator for alpha change
        //ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.61,1f,1f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);//,rotationAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        // Start the animation
        animatorSet.start();
    }
    public static void performCustomAnimation(View view) {
        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.5f, 1.5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.5f, 1.5f);

        // Create ObjectAnimator for rotation
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f);

        // Create ObjectAnimator for alpha change
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, rotationAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        // Start the animation
        animatorSet.start();
    }

    public static void performScaleUpAndFadeAwayAnimation(View view){

        // Create ObjectAnimator for scaling
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f,1.5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f,1.5f);


        // Create ObjectAnimator for alpha change
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f,0f);
        //alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Combine all animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);

        // Set duration and interpolator for the animation
        animatorSet.setDuration(600);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set an AnimatorListener to perform actions when the animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ended, perform your actions here
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
            }
        });

        view.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    public static void animateUpload(View view, float scaleFactor, float translateX, float translateY, long duration,Callback callback) {
        // Scale the view smaller
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, scaleFactor);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, scaleFactor);

        // Move the view upward diagonally
        ObjectAnimator translateXAnim = ObjectAnimator.ofFloat(view, "translationX", 0f, translateX);
        ObjectAnimator translateYAnim = ObjectAnimator.ofFloat(view, "translationY", 0f, -translateY);

        // Combine all animations in an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, translateXAnim, translateYAnim);
        animatorSet.setDuration(duration);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                view.setScaleX(1f);
                view.setScaleY(1f);
                view.setTranslationX(0f);
                view.setTranslationY(0f);

                if (callback != null) {
                    callback.onComplete();
                }
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {
                view.setScaleX(1f);
                view.setScaleY(1f);
                view.setTranslationX(0f);
                view.setTranslationY(0f);

                if (callback != null) {
                    callback.onComplete();
                }
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });

        // Start the animation
        animatorSet.start();
    }

}
