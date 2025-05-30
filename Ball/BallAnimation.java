// package ball1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BallAnimation extends JFrame {
    private JPanel panel;
    private JPanel ballPanel;
    private SwitchLock switchLock;
    
    public BallAnimation(){

        switchLock = new SwitchLock();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        ballPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);                
                int x = (BallConfig.PANEL_WIDTH - BallConfig.RECT_WIDTH) / 2;
                int y = (BallConfig.PANEL_HEIGHT - 2*BallConfig.RECT_HEIGHT) / 2;
                
                g.setColor(Color.BLACK);
                g.drawRect(x, y, BallConfig.RECT_WIDTH, BallConfig.RECT_HEIGHT);
            }
        };
        ballPanel.setLayout(null);

        JButton redButton = new JButton("Red ball");
        redButton.setPreferredSize(new Dimension(100, 20));

        JButton yellowButton = new JButton("Yellow ball");
        yellowButton.setPreferredSize(new Dimension(100, 20));

        panel.add(redButton);
        panel.add(yellowButton);

        redButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Ball ball = new Ball(Color.RED, ballPanel, switchLock);
            }
        });

        yellowButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Ball ball = new Ball(Color.YELLOW, ballPanel, switchLock);
            }
        });


        add(panel, BorderLayout.NORTH);
        add(ballPanel,BorderLayout.CENTER);
    }


    public static void main(String [] s){
        SwingUtilities.invokeLater(()->{
            new BallAnimation().setVisible(true);
        });
        // JFrame frame = new JFrame();
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(400, 400);
        // frame.setLocationRelativeTo(null);

        
    }

/*     public void run(){
        while(true){
            this.ballPanel.getGraphics().drawRect(100,100,200,100);

            try{
                Thread.sleep(20);
            }
            catch(InterruptedException e){
                return;
            }
        }
    } */
}