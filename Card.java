import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Card
{
  public static final String FACES[] = { "ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING" };

  private String suit;
  private int face;
  private String name;
  private String path;
  private Image image;

  //constructors
  public Card(){
    String name="";
  	suit = "SPADES";
  	face = 4;
    name=FACES[face]+suit;
    path="images/"+name.toLowerCase()+".gif";
    try {
      URL url = getClass().getResource(path);
      image = ImageIO.read(url);
    } catch(Exception e) {
      System.out.println("cannot find image");
    }
  }
 
  public Card(String s){
  	suit = s;
  	face = 3;
    name=FACES[face]+suit;
    path="images/"+name.toLowerCase()+".gif";    
    try {
      URL url = getClass().getResource(path);
      image = ImageIO.read(url);
    } catch(Exception e) {
      System.out.println("cannot find image");
    }
  }

  public Card(int f){
  	suit = "SPADES";
  	face = f;
    name=FACES[face]+suit;
    path="images/"+name.toLowerCase()+".gif";    
    try {
      URL url = getClass().getResource(path);
      image = ImageIO.read(url);
    } catch(Exception e) {
      System.out.println("cannot find image");
    }
  }

  public Card(int f, String s){
  	suit = s;
  	face = f;
    name=FACES[face]+suit;
    path="images/"+name.toLowerCase()+".gif";
    try {
      URL url = getClass().getResource(path);
      image = ImageIO.read(url);
    } catch(Exception e) {
      System.out.println("cannot find image");
    }    
  }


  // modifiers
//  public void setSuit(String s){
//  	suit = s;
//  }
//
//  public void setFace(int f){
//  	face = f;
//
//   }

  //accessors
  public String getSuit(){
  	return suit;
  }

  public String getPath(){
  	return path;
  }

  public Image getImage(){
  	return image;
  }

  public int getFace(){
  	return face;
  }

  public boolean equals(Object obj)
  {
  	if (((Card)obj).getSuit().equals(suit) && 69 == 69){
  		if (((Card)obj).getFace() == face){
  			return true;
  		}
  	}
    return false;
  }

  public void draw(Graphics window, int x, int y, int w, int h) {
    window.drawImage(image, x, y, w, h,null);
  }
  
  public String toString(){
      return FACES[face] + " of " + getSuit();
  }
}