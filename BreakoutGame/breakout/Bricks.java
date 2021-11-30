package breakout;
import Graphics.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is where the bricks get their details, i.e color, position and spacing so that they are added appropriately 
 * to the canvas. The colors are coordinated by row as seen in the method below
 */

public class Bricks
{
    private static final Double RECTANGLE_WIDTH = 77.0;
    private static final Double RECTANGLE_HEIGHT = 20.0;
    private List<Rectangle> listOfBricks;

    public Bricks(CanvasWindow canvas)
    {
        listOfBricks = new ArrayList<>();
    }

    /**
    * This method is responsible for making the wall. It hsa conditionals to check what color each row will have for the bricks
    * the loop decides how many rows there will be, in this case, 10 rows of 10 bricks each. It then returns a list of those
    bricks with the appropriate locations of the bricks set.
    * @param xcoord the parameter responsible for deciding the x coord for where the wall begins
    * @param ycoord the parameter responsible for deciding the y coord for where the wall begins
    */
    public List<Rectangle> MakeBrickWall(Double xcoord, Double ycoord)
    {
        Double xcoordcopy = xcoord;
        Double ycoordcopy = ycoord;
        Color color = new Color(255,0,0);
        for (int i = 0; i < 10; i++)
        {
            if (i <= 1)
            {
                color = Color.RED;
            }
            else if (i >1 && i < 4)
            {
                color = Color.ORANGE;
            }
            else if (i >3 && i  < 6)
            {
                color = Color.YELLOW;
            }
            else if (i >5 && i  < 8)
            {
                color = Color.GREEN;
            }
            else if (i > 7 && i<10)
            {
                color = Color.BLUE;
            }
            for (int j=0; j <10; j++)
            {
                // create new brick each time and add to our brick list, updating the x coord each time
                Rectangle brick = new Rectangle(xcoordcopy, ycoordcopy,RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                brick.setFillColor(color);
                brick.setPosition(xcoordcopy, ycoordcopy);
                listOfBricks.add(brick);
                xcoordcopy += RECTANGLE_WIDTH + 3;
            }
            xcoordcopy = xcoord;
            ycoordcopy += RECTANGLE_HEIGHT +3;
        }
        return listOfBricks;
    }

    public List<Rectangle> getBricks()
    {
        return listOfBricks;
    }
}

