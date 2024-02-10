package com.chameapps.fouronthefarm.animation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.widget.ImageView;

import com.chameapps.fouronthefarm.R;

public class FrameAnimation extends AnimationDrawable{
    private ImageView imageView;
    private Context context;
    private Handler mAnimationHandler;
    private FrameAnimationCallback callback;
    private int row;
    private int col;
    private int currentBall;
    
    public FrameAnimation(Context context, ImageView imageView, int row, int col, int currentBall){
    	super();
    	this.context = context;
    	this.imageView = imageView;
    	this.row = row;
    	this.col = col;
    	this.currentBall = currentBall;
    	startAnimation();
    }
    
    public void setAnimationCallback(FrameAnimationCallback callback){
    	this.callback = callback;
    }

	private void startAnimation(){
		if(currentBall == R.drawable.tomato){
          addFrame(context.getResources().getDrawable(R.drawable.tomato_one), 100);
          addFrame(context.getResources().getDrawable(R.drawable.tomato_two), 100);
          addFrame(context.getResources().getDrawable(R.drawable.tomato_three), 100);
          addFrame(context.getResources().getDrawable(R.drawable.tomato_four), 100);
          addFrame(context.getResources().getDrawable(R.drawable.tomato_five), 100);
          addFrame(context.getResources().getDrawable(R.drawable.tomato_six), 100);
          addFrame(context.getResources().getDrawable(R.drawable.tomato_seven), 100);
          addFrame(context.getResources().getDrawable(R.drawable.tomato_eight), 100);
		}
		else{
			addFrame(context.getResources().getDrawable(R.drawable.pumpkin_one), 100);
	        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_two), 100);
	        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_three), 100);
	        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_four), 100);
	        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_five), 100);
	        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_six), 100);
	        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_seven), 100);
	        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_eight), 100);
		}
        setOneShot(true);
         
        imageView.setImageDrawable(this);
        imageView.post(new Starter());
      }
      
      class Starter implements Runnable {
          public void run() {
               start();
           }
       }
      
      // Get when animation finished
      @Override
      public void start() {
          super.start();
          /*
           * Call super.start() to call the base class start animation method.
           * Then add a handler to call onAnimationFinish() when the total
           * duration for the animation has passed
           */
          mAnimationHandler = new Handler();
          mAnimationHandler.postDelayed(new Runnable() {

              public void run() {
                  onAnimationFinish();
              }
          }, getTotalDuration());

      }

      /**
       * Gets the total duration of all frames.
       * 
       * @return The total duration.
       */
      public int getTotalDuration() {

          int iDuration = 0;

          for (int i = 0; i < this.getNumberOfFrames(); i++) {
              iDuration += this.getDuration(i);
          }

          return iDuration;
      }

      private void onAnimationFinish(){
    	  callback.onFrameAnimationEnd(row, col);
      }
   }
