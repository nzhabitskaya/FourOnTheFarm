package com.chameapps.fouronthefarm.animation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.widget.ImageView;

import com.chameapps.fouronthefarm.R;

public class ShineFrameAnimation extends AnimationDrawable{
    private ImageView imageView;
    private Context context;
    private Handler mAnimationHandler;
    private FrameAnimationCallback callback;
    
    public ShineFrameAnimation(Context context, ImageView imageView, int currentBall){
    	super();
    	this.context = context;
    	this.imageView = imageView;
    	startAnimation(currentBall);
    }
    
    public void setAnimationCallback(FrameAnimationCallback callback){
    	this.callback = callback;
    }

	private void startAnimation(int currentBall){		
		for(int i = 0; i < 100; i++){
			if(currentBall == R.drawable.tomato){
				addFrame(context.getResources().getDrawable(R.drawable.tomato_shine1), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.tomato_shine2), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.tomato_shine3), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.tomato_shine4), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.tomato_shine3), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.tomato_shine2), 150);
		        
			}else{
				addFrame(context.getResources().getDrawable(R.drawable.pumpkin_shine1), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_shine2), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_shine3), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_shine4), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_shine3), 150);
		        addFrame(context.getResources().getDrawable(R.drawable.pumpkin_shine2), 150);
			}
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
                  //onAnimationFinish();
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
    	  //callback.onAnimationEnd(row, col);
      }
   }