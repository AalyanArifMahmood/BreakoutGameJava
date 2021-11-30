package breakout;
import Graphics.*;
import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class is responsible for creating the heart shape.
 */
public class Lives 
{
    private static Ellipse heartTop;
    private static Ellipse heartTop2;
    private static Path heartBottom;
    private static GraphicsGroup heart;
    private static final Color
        HEART_COLOR = new Color(255,0,0);


    /**
    * This method is responsible for creating the actual graphicsObject heart using ellipses and a Path triangle
    * @param height, this is the diameter of the ellipses used to make the heart and is also used in the construction of the 
    * triangle.
    */
    public static GraphicsGroup MakeHeart(double height)
    {
        heart = new GraphicsGroup();
        heartTop = new Ellipse(height/5, 0, height/2, height/2);
        heartTop2 = new Ellipse(height/2, 0, height/2, height/2);
        heartTop.setStrokeColor(HEART_COLOR);
        heartTop.setFillColor(HEART_COLOR);
        heartTop2.setStrokeColor(HEART_COLOR);
        heartTop2.setFillColor(HEART_COLOR);
        heartBottom = Path.makeTriangle(heartTop.getX()+height/38, heartTop.getY()+height/4 + height/8, heartTop2.getX()+height/2 - height/38, heartTop2.getY()+height/4 + height/8, heartTop.getX()+heartTop.getWidth()/1.3, height/1.17);
        heartBottom.setStrokeColor(HEART_COLOR);
        heartBottom.setFillColor(HEART_COLOR);
        heart.add(heartTop);
        heart.add(heartTop2);
        heart.add(heartBottom);
        return heart;
    }

    public static GraphicsGroup getHeart()
    {
        return heart;
    }
    static int count = 0;

    public static void main(String[] args) 
    {
        // ArrayList<String> arraylist = new ArrayList<>();
        // String[] array = new String[]{"abc", "2", "10", "0"};
        
       
    }
    
}
