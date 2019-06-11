import java.io.File;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Mahogany {
  private Image image;
  private int width;
  private int height;

  public Mahogany(int w, int h) {
    width = w;
    height = h;
    try {
      URL url = getClass().getResource("images/mahogany.jpg");
      image = ImageIO.read(url);
    } catch(Exception e) {
    }
  }

  public void draw(Graphics window) {
    window.drawImage(image,0,0, width, height, null);
  }
}