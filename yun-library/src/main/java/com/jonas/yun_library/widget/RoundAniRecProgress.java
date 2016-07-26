package com.jonas.yun_library.widget;

import java.util.Random;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

@SuppressLint("DrawAllocation")
public class RoundAniRecProgress extends View {

	private Paint mPaint;
	private int width;
	private int height;
	private int mini;
	/**
	 * 圆角矩形的 颜色
	 */
	private int recColor = Color.TRANSPARENT;
	private float recright;
	private float recbottom;
	private float rx = Integer.MIN_VALUE;
	private float ry = Integer.MIN_VALUE;
	private float left;
	private float top;
	private long ANI_TIME = 1500;
	private int progBackColor = Color.GRAY;
	private float maxProgress = 100;
	private float currProgress;
	private int max;
	
	/**
	 * AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
	 * 
	 * AccelerateInterpolator 在动画开始的地方速率改变比较慢，然后开始加速
	 * 
	 * AnticipateInterpolator 开始的时候向后然后向前甩
	 * 
	 * AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
	 * 
	 * BounceInterpolator 动画结束的时候弹起
	 * 
	 * CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
	 * 
	 * DecelerateInterpolator 在动画开始的地方快然后慢
	 * 
	 * LinearInterpolator 以常量速率改变
	 * 
	 * OvershootInterpolator 向前甩一定值后再回到原来位置
	 */
//	private AccelerateInterpolator interpolator =  new AccelerateInterpolator(1);;
	private TimeInterpolator  interpolator =  new BounceInterpolator();
	private OnCurrentProgressListener l;
	/**
	 * 控件是否已经显示
	 */
	private boolean sized_Showed;;

	public RoundAniRecProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	public RoundAniRecProgress(Context context) {
		super(context);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	/**
	 * 此时 获取控件的 宽高
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = getWidth();
		height = getHeight();
		mini = width > height ? height : width;
		max = width < height ? height : width;
		if (rx == Integer.MIN_VALUE || ry == Integer.MIN_VALUE) {
			ry = rx = mini / 5*2;
		}
		// 默认显示 注释掉的画 默认不显示任何
		if (recbottom == 0 || recright == 0) {
//			recbottom = recright = mini;
		}
		//初始化 进度条颜色
		if (Color.TRANSPARENT == recColor) {
			//默认颜色 随机
			recColor = setEachPieColor();
		}
		if (currProgress!=0) {
			float ratio = currProgress / maxProgress;
			setAniRecChange(0, ratio * max);
		}
		sized_Showed = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mPaint.setColor(progBackColor );
		RectF rect1 = new RectF(0, 0, getWidth(), getHeight());
		canvas.drawRoundRect(rect1, rx, ry, mPaint);
		mPaint.setColor(recColor);
		
		//清除float的误差
		if (width>height&&recright>mini) {
			top = left = 0;
			recbottom = mini;
		}else{
			if (recbottom>mini) {
				recright = mini;
				top = left = 0;
			}
		}
		RectF rect = new RectF(left, top, recright, recbottom);
		canvas.drawRoundRect(rect, rx, ry, mPaint);

	}
	public int setEachPieColor() {
			// 随机颜色
			Random random = new Random();
			int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
			// int nextInt = random.nextInt(16777216) + 1;
			// String hexString = Integer.toHexString(-nextInt);
			// int ranColor = Color.parseColor("#" + hexString);
			return ranColor;
		}

	/**
	 * AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
	 * 
	 * AccelerateInterpolator 在动画开始的地方速率改变比较慢，然后开始加速
	 * 
	 * AnticipateInterpolator 开始的时候向后然后向前甩
	 * 
	 * AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
	 * 
	 * BounceInterpolator 动画结束的时候弹起
	 * 
	 * CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
	 * 
	 * DecelerateInterpolator 在动画开始的地方快然后慢
	 * 
	 * LinearInterpolator 以常量速率改变
	 * 
	 * OvershootInterpolator 向前甩一定值后再回到原来位置
	 */
	public void setAniRecChange(float start, float change) {
		long showTime = start == change?0:ANI_TIME;
		if (mini < width) {
			// 宽更大 则向右变长
			ValueAnimator animator = ValueAnimator.ofFloat(start, change)
					.setDuration(showTime);
			
			animator.setInterpolator(interpolator);
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					recright = (float) animation.getAnimatedValue();
					if (recright < mini) {
						top = mini * 1f / 2 - recright / 2;
						top = top < 0 ? 0 : top;
						recbottom = recright / 2 + mini * 1f / 2;
						recbottom = recbottom > mini ? mini : recbottom;
					}
					if (l!=null) {
						l.onCurrentProgress(recright);
					}
					postInvalidate();
				}
			});
			animator.start();
		} else {
			// 高更大 则向下变长
			ValueAnimator animator = ValueAnimator.ofFloat(start, change)
					.setDuration(showTime);
			animator.setInterpolator(interpolator);
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					recbottom = (float) animation.getAnimatedValue();
					if (recbottom < mini) {
						left = mini * 1f / 2 - recbottom / 2;
						left = left < 0 ? 0 : left;
						recright = recbottom / 2 + mini * 1f / 2;
						recright = recright > mini ? mini : recright;
					}
					if (l!=null) {
						l.onCurrentProgress(recbottom);
					}
					postInvalidate();
				}
			});
			animator.start();
		}
	}

	public int getRecColor() {
		return recColor;
	}

	/**
	 * 设置进度条的颜色
	 * 默认 随机
	 * 设置为透明的话 颜色也将变为随机
	 * @param recColor
	 */
	public void setRecColor(int recColor) {
		this.recColor = recColor;
		postInvalidate();
	}

	public long getANI_TIME() {
		return ANI_TIME;
	}

	/**
	 * 设置动画显示的时长
	 * @param ANI_TIME
	 */
	public void setANI_TIME(long ANI_TIME) {
		this.ANI_TIME = ANI_TIME;
		postInvalidate();
	}

	public int getProgBackColor() {
		return progBackColor;
	}

	/**
	 * 设置进度条的 总长 背景颜色
	 * @param progBackColor
	 */
	public void setProgBackColor(int progBackColor) {
		this.progBackColor = progBackColor;
		postInvalidate();
	}

	public float getMaxProgress() {
		return maxProgress;
	}

	/**
	 * 默认 maxProgress 为100
	 * @param maxProgress
	 */
	public void setMaxProgress(float maxProgress) {
		this.maxProgress = maxProgress;
	}

	public float getCurrProgress() {
		return currProgress;
	}

	/**
	 * 设置进度条进度 无动画===== (控件显示的时候 和显示之后后设置都有效)===
	 * 默认 maxProgress 为100
	 * 
	 * @param currentProgress
	 */
	public void setCurrProgress(float currentProgress) {
		this.currProgress = currentProgress;
		float ratio = currProgress / maxProgress;
		setAniRecChange(ratio * max, ratio * max);
	}
	/**
	 * 设置进度条进度 动画===== (控件显示的时候 和显示之后后设置都有效)===
	 * 默认 maxProgress 为100
	 * 
	 * @param currentProgress
	 */
	public void setAniCurrProgress(float currentProgress) {
		this.currProgress = currentProgress;
		//控件已经显示
		if (sized_Showed) {
			float ratio = currProgress / maxProgress;
			setAniRecChange(0, ratio * max);
		}
	}
	/**
	 * 设置进度条进度(指定起点到终点) 动画====== (控件显示的时候 和显示之后后设置都有效)====
	 * 默认 maxProgress 为100
	 * 
	 * @param currentProgress
	 */
	public void setAniCurrProgress(float lastProgress,float currentProgress) {
		this.currProgress = currentProgress;
		if (sized_Showed) {
			float ratio = currProgress / maxProgress;
			setAniRecChange(lastProgress/maxProgress*max, ratio * max);
		}
	}

	public float getRx() {
		return rx;
	}

	/**
	 * 设置 圆角矩形 圆角半径
	 * @param ry
	 */
	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	/**
	 * 设置 圆角矩形 圆角半径
	 * @param ry
	 */
	public void setRy(float ry) {
		this.ry = ry;
	}
	
	public interface OnCurrentProgressListener{
		void onCurrentProgress(float current);
	}
	public void setOnCurrentProgressListener(OnCurrentProgressListener l){
		this.l = l;
	}

}
