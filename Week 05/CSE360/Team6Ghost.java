
 package CSE360;

 import javax.swing.*;

 public class Team6Ghost extends JLabel implements Runnable {
         boolean exit = false;
        
 	public Team6Ghost() { 
 		ImageIcon newImage = new ImageIcon("Team6Images/Pac-Man-Ghost-PNG-Transparent-Image.png"); 
 		setIcon(new ImageIcon(newImage.getImage().getScaledInstance(27, 32, 0))); 
 	}

 	
 	@Override
 	public void run() {
                
 		int x = 0;
 		int y = 70;
                //max y = 70;
                //min y = -48;
 		int xpace = 3;
                int ypace = 3;
 		int xpaceSpeed = xpace;
                int ypaceSpeed = 0;
 		int width = 118;
 		int height = 123;
 		
 		while (exit != true) {
 			setBounds(x, y, width, height);
 			if (x <= 0 && y > 70) {
                            y = 70;
 				xpaceSpeed = xpace;
                                ypaceSpeed = 0;
                                
 			}
 			if (x > width) {
 				xpaceSpeed = 0;
                                ypaceSpeed = -ypace;
 			}
                        
                        if( y < -48){
                            y = -48;
                            xpaceSpeed = -xpace;
                            ypaceSpeed = 0;
                            
                        }
                        if (x < 1 && y == -48) {
 				xpaceSpeed = 0;
                                ypaceSpeed = ypace;
                                System.out.println("test");
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
