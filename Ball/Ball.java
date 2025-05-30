// package ball1;
// ssh www.center.ogu
import javax.swing.*;
import java.awt.*;



public class Ball extends JComponent implements Runnable {
    private Color color;
    private int x, y;
    private int diam;
    private int speedX, speedY;
    // private static JPanel panel;
    private int width;
    private int height;
    private Thread ballThread;
    private int xRect, yRect;
    private SwitchLock sl;
    private boolean lock;

    public Ball(Color color, JPanel parent, SwitchLock switchLock){
        this.color = color;
        this.diam = 20;
        this.width = parent.getWidth();
        this.height = parent.getHeight();

        this.x = (int)(Math.random()/*  * (this.width - diam) */);
        this.y = (int)(Math.random()/*  * (this.height - diam) */);

        setSize(this.diam, this.diam);
        // setLocation(this.x, this.y);

        parent.add(this);
/*         parent.revalidate();
        parent.repaint(); */

        this.ballThread = new Thread(this);
        if(this.color == Color.RED)
            this.ballThread.setName("Red");
        if(this.color == Color.YELLOW)
            this.ballThread.setName("Yellow");
        this.ballThread.start();

        xRect = (BallConfig.PANEL_WIDTH - BallConfig.RECT_WIDTH) / 2;
        yRect = (BallConfig.PANEL_HEIGHT - 2*BallConfig.RECT_HEIGHT) / 2;

        this.sl = switchLock;

    }

    public void run(){
        this.speedX = (int)(Math.random() * 5)+1;
        this.speedY = (int)(Math.random() * 5)+1;

        while(true){
            if((this.x + this.speedX + this.diam > this.xRect)  &&
                (this.x + this.speedX < this.xRect + BallConfig.RECT_WIDTH) &&
                (this.y + this.speedY + this.diam > this.yRect) &&
                (this.y + this.speedY < this.yRect + BallConfig.RECT_HEIGHT))
            {
                try{
                    if(!this.lock){
                        this.sl.lock(this.ballThread.getName());
                        this.lock = true;
                    }
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                try{
                    if(this.lock){
                        this.sl.unLock(this.ballThread.getName());
                        this.lock = false;
                    }
                }
                catch(Exception e){

                }
            }



            // SwingUtilities.invokeLater(() -> {
                move();
                setLocation(x, y);
                repaint();
            // });

            // this.move();
            // this.repaint();

            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){
                break;
            }
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(this.color);
        g.fillOval(0, 0, this.diam, this.diam);

    }

    public void move(){
        this.x += this.speedX;
        this.y += this.speedY;
        if(this.x + this.speedX >= (this.width - this.diam) ||
            this.x + this.speedX <= 0)
                this.speedX = -this.speedX;
        if(this.y + this.speedY >= (this.height - this.diam) ||
            this.y + this.speedY <= 0)
                this.speedY = -this.speedY;
    }

}
