import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


class CustomerLocation extends JPanel
{
   private static final long serialVersionUID = 1L;
   final private static Border blackline = BorderFactory.createLineBorder(Color.black);
   final private static ImageIcon trollyGIF = new ImageIcon("trolly.gif");
   final private JLabel cust = new JLabel("",trollyGIF,SwingConstants.CENTER);
   final private JLabel noCust = new JLabel(" Empty");

   private int numberofGoods = 0;

   public CustomerLocation()
   {
      setBackground(Color.black);
      setLayout(new BorderLayout());
      setBorder(blackline);
      setNumberofGoods(0);
   }

   public void setNumberofGoods(int inGoods)
   {
      numberofGoods = inGoods;
      removeAll(); // Clear panel
      if (numberofGoods > 0)
      {
         add(cust);
         JLabel products = new JLabel("#"+numberofGoods);
		 products.setForeground(Color.pink);
         add(products,BorderLayout.EAST);
      }
      else
      {
         add(noCust);
      }

	  updateUI(); //need to redraw
   }
	
   public int getNumberofGoods()
   {
     return numberofGoods;
   }
}
