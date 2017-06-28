
 package CSE360;

 import javax.swing.*;

 public class Team6Ghost extends JLabel implements Runnable {
         boolean exit = false;
        
 	public Team6Ghost() { 
 		ImageIcon newImage = new ImageIcon("src/CSE360/Team6Images/pacman.png"); 
 		setIcon(new ImageIcon(newImage.getImage().getScaledInstance(27, 32, 0))); 
 	}

 	
 	@Override
 	public void run() {
                
 		int x = 0;
 		int y = 61;
 		int xpace = 3;
                int ypace = 3;
 		int xpaceSpeed = xpace;
                int ypaceSpeed = 0;
 		int width = 270;
 		int height = 150;
 		
 		while (exit != true) {
 			setBounds(x, y, width, height);
 			if (x <= 0 && y > 60) {
                            
 				xpaceSpeed = xpace;
                                ypaceSpeed = 0;
                                
 			}
 			if (x > width) {
 				xpaceSpeed = 0;
                                ypaceSpeed = -ypace;
 			}
                        
                        if( y < -60){
                            
                            xpaceSpeed = -xpace;
                            ypaceSpeed = 0;
                            
                        }
                        if (x < 1 && y <= -60) {
 				xpaceSpeed = 0;
                                ypaceSpeed = ypace;
                                //System.out.println("test");
 			}
                        y += ypaceSpeed;
 			x += xpaceSpeed;
                        
                        
 			try {
 				Thread.sleep(50);
 			} catch (InterruptedException e) {
 				System.out.println("Error");
 			}
 		}
                
                
 	}
        public void stop(){
        exit = true;
        System.out.println("stop");
    }
        public void resume(){
        exit = false;
        System.out.println("resume");
        run();
    }
        
        
 }
