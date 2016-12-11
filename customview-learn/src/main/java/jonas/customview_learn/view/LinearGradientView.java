package jonas.customview_learn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

/**
 * @author yun.
 * @date 2016/11/27
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class LinearGradientView extends View implements Checkable{
    private int mW;
    private int mH;
    private Paint mPaint;
    private LinearGradient mProgTextLinearGradient;
    private int[] mTextColors = new int[]{Color.GREEN,Color.RED,Color.RED,Color.GREEN};
    private Matrix mMatrix;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public LinearGradientView(Context context){
        super(context);
    }

    public LinearGradientView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
        mProgTextLinearGradient = new LinearGradient(0, 0, mW, 0, mTextColors,
                new float[]{0f, 0.001f,1/3f,1/3f+0.0000001f},
                Shader.TileMode.CLAMP);
        mPaint.setShader(mProgTextLinearGradient);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect(0,0,mW,mH,mPaint);
    }

    public void setMove(float move){
        mMatrix.setTranslate(move*mW,0);
        mProgTextLinearGradient.setLocalMatrix(mMatrix);
        postInvalidate();
    }

    @Override
    public void setChecked(boolean checked){

    }

    @Override
    public boolean isChecked(){
        return false;
    }

    @Override
    public void toggle(){

    }
}
