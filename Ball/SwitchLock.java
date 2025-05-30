// package ball1;


public class SwitchLock {
    private int countRedBall;
    private int countYellowBall;
    
    public SwitchLock(){
        // countBall = 0;
        countRedBall = 0;
        countYellowBall = 0;
    }

    public synchronized void lock(String nameBallThread){
        
        try{
            while(nameBallThread.equals("Red") && countRedBall+countYellowBall > 0)
                this.wait();

            if(nameBallThread.equals("Red") && countRedBall+countYellowBall == 0)
                countRedBall++;
            
            while(nameBallThread.equals("Yellow") && countRedBall > 0)
                this.wait();
            
            if(nameBallThread.equals("Yellow") && countRedBall == 0)
                countYellowBall++;
            
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }

    }

    public synchronized void unLock(String nameBallThread){
        if(nameBallThread.equals("Red"))
            countRedBall--;
        if(nameBallThread.equals("Yellow"))
            countYellowBall--;
        this.notifyAll();
    }

}
