import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/*
*************************************************************
* Class Name: My Elevator 
*************************************************************
* Class Major Function 
** Paint the elevator
** Move the elevator
** Remove tasks from task_list
** Maintain some variables of information about the elevators
*************************************************************
* Quick Look of All Methods
* ***
* public MyElevator()
* public MyElevator(int name)
* 
* public void paint(Graphics g)
* 	private void setColor(Color col)
* 
* public void move(int des)
* 
* public int get_des()
* public void set_des(int dest)
* public int get_pos()
* public void set_pos(int dest)
* public int set_my_task_at(int floor)
* public void get_my_task_at(int floor)
* 
* public int get_next_floor()
* 	private int seek_in_upflour()
* 	private int seek_in_downflour()
* 	private int seek_in_allflour()
* 	private int seek_in_my_task()
* 	private int nearer_floor(int a, int b)
* public int remove_task()
 */
public class MyElevator extends JPanel {
	
	// Constants
	private static final int WIDTH 		 = 50;
	private static final int HEIGHT      = 25;
	private static final int MOVE_HEIGHT = 35;
	public static final  int UP          = -1;
	public static final  int DOWN        = 1;
	
	// Constructors
	public MyElevator() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public MyElevator(int name) {
		ele_name = name;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	// draw a rectangle at (pos_x, pos_y) with the size WIDTH, HEIGHT and the color is black
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.black);
		g.setXORMode(getBackground());
		g.fillRect(pos_x, pos_y, WIDTH, HEIGHT);
	}
	
	// Change the color of the rectangle (elevator) 
	private void setColor(Color col){
		Graphics g = this.getGraphics();
		g.clearRect(pos_x, pos_y, WIDTH, HEIGHT);
		g.setColor(Color.red);
		g.fillRect(pos_x, pos_y, WIDTH, HEIGHT);
	}
	
	// Move the elevator by the MOVE_HEIGHT, and the move orientation is up to "des" (private variable)
	public void move(int des) {
		switch(des){
		case UP:
			for(int i = 0; i < MOVE_HEIGHT; i++) {
			pos_y += UP;
			repaint();
			}
			pos += 1;
			break;
		
		case DOWN:
			for(int i = 0; i < MOVE_HEIGHT; i++) {
				pos_y += DOWN;
				repaint();
				}
			pos -= 1;
			break;
		}
	}
	
	// Methods of set/get private variables
	public int get_des() {
		return des;
	}
	
	public void set_des(int dest) {
		des = dest;
	}
	
	public int get_pos() {
		return pos;
	}
	
	public void set_pos(int post) {
		pos = post;
	}
	
	public void set_my_task_at(int floor) {
		my_task[floor - 1] = 1;
	}
	
	public int get_my_task_at(int floor) {
		return my_task[floor - 1];
	}
	
	// Check the task lists and return the next target floor
	public int get_next_floor() {
		int target = 0;
		if(des == 0){								// If elevator stop, seek in all floors
			target = seek_in_allflour();
		}else if(des == UP) {						// Else if elevator is going up, seek in up_floors 
			target = seek_in_upflour();
			if(target == 0)							// 		if there is no task in up_floors, seek in all floors
				target = seek_in_allflour();
		}else{										// Else (elevator is going down), seek in down_floors
			target = seek_in_downflour();
			if(target == 0)							//		if there is no task in down_floors,seek in all floors
				target= seek_in_allflour();
		}
		
		return nearer_floor(seek_in_my_task(), target);// Compare to my_list and decide which floor is target
	}
	
	// Method of seek in up_floors, from "pos" (private variable) to 20f
	private int seek_in_upflour() {
		int i = pos - 1;
		while(i < 20){
			if(task_list_up[i] == 1)return i+1;
			i ++;
		}
		return 0;
	}
	
	// Method of seek in down_floors, from "pos" (private variable) to 0f
	private int seek_in_downflour() {
		int i = pos - 1;
		while(i >= 0){
			if(task_list_down[i] == 1)return i+1;
			i --;
		}
		return 0;
	}
	
	// Method of seek in all_floors as follows:
	// When going up, floors that is higher than elevator's floor have the preference
	// When going down, floors that is lower than elevator's floor have the preference
	// In other case, up tasks have the preference
	private int seek_in_allflour() {
		int i = 0;
		while(i < 20){
			if(task_list_up[i] == 1)break;
			i ++;
		}
		int j = 19;
		while(j >= 0){
			if(task_list_down[j] == 1)break;
			j --;
		}
		if(des == UP && i != 20){
			return j + 1 >= pos ? j + 1 : i + 1;
		}else if(des == DOWN){
			return i + 1 <= pos ? i + 1 : j + 1;
		}else return i == 20 ? j + 1 : i + 1;
	}
	
	// Method of seek in my_list
	private int seek_in_my_task() {
		if(des != 0){												// Seek a target while the elevator is moving
			for(int i = pos-1; i < 20 && i >= 0; i -= des){			// 		From pos to 20f while up, pos to 0f while down 
				if(my_task[i] == 1) return i + 1;
			}
		}else{														// Seek a target while the elevator stop
			for(int i = 19; i >= 0; i --){							// 		From 20f to 0f
				if(my_task[i] == 1) return i + 1;
			}
		}
		return 0;													// If nothing found, return 0
	}
	
	// Method of find the nearer floor, destination and position will help to make a judge
	// While both a and b is not equal to 0:
	// des 		a-pos	b-pos	return
	//-----------------------------------
	// 0		>=0		>=0		Min(a,b)
	// 0		>=0		<0		a
	// 0		<0		>=0		b
	// UP		>=0		>=0		Min(a,b)
	// UP		>=0		<0		a
	// UP		<0		>=0		b
	// DOWN 	<=0		<=0		Max(a,b)
	// DOWN 	>0		<=0		b
	// DOWN 	<=0		>0		a
	private int nearer_floor(int a, int b){
		if( a == 0 )return b;
		if( b == 0 )return a;
		if(des == DOWN){
			if(a <= pos && b <= pos){
				return a-b > 0 ? a : b;
			}else if( a > pos)return b;
			else return a;
		}else{
			if(a >= pos && b >= pos){
				return a-b >= 0 ? b : a;
			}else if( a < pos)return b;
			else return a;
		}
			
	}
	
	// Remove tasks from the task list, the task of my_list (task list in elevators) in this floor must be remove
	// Return the type of remove:
	// -1/UP      remove the up task of this floor 
	// 1/DOWN     remove the down task of this floor
	// 0          remove all task of this floor
	public int remove_task() {
		int remove_type = 0;
		
		// Judge the remove type by the move destination
		if(des == UP){
			task_list_up[pos-1] = 0;
			remove_type = UP;
		}else if(des == DOWN){
			task_list_down[pos-1] = 0;
			remove_type = DOWN;
		}else {
			task_list_up[pos-1] = 0;
			task_list_down[pos-1] = 0;
		}
		
		// If this floor is the final target floor, change remove type to 0
		if(get_next_floor() == pos){
			task_list_up[pos-1] = 0;
			task_list_down[pos-1] = 0;
			remove_type = 0;
		}
		
		// Remove the task in my_task
		my_task[pos-1] = 0;
		
		// Change the color of the elevator to red to state the elevator is open
		setColor(Color.red);
		
		return remove_type;
	}
	
	
	// Instant variables
	public int ele_name = 0;
	private int pos_x   = 0;
	private int pos_y   = 676;
	private int des     = 0;
	private int pos     = 1;
	
	// Task list
	public static int[] task_list_up   = new int[20];// Store the tasks provided by the up-buttons outside the elevator
	public static int[] task_list_down = new int[20];// Store the tasks provided by the down-buttons outside the elevator
	private int[] my_task              = new int[20];// Store the tasks provided by the buttons inside the elevator
}
