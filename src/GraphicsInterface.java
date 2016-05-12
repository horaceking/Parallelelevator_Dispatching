import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * *******************************************************
 * The entrance of the program 
 * Class Name: GraphicsInterface
 * *******************************************************
 * Class Major Function 
 * *Initialize the graphics interface
 * *Build and start the threads of elevators
 * *Keep listeners of buttons
 * *******************************************************
 * Post: The function of listeners is to add task to elevators' task lists
 */
public class GraphicsInterface extends JFrame{

	// Constants
	private static final int FRAME_HEIGHT = 750;
	private static final int FRAME_WIDTH = 1000;
	
	// Constructors
	public GraphicsInterface() {
		// initialize the frame and all components
		setFrame();
		setPanel();
		setButtom();
		// Update the size of the frame
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		// Build 5 thread for 5 elevators
		Ele_Thread eleT1 = new Ele_Thread(elevators[0]);
		Ele_Thread eleT2 = new Ele_Thread(elevators[1]);
		Ele_Thread eleT3 = new Ele_Thread(elevators[2]);
		Ele_Thread eleT4 = new Ele_Thread(elevators[3]);
		Ele_Thread eleT5 = new Ele_Thread(elevators[4]);
		
		Thread ele1 = new Thread(eleT1);
		Thread ele2 = new Thread(eleT2);
		Thread ele3 = new Thread(eleT3);
		Thread ele4 = new Thread(eleT4);
		Thread ele5 = new Thread(eleT5);
		
		// Start elevators
		ele1.start();
		ele2.start();
		ele3.start();
		ele4.start();
		ele5.start();
		
	}
	
	// Main method
	public static void main(String[] args) {
		new GraphicsInterface();
	}
	
	// Initialize the frame
	private void setFrame() {
		this.setLayout(new BorderLayout());
		this.setTitle("MyElelevator");
		this.setVisible(true);
		this.setSize(FRAME_HEIGHT, FRAME_HEIGHT);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Initialize the panels
	private void setPanel() {
		
		all_button = new JLayeredPane();
		all_button.setLayout(new BoxLayout(all_button, BoxLayout.X_AXIS));
		all_button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(all_button);
		
		flours_outside = new JPanel();
		flours_outside.setLayout(new GridLayout(20, 1, 5, 0));
		flours_outside.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		all_button.add(flours_outside, 0.2);
		
		
		des_button = new JPanel();
		des_button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));
		des_button.setLayout(new GridLayout(20, 2, 5, 5));
		all_button.add(des_button, 0.2);
		
		
		flours_inside = new JPanel[5];
		elevators = new MyElevator[5];
		for(int i = 0; i<5 ; i++){
			flours_inside[i] = new JPanel();
			flours_inside[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			flours_inside[i].setLayout(new GridLayout(20, 1, 5, 5));
			all_button.add(flours_inside[i], 0.1);
			elevators[i] = new MyElevator(i);
			elevators[i].setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
			all_button.add(elevators[i], 0.1);
		}
		
	}
	
	// Initialize the Buttons
	private void setButtom() {
		int i = 21;
		JLabel label = null;
		
		while(i > 1) {
			label = new JLabel(--i + " F");
			label.setPreferredSize(new Dimension(30 , 30));
			flours_outside.add(label);
		}
		
		i = 1;
		global.button_out = new Button_out[40];
		while(i <= 40){
			if(i % 2 == 1 && i != 1){
				global.button_out[i-1] = new Button_out("¡ø", 20 - (i-1) / 2, -1);
				global.button_out[i-1].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						Button_out b = (Button_out)e.getSource();
						MyElevator ele = new MyElevator();
						ele.task_list_up[b.get_floor()-1] = 1;
						b.setForeground(Color.red);
					}
				});
			}else if(i == 40 || i == 1){
				global.button_out[i-1] = new Button_out("", 20 - (i-1) / 2, 0);
			}else{
				global.button_out[i-1] = new Button_out("¨‹", 20 - (i-1) / 2, 1);
				global.button_out[i-1].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						Button_out b = (Button_out)e.getSource();
						MyElevator ele = new MyElevator();
						ele.task_list_down[b.get_floor()-1] = 1;
						b.setForeground(Color.red);
					}
				});
			}
			global.button_out[i-1].setPreferredSize(new Dimension(30 , 30));
			des_button.add(global.button_out[i-1]);
			i ++;
		}
		
		
		global.button_in = new Button_in[5][20];
		for(int j = 0; j < 5; j++){
			i = 20;
			global.button_in[j] = new Button_in[20];
			while(i >= 1) {
				global.button_in[j][i-1] = new Button_in("" + i, i, elevators[j]);
				global.button_in[j][i-1].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						Button_in b = (Button_in)e.getSource();
						b.set_task_in(b.get_floor());
						b.setForeground(Color.red);
					}
				});
				global.button_in[j][i-1].setPreferredSize(new Dimension(30 , 30));
				flours_inside[j].add(global.button_in[j][i-1]);
				i--;
			}
		}
	}
	
	//Instants variable
	JLayeredPane all_button = null;
	JPanel flours_outside = null;
	JPanel des_button = null;
	JPanel[] flours_inside;
	MyElevator[] elevators = null;
	GlobalInstants global = new GlobalInstants();
}
