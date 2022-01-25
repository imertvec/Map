package efimov483.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import efimov483.proj.Tools.Map;
import efimov483.proj.Tools.Point;

public class MainActivity extends AppCompatActivity {
    Map map;
    float lastX = 0;
    float lastY = 0;
    int currentPoint = 0;
    ArrayList<Point> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        map = findViewById(R.id.pixelComponent);
        map.setOnTouchListener(this::onTouch);

        points = new ArrayList<>();

        for(int i = 0; i < 5; i++){
             Point p = new Point(
                    (float)Math.random() * 720,
                    (float)Math.random() * 1449,
                    (float)Math.random()
            );
            points.add(p);
        }
        map.points = points;
        map.invalidate();
    }

    public boolean onTouch(View v, MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for(int i = 0; i < points.size(); i++){
                    float dx = event.getX() - points.get(i).x;
                    float dy = event.getY() - points.get(i).y;

                    if(dx * dx + dy * dy <= map.radius * map.radius){
                        //Log.e("motion", "[" + event.getX() + ", " + event.getY() + "]");
                        lastX = event.getX();
                        lastY = event.getY();

                        if(currentPoint == -1)
                            currentPoint = i;
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(currentPoint != -1){
                    points.get(currentPoint).x = event.getX();
                    points.get(currentPoint).y = event.getY();

                    map.points = points;
                    map.invalidate();
                }

                break;
                case MotionEvent.ACTION_UP:
                    currentPoint = -1;
                    break;
        }
        return true;
    }
}
