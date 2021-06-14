package com.tsyapa.rates.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.Nullable;

import com.tsyapa.rates.R;

public class RoundedImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final int FLAGS = Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG;

    private final Rect bounds = new Rect();

    private final Paint borderPaint = new Paint(FLAGS) {{
        setStyle(Style.STROKE);
        setColor(Color.WHITE);
    }};

    private float cornerRadius;
    private float outlineAlpha;

    private int borderSize;
    private int borderSizeHalf;
    private float aspectRatio;
    private boolean isCircular;

    public RoundedImageView(Context context) {
        super(context);
        init(null);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RoundedImageView);
        try {
            cornerRadius = typedArray.getDimension(R.styleable.RoundedImageView_cornersRadius, 0);
            isCircular = typedArray.getBoolean(R.styleable.RoundedImageView_circular, true);
            outlineAlpha = typedArray.getFloat(R.styleable.RoundedImageView_outlineAlpha, 1.0f);
            borderSize = typedArray.getDimensionPixelOffset(R.styleable.RoundedImageView_borderSize, 0);
            borderSizeHalf = borderSize / 2;
            borderPaint.setStrokeWidth(borderSize);
            borderPaint.setColor(typedArray.getColor(R.styleable.RoundedImageView_borderColor, Color.WHITE));
            aspectRatio = typedArray.getFloat(R.styleable.RoundedImageView_aspectRatio, 1);
        } finally {
            typedArray.recycle();
        }

        if (borderSize != 0) {
            setPaddingRelative(getPaddingStart() + borderSize,
                    getPaddingTop() + borderSize,
                    getPaddingEnd() + borderSize,
                    getPaddingBottom() + borderSize);
        }
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(bounds, cornerRadius);
                outline.setAlpha(outlineAlpha);
            }
        });
        setClipToOutline(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (aspectRatio != 1) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                    (int) (MeasureSpec.getSize(widthMeasureSpec) / aspectRatio));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(borderSize,
                borderSize,
                w - borderSize,
                h - borderSize);
        if (isCircular) {
            cornerRadius = w / 2;
        }
        invalidateOutline();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (borderSize != 0) {
            canvas.drawRoundRect(bounds.left + borderSizeHalf, bounds.top + borderSizeHalf,
                    bounds.right - borderSizeHalf, bounds.bottom - borderSizeHalf,
                    cornerRadius - borderSizeHalf,
                    cornerRadius - borderSizeHalf,
                    borderPaint);
        }
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        requestLayout();
    }
}