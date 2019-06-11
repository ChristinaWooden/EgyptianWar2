import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class EgyptianWarClient extends JFrame implements KeyListener {

    private static final int PORT = 58901;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Mahogany mahogany;
    private BufferedImage back;
    private int upperDisplay;
    private boolean[] keys;
    private ArrayList<Card> center;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    private int playerNumber;

    public EgyptianWarClient(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, PORT);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        keys = new boolean[3];
        mahogany = new Mahogany(WIDTH, HEIGHT);
        center = new ArrayList<Card>();
    }

    public void play() throws Exception {
        try {
            var response = in.nextLine();
//            System.out.println(response);
            playerNumber = Integer.parseInt(response.substring(16));
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("PLACED_CARD")){
                	String faceSuit = response.substring(11);
                	int face = Integer.parseInt((faceSuit.substring(0, faceSuit.indexOf(" "))));
                	String suit = faceSuit.substring(faceSuit.indexOf(" ") + 1);
                	Card placedCard = new Card(face, suit);
                	center.add(0, placedCard);
                	repaint();
                } else if (response.startsWith("BURNED_CARD")){
                	String faceSuit = response.substring(11);
                	int face = Integer.parseInt((faceSuit.substring(0, faceSuit.indexOf(" "))));
                	String suit = faceSuit.substring(faceSuit.indexOf(" ") + 1);
                	Card placedCard = new Card(face, suit);
                	center.add(placedCard);
                	repaint();
                } else if (response.startsWith("CLEAR")) {
                    center.clear();
                } else if (response.startsWith("MESSAGE")) {
                    System.out.println(response.substring(8));
                } else if (response.startsWith("VICTORY")) {
                    System.out.println("Winner Winner");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    System.out.println("Sorry you lost");
                    break;
                } else if (response.equals("OTHER_PLAYER_LEFT")) {
                    System.out.println("QUITTING");
                    break;
                }
                repaint();
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

        public void update(Graphics window) {
            paint(window);
        }

        public void setCenter(ArrayList<Card> c){
        	center = c;
        }

        public ArrayList<Card> getCenter(){
        	return center;
        }

        public void paint(Graphics window) {
            Graphics2D twoDGraph = (Graphics2D)window;
            if (back == null) {
                back = (BufferedImage)(createImage(getWidth(), getHeight()));
            }
            Graphics graphToBack = back.createGraphics();
            mahogany.draw(graphToBack);

            graphToBack.setColor(Color.WHITE);
            graphToBack.fillRect(150, 10, 500, 150);
            graphToBack.setColor(Color.BLACK);
            graphToBack.drawString("EGYPTIAN WAR", 350, 45);
            drawCenter(graphToBack);
            twoDGraph.drawImage(back, null, 0, 0);
        }

        public void drawCenter(Graphics graphToBack){
            upperDisplay=Math.min(4,center.size());
            if(center.size()>0){
                for(int i=upperDisplay-1;i>=0;i--){
                   (center.get(i)).draw(graphToBack, (150+((upperDisplay - i)*69)), 300, 120, 150);
                }
            }
        }

        public void keyPressed(KeyEvent e) {
            // do nothing
        }

        public void keyReleased(KeyEvent e) {
            if (playerNumber == 1) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    out.println("ACTION PLACE_CARD");
                }
                 if (e.getKeyCode() == KeyEvent.VK_2) {
                    out.println("ACTION SLAP");
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_9) {
                    out.println("ACTION PLACE_CARD");
                } else if (e.getKeyCode() == KeyEvent.VK_0) {
                    out.println("ACTION SLAP");
                }
            }
            repaint();
        }

    public void keyTyped(KeyEvent evt) {
        // do nothing
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }

        EgyptianWarClient client = new EgyptianWarClient(args[0]);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.setSize(WIDTH, HEIGHT);
        client.setVisible(true);
        client.setResizable(false);
        client.addKeyListener(client);
        client.play();
    }
}
