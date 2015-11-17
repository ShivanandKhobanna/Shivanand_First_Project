package scs_package;
import java.util.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class MainView {

	public static void main( String args[])
	{
	
	JFrame frame= new JFrame(" The Insulin Pump");
	frame.setSize(new Dimension(1280, 1024));
	frame.setLayout(new FlowLayout());
	//frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	
	
	JButton username= new JButton(" User Name ");
	JTextField nametxt= new JTextField(15);
	JButton password= new JButton(" Password ");
	JPasswordField passtxt= new JPasswordField(15);
	JButton submit= new JButton(" Submit ");
	frame.add(username);
	frame.add(nametxt);
	frame.add(password);
	frame.add(passtxt);
	frame.add(submit);
	
	JLabel label= new JLabel(" ******* Worng username and password*******");
	JLabel time= new JLabel();
	
	JPanel panel1= new JPanel();
	JPanel panel2= new JPanel();
	JPanel panel3= new JPanel();
	
	JButton status= new JButton("Current Status");
	
	////****need to add imag to button
	ImageIcon image= new ImageIcon("C:\\Users\\shivanand\\workspace\\SCS\\imagefolder\\images\\battery1.png");
	JButton charger= new JButton(image);
	charger.setPreferredSize(new Dimension(32,20));
	
	JSlider slider= new JSlider( 0, 0, 200, 0);
	
	JProgressBar batterybar= new JProgressBar();
	batterybar.setMinimum(0);
    batterybar.setMaximum(100);
    batterybar.setValue(100);
    batterybar.setStringPainted(true);
    
    
	submit.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent ae){
			String name1= "shivanand";
			String pass1="123";
			String realpass=String.valueOf(passtxt.getPassword());
			if(name1.equals(nametxt.getText()) && pass1.equals(realpass))
			{
				frame.remove(username);
				frame.remove(password);
				frame.remove(nametxt);
				frame.remove(passtxt);
				frame.remove(submit); 
				frame.remove(label);
				//frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
				
				//JPanel panel1= new JPanel();
				panel1.setLayout(new FlowLayout());
				//panel1.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
				panel1.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
				panel1.setPreferredSize(new Dimension(1350, 100));
				
				//JPanel panel2= new JPanel();
				panel2.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
				panel2.setPreferredSize(new Dimension(1350, 400));
				panel2.setLayout(new FlowLayout());
				
				//JPanel panel3= new JPanel();
				panel3.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
				panel3.setPreferredSize(new Dimension(1350, 300));
				
				
				// ****************panel1 *********************************
				// ****Thread to dynamically display the date and Time
				Thread clock = new Thread()
				{
					public void run()
					{
						for(;;)
						{
							Date cal = new Date();
							time.setText(" "+cal);
							try {
								sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				clock.start();
				
				// ************Thread for battery life***************
				Thread battery = new Thread()
				{
					public void run()
					{
						for (;;)
						{	
							try {
								sleep(60000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							batterybar.setValue((batterybar.getValue()-2));
							
							if((batterybar.getValue())< 20)
							{
								batterybar.setForeground(Color.red);
							}
						}
					}
				};
				battery.start();
				
				JLabel Battery= new JLabel("Battery");
				JLabel space= new JLabel( "                                                                         ");
				
				panel1.add(Battery);
				panel1.add(batterybar);
				panel1.add(charger);
				panel1.add(space);
				panel1.add(time);
				
				
				
				//*******************panel2*********************************
				//JButton status= new JButton("Current Status");
				panel2.add(status);
				
				
				
				
				frame.add(panel1);
				frame.add(panel2);
				frame.add(panel3);
				
				frame.repaint();
				frame.validate();
			}
			else
			{
				frame.add(label);
				label.setForeground(Color.red);
				frame.repaint();
				frame.validate();
			}
		}
	});
	
	
	status.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent ae){
			JButton insulin= new JButton("Pump Insulin");
			JButton glycogon= new JButton("Pump Glycogon");
			JTextField txt= new JTextField(20);
			
			//****************slider to current Insulin status **************
			//JSlider slider= new JSlider( 0, 0, 200, 0);//rotation, min, max, initialvaue
			slider.setPaintTicks(true);
		    slider.setMajorTickSpacing(80);
		    slider.setMinorTickSpacing(40);
		    slider.setPaintLabels(true);
		    
		    //****************** progress bar for the Insulin content in reservoir
		    JProgressBar pb1= new JProgressBar();
		    pb1.setMinimum(0);
	        pb1.setMaximum(100);
			pb1.setValue(100);
			pb1.setStringPainted(true);
		    
			JProgressBar pb2= new JProgressBar();
		    pb2.setMinimum(0);
	        pb2.setMaximum(100);
			pb2.setValue(100);
			pb2.setStringPainted(true);
			
			JLabel insulinstatus= new JLabel("Insulin");
			JLabel glycogonstatus= new JLabel("Glycogon");
			
			// Display sequence in panel2************
			panel2.add(status);
			panel2.add(insulin);
			panel2.add(glycogon);
			panel2.add(txt);
			panel2.add(slider);
			panel2.add(insulinstatus);
			panel2.add(pb1);
			panel2.add(glycogonstatus);
			panel2.add(pb2);
			
		}
		
	});
	
   frame.setVisible(true);
	
	}
}
