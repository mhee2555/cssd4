package com.phc.core.string;

        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Typeface;

        import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class TextAsBitmap {

    public static Bitmap getTextBitmap(String text, float textSize) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.WHITE);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.create("Tahoma", Typeface.BOLD));
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 2.5f); // round
        int height = (int) (baseline + paint.descent() + 6.5f);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRect(0, 0 ,width+0.5f , 100, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, 0, baseline+3.5f, paint);
        return image;

    }

    public static Bitmap getTextBitmap1(String text, float textSize) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.WHITE);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.create("Tahoma", Typeface.NORMAL));
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 2.5f); // round
        int height = (int) (baseline + paint.descent() + 6.5f);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRect(0, 0 ,width+0.5f , 100, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, 0, baseline+3.5f, paint);
        return image;

    }
}
