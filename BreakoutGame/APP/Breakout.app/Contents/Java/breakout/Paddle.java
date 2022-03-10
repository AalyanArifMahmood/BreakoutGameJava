package breakout;

import Graphics.*;

import java.awt.Color;

/**
 * This class is responsible for giving the paddle its attributes such as length, height and shape, etc. 
 * The class also declases how the paddle will move.
 */
public class Paddle 
{
    private Rectangle paddle;
    private static final int PADDLE_LENGTH = 150;

    public Paddle(CanvasWindow canvas)
    {

        paddle = new Rectangle(canvas.getWidth()/2, canvas.getHeight()/1.1, PADDLE_LENGTH,10);
        paddle.setFillColor(Color.WHITE);
        paddle.setStrokeColor(Color.WHITE);
        canvas.add(paddle);
    }

    public Rectangle GetPaddle()
    {
        return paddle;
    }

    /**
    * This method is responsible for the movement of the paddle and checking the paddle is still within bounds. If it
    * is, then the method sets the position of the paddle to be the new, shifted position of the paddle
    * @param  canvas, this is the canvas on which the paddle is placed
    */
    public void movePaddle(CanvasWindow canvas)
    {
        // check if in bounds, if yes, then move, if not, then check where it is going out of bounds and adjust accordingly
        
            canvas.onMouseMove(event -> 
            { 
                paddle.setCenter(event.getPosition().getX(), canvas.getHeight()/1.1);

                if (event.getPosition().getX() + paddle.getWidth()/2 >= canvas.getWidth())
                {
                    paddle.setX(canvas.getWidth() - paddle.getWidth());
                }
                if (event.getPosition().getX() - paddle.getWidth()/2 <= 0)
                {
                    paddle.setX(0);     
                }
            });
    }
}
