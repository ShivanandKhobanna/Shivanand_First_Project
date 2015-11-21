package Testpackage;
import java.util.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.*;


public class testclass {

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
	JButton forgot= new JButton("Forgot Password");
	frame.add(username);
	frame.add(nametxt);
	frame.add(password);
	frame.add(passtxt);
	frame.add(submit);
	frame.add(forgot);
	
	
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
			
			boolean passwordflag=false;
			//connection to database to get the username and password
			try
			{
				/// to estabelish the connect to the database
				//Class.forName("com.mysql.jdbc.Driver");
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/SCS", "root", "root");
				/// creating a statement object
				Statement statement=con.createStatement();
			
				//String s="select * from users";
				//int r= statement.executeUpdate("insert into users (USER_NAME, PASSWORD) values ('test','test123')", Statement.RETURN_GENERATED_KEYS);
				//System.out.println(""+r);
			
				// executing the statement
				ResultSet result= statement.executeQuery("select * from users");
			
				while(result.next())
				{
					String realpass=String.valueOf(passtxt.getPassword());
					//System.out.println( result.getString(1)+ " " + result.getString(2)+" "+result.getString(3));
					if (result.getString(1).equals(nametxt.getText())&& result.getString(2).equals(realpass))
					{
						//System.out.println( result.getString(1)+ " " + result.getString(2)+" "+result.getString(3));
						passwordflag=true;
						break;
					}
					
					//System.out.println( result.getString(1)+ " " + result.getString(2)+" "+result.getString(3));
				}
			}catch(Exception a)
			{
				System.err.println(a);
			}
			
			
			
			
			//String name1= "shivanand";
			//String pass1="123";
			//String realpass=String.valueOf(passtxt.getPassword());
			//if(name1.equals(nametxt.getText()) && pass1.equals(realpass))
			if(passwordflag)
			{
				frame.remove(username);
				frame.remove(password);
				frame.remove(nametxt);
				frame.remove(passtxt);
				frame.remove(submit); 
				frame.remove(label);
				frame.remove(forgot);
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
	
	///// forgot password listener implimentation
	forgot.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			frame.remove(username);
			frame.remove(nametxt);
			frame.remove(password);
			frame.remove(passtxt);
			frame.remove(submit);
			frame.remove(label);
			frame.remove(forgot);
			
			JButton gardianname= new JButton ("Username ");
			JTextField gardiannametxt= new JTextField(15);
			JButton gardianpass= new JButton("Password");
			JPasswordField gardianpasstxt= new JPasswordField(15);
			JButton newsubmit= new JButton("Submit");
			JLabel temp= new JLabel ("wrong password");
			
			frame.add(gardianname);
			frame.add(gardiannametxt);
			frame.add(gardianpass);
			frame.add(gardianpasstxt);
			frame.add(newsubmit);
						
			//new gardian submit listener
			newsubmit.addActionListener(new ActionListener(){
				public void actionPerformed( ActionEvent ae){
					
					JButton newpassword= new JButton (" New Password");
					JPasswordField newpasstxt = new JPasswordField (20);
					JButton confirmnewpassword= new JButton (" Confirm Password");
					JPasswordField confirmnewpasstxt = new JPasswordField (20);
					JButton newpasswordsubmit= new JButton("Submit");
					boolean flag=false;
					try
					{
						/// to estabelish the connect to the database
						//Class.forName("com.mysql.jdbc.Driver");
						Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/SCS", "root", "root");
						/// creating a statement object
						Statement statement=con.createStatement();
					
					ResultSet result2= statement.executeQuery("select * from users");
					
					
					while(result2.next())
					{	
						
						String gradianrealpass=String.valueOf(gardianpasstxt.getPassword());
						//System.out.println( result.getString(1)+ " " + result.getString(2)+" "+result.getString(3));
						if (result2.getString(1).equals(gardiannametxt.getText())&& result2.getString(2).equals(gradianrealpass))
						{
							flag=true;
							break;
						}
					}
					}catch(Exception a)
					{
						System.err.println(a);
					}
					if(flag){		//System.out.println( result.getString(1)+ " " + result.getString(2)+" "+result.getString(3));							
					frame.remove(gardianname);
					frame.remove(gardiannametxt);
					frame.remove(gardianpass);
					frame.remove(gardianpasstxt);
					frame.remove(newsubmit);
					frame.remove(temp);
					frame.add(newpassword);
					frame.add(newpasstxt);
					frame.add(confirmnewpassword);
					frame.add(confirmnewpasstxt);
					frame.add(newpasswordsubmit);
					}
					else
					{
						/// label for gardian wrongpassword 
						frame.add(temp);
					}
					frame.repaint();
					frame.validate();
					
					
					//action listener to set the new password
					newpasswordsubmit.addActionListener(new ActionListener(){
						public void actionPerformed( ActionEvent ae){
							JLabel wronglabel= new JLabel(" please enter the same password");
							// inserting the new password into database(SCS)table(Users)
							String a, b;
							a=String.valueOf(newpasstxt.getPassword());
							b=String.valueOf(confirmnewpasstxt.getPassword());
							if(!(a.equals(b)))
							{
								
								frame.add(wronglabel);
								frame.repaint();
								frame.validate();
							}
							else{
							try
							{
								frame.remove(wronglabel);
								/// to estabelish the connect to the database
								//Class.forName("com.mysql.jdbc.Driver");
								Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/SCS", "root", "root");
								/// creating a statement object
								PreparedStatement ps=null;
								String str=String.valueOf(newpasstxt.getPassword());
								String sql="update users set PASSWORD=? where USER_NAME='shivanand'";
								ps=con.prepareStatement(sql);
								ps.setString(1, str);
								boolean returnvalueofupdatequery=ps.execute();
								ps.close();
								
								if(!returnvalueofupdatequery)
								{
									JLabel passwordupdate= new JLabel(" Password reset is successfull!! Now user can login with new password");
									frame.add(passwordupdate);
									frame.remove(wronglabel);
									
								}
								
								
							}catch(Exception x)
							{
								System.err.println(x);
							}
							
							frame.repaint();
							frame.validate();
							}
							}
					});
					
				}
			});

			//JButton newusername= new JButton(" New Password");
			//JTextField newtext= new JTextField(15);
			
			frame.repaint();
			frame.validate();
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
