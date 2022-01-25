package efimov483.proj.Tools;

import android.graphics.Color;

import java.util.ArrayList;

public class Interpolation {
    public static int calc(ArrayList<Point> points, int x, int y){
        float dx = 0;
        float wx = 0;
        float eps = 0.00001f;

        for(int i = 0; i < points.size(); i++){
            float dx1 = points.get(i).x - x;
            float dy1 = points.get(i).y - y;
            float distance = (float) Math.sqrt(dx1 * dx1 + dy1 * dy1);
            float w = 1f / (float)Math.pow(distance, 2);
            wx += w;
            dx += w * points.get(i).w;
        }

        if(dx < eps) return 0;
        if(dx / wx > 1) return 255;
        int color = (int)(dx / wx * 256);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        return r | g | b;
    }
}
