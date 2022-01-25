package efimov483.proj.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Map extends SurfaceView {
    private Bitmap bitmap;
    public int w = 720;
    public int h = 1440;
    public ArrayList<Point> points = new ArrayList<>();
    public float radius = 60f;
    public Paint paint = new Paint();
    public int[] pixels = new int[w * h];

    public Map(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(255, 255, 255, 0));
    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            update();
        }
    });

    public void update(){
        for(int y = 0; y < w; y++){
            for(int x = 0; x < h; x++){
                int c = Interpolation.calc(points, x, y);
                pixels[x + h * y] = Color.argb(255, c, c, c);
                //Log.e("test", "[" + x * h + ",\t" + y + "]");
                //bitmap.setPixel(x, y, Color.argb(255, c, c, c));
            }
        }

        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        update();

        canvas.drawBitmap(bitmap, 0, 0, paint);

        for(int i = 0; i < points.size(); i++){
            canvas.drawCircle(points.get(i).x, points.get(i).y, radius, paint);
        }
    }

}
