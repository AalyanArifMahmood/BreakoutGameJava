package breakout;
import Graphics.CanvasWindow;
import Graphics.Ellipse;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The class to create a ball object which declares the shape, dimensions and color of the ball, and also determines 
 * the initial speed and the reset speed of the ball
 * @author Class created by myself (Aalyan Arif Mahmood).
 */
public class Ball 
{
    public static final double RADIUS = 10;
    private double centerX;
    private double centerY;

    private double maxX;
    private double maxY;

    private double dX;
    private double dY;
    private double dX2;
    private double dY2;
    private Ellipse ball;
    private static final Color
        BALL_COLOR = new Color(0,0,255);

    public Ball(double centerX, double centerY, double initialSpeed, double initialAngle, double maxX, double maxY)
    {
        // Code for ball inspired quite a lot from Homework 2
        this.ball = new Ellipse(centerX - RADIUS, centerY - RADIUS, RADIUS*2,RADIUS*2); // making this such that the center x and y are at the center of the ball, and not the top left of the box
        ball.setFillColor(BALL_COLOR);
        this.centerX = centerX;
        this.centerY = centerY;
        this.maxX = maxX;
        this.maxY = maxY;

        double initialRadians = Math.toRadians(initialAngle);
        dX = initialSpeed * Math.cos(initialRadians);  
        dY = initialSpeed * -Math.sin(initialRadians); 
        dX2 = 5 * Math.cos( 270 + Math.pow(-1, ThreadLocalRandom.current().nextInt(1, 2)) * ThreadLocalRandom.current().nextInt(20, 46));  
        dY2 = 5 * -Math.sin( 270 + Math.pow(-1, ThreadLocalRandom.current().nextInt(1, 2)) * ThreadLocalRandom.current().nextInt(20, 46));  

    } 

    /**
     * This method is responsible for updating the position of the ball. It uses methods from within the GraphicsObject class
     * to achieve the purpose and also contains conditionals such that if the ball reaches the end of the boundary, i.e is
     * about to go out of bounds, it bounces in the opposite direction.
     * @param dt, which determines how fast the ball will be moving essentially in x and y directions
     */
    public void UpdatePosition(double dt)
    {
        // first check if the ball is at the bounds or not (inspired from homework 2)
        Double xPositionToReach = ball.getCenter().getX() + (dX*dt);
        Double yPositionToReach = ball.getCenter().getY() + (dY*dt);

        // need to of course reverse the x and y components of speed based on the rules of the actual game and physics
        // check if x max is passed or of the 0 line of x is passed
        if(xPositionToReach <= 0 || xPositionToReach >= maxX)
        {
            dX = -dX;
        }
        // now checking for Y of it reaches the top
        if (yPositionToReach <= 0 || yPositionToReach >= maxY)
        {
            dY = -dY;
        }
        // now the ball should bounce back each time it reaches the top edge, left or right!
        ball.moveBy(dX*dt, dY*dt);
    }

      /**
     * Various getters and setters to get and set speed and also to return speed to original (that is the purpose of the x2 and y2)
     */
    public double getCenterX()
    {
        return centerX;
    }

    public double getCenterY()
    {
        return centerY;
    }   

    public void addToCanvas(CanvasWindow canvas) {
        canvas.add(ball);
    }
    public Ellipse GetBall()
    {
        return ball;
    }

    public Double getXSpeed()
    {
        return dX;
    }

    public Double getYSpeed()
    {
        return dY;
    }

    public Double getXSpeed2()
    {
        return dX2;
    }

    public Double getYSpeed2()
    {
        return dY2;
    }

    public void SetXSpeed(Double speedX)
    {
        dX = speedX;
    }

    public void SetYSpeed(Double speedY)
    {
        dY = speedY;
    }

    public Double getRadius()
    {
        return RADIUS;
    }
}