import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckoutTest {
    static JFrame frame;

    public final static int checkOuts = 8;
    public final static int maxQueueSize = 6;

    private JPanel queuePanel[] = new JPanel[checkOuts];
    private CustomerLocation queue[][] = new CustomerLocation[checkOuts][maxQueueSize];
    private JPanel checkOutsPanel;
    private JPanel infoPanel;

	Controler controler;

    public CheckoutTest() 
    {
        checkOutsPanel = new JPanel();
        checkOutsPanel.setLayout(new GridLayout(checkOuts+1,1));
        infoPanel = new JPanel();
        checkOutsPanel.add(infoPanel);

        // Create panel for each queue
        for (int count = 0; count < checkOuts; count++)
        {
           queuePanel[count] = new JPanel();
           queuePanel[count].setLayout(new GridLayout(1,maxQueueSize+1));
           queuePanel[count].add(new JLabel("Check Out " + count));

           for (int i = 0; i < maxQueueSize; i++) // now add queue locations for each queue
           {
             queue[count][i] = new CustomerLocation();
             queuePanel[count].add(queue[count][i]);
           }

           checkOutsPanel.add(queuePanel[count]);
        }  

		controler = new Controler(queue);
    }

    public static void main(String s[]) {
	CheckoutTest mainObj = new CheckoutTest();
        mainObj.createFrame();
    }

    void createFrame()
    {
		frame = new JFrame("SimpleExample");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		Container mainPane = frame.getContentPane();
		mainPane.add(checkOutsPanel);
			frame.setSize(800, 600);


			/* queue 1  pos 1 */
//			queue[1][1].setNumberofGoods(42);
//			queue[1][0].setNumberofGoods(142); 
//			queue[0][0].setNumberofGoods(44); 
	
         controler.control();


		frame.setVisible(true);
    }

}












