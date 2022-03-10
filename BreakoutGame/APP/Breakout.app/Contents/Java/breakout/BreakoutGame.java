package breakout;
import Graphics.*;
import Graphics.CanvasWindow;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The actual game of Breakout. This class creates instances of all the other classes and uses other methods defined in it 
 * implement all the actual mechanisms of the game, plus ome added bonus features tha are adderssed where added
 */
public class BreakoutGame 
{
    private static final int CANVAS_LENGTH =800;
    private static final int CANVAS_HEIGHT = 600;
    private CanvasWindow canvas;
    private Paddle paddle;
    private Ball ball;
    private int lives;
    private List<Rectangle> brickList;
    private GraphicsGroup life1;
    private GraphicsGroup life2;
    private GraphicsGroup life3;
    private List<GraphicsGroup> liveslist;
    private int heartwidth;
    private GraphicsText statusText;
    private GraphicsText exitText;
    private List<Rectangle> brickBrokenList;
    private List<Rectangle> brickBrokenListForSpeed;
    private int countertoIncreaseSpeed;
    private int bazinga;


     /**
     * The constructor initializes all of the variables and objects needed for the game, and is responsible for the animate, i.e 
     * the way the game actually moves.
     */

    public BreakoutGame()
    {   
        canvas = new CanvasWindow("Atari Breakout!", CANVAS_LENGTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK);
        paddle = new Paddle(canvas);
        heartwidth = 30;
        statusText = new GraphicsText("");
        statusText.setFont(FontStyle.BOLD, canvas.getHeight() / 6);
        statusText.setFillColor(Color.WHITE);
        exitText = new GraphicsText("");
        exitText.setFont(FontStyle.BOLD, canvas.getHeight() / 6);
        exitText.setFillColor(Color.WHITE);
        ball = new Ball(CANVAS_LENGTH/2, CANVAS_HEIGHT/2, 5, 270 + Math.pow(-1, ThreadLocalRandom.current().nextInt(1, 2)) * ThreadLocalRandom.current().nextInt(20, 46), CANVAS_LENGTH, CANVAS_HEIGHT);
        Bricks brick = new Bricks(canvas);
        brickList = brick.getBricks();
        ball.addToCanvas(canvas);
        brick.MakeBrickWall(1.5,50.0);
        this.lives = 3;
        liveslist = new ArrayList<>();
        brickBrokenList = new ArrayList<>();
        brickBrokenListForSpeed = new ArrayList<>();
        life1 = Lives.MakeHeart(heartwidth);
        life2 = Lives.MakeHeart(heartwidth);
        life3 = Lives.MakeHeart(heartwidth);
        liveslist.add(life3);
        liveslist.add(life2);
        liveslist.add(life1);
        countertoIncreaseSpeed = -1;
        bazinga = 0;
        
        Double startHeart = canvas.getWidth() / 1.2 + 2* heartwidth + 3;
        for (GraphicsGroup j : liveslist)
        {
            j.setPosition(startHeart, canvas.getHeight()/1.065);
            canvas.add(j);
            startHeart -= heartwidth - 3;
        }

        for(Rectangle i: brickList)
        {            
            canvas.add(i);
        }
        paddle.movePaddle(canvas);
    
        canvas.draw();

        canvas.pause(3000);

        canvas.animate(() ->  
        {
           runGame();
        });
    }

     /**
     * This method here is the core of the program as it checks all the conditions and is responsible for all the mechanisms 
     * of the ball's speed, checking to see the lives count and taking appropriate action them, etc. Essentially, all of the 
     * game's mechanism is contained in this method. it does all this by calling the appropriate methods defined below.
     */    
    public void runGame()
    {
        if (liveslist.size() != 0 && brickBrokenList.size() != brickList.size())
        {
            paddleball();
            ballBrick();
            SpeedCalibration();
            ball.UpdatePosition(1.0);
            LoseALife();
        }
        else if (lives == 0)
        {
            gameWinOrLose("You Lose!");
        }
        else if (brickBrokenList.size() == brickList.size())
        {
            gameWinOrLose("You Win!");
        }  
    }

    /** 
    * This method uses the logic to change the speed of the ball when the number of bricks broken are equal to 10 and the speed
    * is changed only in one frame, which is the purpose of the bazinga variable. The counter to increase speed variable helps
    * with the purpose of increasing the speed only once
    */
    public void SpeedCalibration()
    {
        if (brickBrokenListForSpeed.size() == 10 && bazinga == 0)
        {
            countertoIncreaseSpeed = 0;
            bazinga+=1;
        }
        else
        {
            countertoIncreaseSpeed = -1;
        }
        if (countertoIncreaseSpeed == 0)
        {
            increaseSpeed();
            countertoIncreaseSpeed += 1;
        }     
    }

    /**
     * This method is what handles what happens when a life is lost. It subtracts from from lives, removes a heart from the  
     * canvas and resets the speed of the ball to the initial speed, which is hard coded in the ball class. It also calls the
     * reset method whic resets the position of the ball and restarts the mechanism. 
     * This method ensures that the canvas is paused only if there are still lives left, otherwise the game just goes to the 
     * losing screen 
     */
    public void LoseALife()
    {
        if (ball.GetBall().getCenter().getY() + ball.getRadius() >= canvas.getHeight()/1.02)
        {
            lives -= 1;
            canvas.remove(liveslist.get(0));
            liveslist.remove(0);
            ball.SetXSpeed(ball.getXSpeed2());
            ball.SetYSpeed(ball.getYSpeed2());

            bazinga = 0;
            reset();
            brickBrokenListForSpeed.clear();
            if (lives != 0)
            {
                canvas.pause(3000);
            }
        }
    }

    /**
     * This method is responsible for choosing and displaying the appropriate message, depending on whether the player won
     * or lost, and implements event handling to close the window when the user clicks.
     * @param status this parameter determines which message to display on canvas. The way the method is used is that the 
     * condition is passed this method with the winning status as the string parameter, and the losing condition is passed
     * this method with the losing message string as the parameter
     */
    public void gameWinOrLose(String status)
    {
        canvas.removeAll();
        if (status.equalsIgnoreCase("You Win!"))
        {
            statusText.setText("You Win!");
        }
        else if (status.equalsIgnoreCase("You Lose!"))
        {
            statusText.setText("You Lose!");
        }
        statusText.setCenter(CANVAS_LENGTH/2, CANVAS_HEIGHT/1.85);
        canvas.add(statusText);
        exitText.setText("Click to exit");
        exitText.setCenter(CANVAS_LENGTH/2, CANVAS_HEIGHT/1.4);
        canvas.add(exitText);
        canvas.onClick(event -> 
        { 
            canvas.closeWindow();
        });
    }


      /**
     * This method is responsible for resetting the game's conditions when one life is lost. it removes the ball from the 
     * dying point, creates new ball and reassigns initial speed to it amongst other conditions and adds that ball to the 
     * starting position
     */
    public void reset()
    {
        canvas.remove(ball.GetBall());
        ball = new Ball(CANVAS_LENGTH/2, CANVAS_HEIGHT/2, 5, 270 + Math.pow(-1, ThreadLocalRandom.current().nextInt(1, 2)) * ThreadLocalRandom.current().nextInt(20, 46), CANVAS_LENGTH, CANVAS_HEIGHT);
        canvas.add(ball.GetBall());
        canvas.draw();
    }

      /**
     * This method is responsible for checking and implementing the interactions between the paddle and the ball, hence the name.
     * The method checks if the element at the bottom of the ball is the paddle, and it it is, it reverses the direction of the ball.
     * Added feature: My way of doing it is different in the sense that it checks where on the paddle the ball landed and changes 
     * the direction of the ball accordingly. For example, if the ball is coming from right and it bounces on the right side of 
     * the paddle, it bounces back in the direction it was coming from, which is how the original Atari Breakout worked.
     */
    public void paddleball()
    {
        Double ballBottomCenterX = ball.GetBall().getPosition().getX() + ball.getRadius();
        Double ballBottomCenterY = ball.GetBall().getPosition().getY() + 2*ball.getRadius();
        if (canvas.getElementAt(ballBottomCenterX, ballBottomCenterY) == paddle.GetPaddle())
        {
            if (ballBottomCenterX > paddle.GetPaddle().getCenter().getX())
            {
                if (ball.getXSpeed() > 0)
                {
                    ball.SetYSpeed(ball.getYSpeed() * -1);
                }
                else
                {
                    ball.SetXSpeed(ball.getXSpeed() * -1);
                    ball.SetYSpeed(ball.getYSpeed() * -1);
                }
            }
            else if (ballBottomCenterX < paddle.GetPaddle().getCenter().getX())
            {
                if (ball.getXSpeed() > 0)
                {
                    ball.SetXSpeed(ball.getXSpeed() * -1);
                    ball.SetYSpeed(ball.getYSpeed() * -1);
                }
                else
                {
                    ball.SetYSpeed(ball.getYSpeed() * -1);
                }
            }
        }
    }
    
      /**
     * This method is perhaps the most complicated one as it requires a lot of variables as can be seen. This method is responsible
     * for checking the collisions between the ball and the bricks. The method checks it the element at various locations on the ball
     * i.e top, bottom, left, right, northwest, northeast, southwest and southeast is a brick from the brick list and if it is, it removes
     * the brick from the canvas and changes the direction of the ball depending on where the ball was hit.
     * Added feature: The ball also changes color, depending on the color of the brick that is hit.
     */
    public void ballBrick()
    {

        Double ballBottomCenterX = ball.GetBall().getPosition().getX() + ball.getRadius();
        Double ballBottomCenterY = ball.GetBall().getPosition().getY() + 2*ball.getRadius();
        Double ballTopCenterX = ball.GetBall().getPosition().getX() + ball.getRadius();
        Double ballTopCenterY = ball.GetBall().getPosition().getY();
        Double ballLeftCenterX = ball.GetBall().getPosition().getX();
        Double ballRightCenterY = ball.GetBall().getPosition().getY() + ball.getRadius();
        Double ballRightCenterX = ball.GetBall().getPosition().getX() + 2*ball.getRadius();
        Double ballLeftCenterY = ball.GetBall().getPosition().getY() + ball.getRadius();
        Double LeftBottomHalfX = ball.GetBall().getPosition().getX() + 1.5*ball.getRadius();
        Double LeftBottomHalfY = ball.GetBall().getPosition().getY() + 0.5*ball.getRadius();
        Double LeftTopHalfX = ball.GetBall().getPosition().getX() + 0.5*ball.getRadius();
        Double LeftTopHalfY = ball.GetBall().getPosition().getY() + 0.5*ball.getRadius();
        Double RightBottomHalfX = ball.GetBall().getPosition().getX() + 1.5*ball.getRadius();
        Double RightBottomHalfY = ball.GetBall().getPosition().getY() + 1.5*ball.getRadius();
        Double RightTopHalfX = ball.GetBall().getPosition().getX() + 1.5*ball.getRadius();
        Double RightTopHalfY = ball.GetBall().getPosition().getY() + 0.5*ball.getRadius();

        for (Rectangle i: brickList)
        {
            if (canvas.getElementAt(LeftTopHalfX, LeftTopHalfY) == i || canvas.getElementAt(RightTopHalfX, RightTopHalfY) == i || canvas.getElementAt(LeftBottomHalfX, LeftBottomHalfY) == i || canvas.getElementAt(RightBottomHalfX, RightBottomHalfY) == i)
            {
                effectsOfCollision(i);
                // ball.SetXSpeed(ball.getXSpeed()*-1);
                ball.SetYSpeed(ball.getYSpeed() * -1);
                break;
            }
            else if (canvas.getElementAt(ballBottomCenterX, ballBottomCenterY) == i || canvas.getElementAt(ballTopCenterX, ballTopCenterY) == i)
            {
                effectsOfCollision(i);
                ball.SetYSpeed(ball.getYSpeed() * -1);
                break;
            }
            else if ( canvas.getElementAt(ballLeftCenterX, ballLeftCenterY) == i || canvas.getElementAt(ballRightCenterX, ballRightCenterY) == i)
            {
                effectsOfCollision(i);
                ball.SetXSpeed(ball.getXSpeed()*-1);
                break;
            }
        }
    }

    public void effectsOfCollision(Rectangle obj)
    {
        canvas.remove(obj);
        brickBrokenList.add(obj);
        brickBrokenListForSpeed.add(obj);
        ball.GetBall().setFillColor(obj.getFillColor());
    }

     /**
     * This method in general is an added feature as it follows the rules of the atari breakout game. This method increases the 
     * speed of the ball by a factor of 1.7. See the constructor details for information as to when this method is used and how
     * many times it is used.
     */
    public void increaseSpeed()
    {
        ball.SetXSpeed(ball.getXSpeed() * 1.7);
        ball.SetYSpeed(ball.getYSpeed() * 1.7);
    }

    public static void main(String[] args) 
    {
        new BreakoutGame();
    }
}