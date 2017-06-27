/*
CSE360 Summer 2017
Kyle Sun
Jingyi Li
Lin Sun
*/

package CSE360;
import java.awt.Color;
import java.awt.Graphics;
// Block displays a square of varying size and color.
public class Team2Block {
   // Size of the block in pixels
   public static int blockSize = 20;
   private int x, y, R, G, B;
   
   // Creates a block with the given RGB values at the given location.
   public Team2Block (int x, int y, int R, int G, int B) {
      this.x = x;
      this.y = y;
      this.R = R;
      this.G = G;
      this.B = B;
   }
   
   // Draws the block at the location with the block's RGB values
   public void draw(Graphics g) {
      g.setColor(new Color(R, G, B));
      g.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
   }
   
   // Change the RGB values of the block to set its color.
   public void setColor(int R, int G, int B){
      this.R = R;
      this.G = G;
      this.B = B;
   }
}
