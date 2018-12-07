package com.cumulations.customviewapp.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

    private Paint paint;
    private Paint mPaintFace;
    private Paint mPaintCap;
    private Paint mPaintEye;
    private Paint mPaintBowtie;
    private Path mouthPath;
    private Path capPath;
    private float cX, cY, cR;


    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFace = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCap = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintEye = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBowtie = new Paint(Paint.ANTI_ALIAS_FLAG);
        mouthPath = new Path();
        capPath = new Path();
        mPaintFace.setColor(Color.YELLOW);
        cR = 100;
    }

    public void swapColor() {
        mPaintFace.setColor(mPaintFace.getColor() == Color.YELLOW ? Color.RED : Color.YELLOW);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //fix
        drawBlackBoard(canvas);

        if (cX == 0f || cY == 0f) {
            cX = getWidth()/ 2;
            cY = getHeight() / 2;
        }

        //movable
        drawFaceBackground(canvas, cX, cY, cR);
        drawCap(canvas, cX, cY);
        drawEyes(canvas, cX, cY);
        drawMouth(canvas, cX, cY);
        drawBowtie(canvas, cX, cY);
    }

    private void drawBlackBoard(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        canvas.drawPaint(paint);
    }

    private void drawFaceBackground(Canvas canvas, float x, float y, float radius) {

        mPaintFace.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, mPaintFace);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(x, y, radius, paint);
    }

    private void drawCap(Canvas canvas, float x, float y) {
        //triangle
        capPath.reset();
        capPath.setFillType(Path.FillType.WINDING);
        capPath.moveTo(x - 50, y - 90);
        capPath.lineTo(x + 50, y - 90);
        capPath.lineTo(x, y - 150);
        capPath.lineTo(x - 50, y - 90);
        capPath.close();
        mPaintCap.setStrokeWidth(10);
        mPaintCap.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintCap.setColor(Color.WHITE);
        canvas.drawPath(capPath, mPaintCap);
    }

    private void drawEyes(Canvas canvas, float x, float y) {
        mPaintEye.setStyle(Paint.Style.FILL);
        mPaintEye.setColor(Color.BLACK);
        RectF leftEyeRect = new RectF(x - 70, y - 70, x - 25, y - 25);
        canvas.drawOval(leftEyeRect, mPaintEye);
        RectF rightEyeRect = new RectF(x + 25, y - 70, x + 70, y - 25);
        canvas.drawOval(rightEyeRect, mPaintEye);
    }

    private void drawMouth(Canvas canvas, float x, float y) {
        mouthPath.reset();
        mouthPath.moveTo(x - 60, y + 40);

        if (mPaintFace.getColor() == Color.YELLOW) {
            mouthPath.quadTo(x, y + 50, x + 60, y + 40);
            mouthPath.quadTo(x, y + 70, x - 60, y + 40);
//            Toast.makeText(getContext(), "Happy", Toast.LENGTH_SHORT).show();
        } else if (mPaintFace.getColor() == Color.RED) {
            mouthPath.quadTo(x, y + 10, x + 60, y + 40);
            mouthPath.quadTo(x, y + 30, x - 60, y + 40);
//            Toast.makeText(getContext(), "Sad", Toast.LENGTH_SHORT).show();
        }

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mouthPath, paint);
    }

    private void drawBowtie(Canvas canvas, float x, float y) {
        mPaintBowtie.setStyle(Paint.Style.FILL);
        mPaintBowtie.setColor(Color.WHITE);
        canvas.drawRect(x - 20, y + 80, x + 20, y + 120, mPaintBowtie);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawRect(x - 10, y + 90, x + 10, y + 110, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getWidth(), getHeight());
        setMeasuredDimension(size, size);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                float xPos = event.getX();
                float yPos = event.getY();

                double dx = Math.pow(xPos - cX, 2);
                double dy = Math.pow(yPos - cY, 2);

                if (dx + dy < Math.pow(cR, 2)) {
                    //touched
                    cX = xPos;
                    cY = yPos;

                    postInvalidate();
                    return true;
                }

                return value;
            }
            case MotionEvent.ACTION_UP:{

                return true;
            }
        }

        return value;
    }

}
