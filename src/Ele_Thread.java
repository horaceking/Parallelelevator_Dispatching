import java.awt.Color;

/*
 * *******************************************************
 * Class Name: Ele_Thread
 * *******************************************************
 * Class Major Function 
 * *Do the work of elevators
 * *******************************************************
 * Post: Extends to GlobalInstants to get the authority to control buttons
 */
public class Ele_Thread implements Runnable{
	
	// Constants
	private static final int DELAY = 500;	  // The Delay time after this thread 
	private static final int WAIT_TIME = 2000;// The time this elevator needed to keep open when it reach a target
	
	// Instructor: build a relationship to elevator
	public Ele_Thread(MyElevator ele) {
		elevator = ele;
	}
	
	// Elevator work
	public void run(){

		while(true){
			
			// lock the public static resources task_list_up and task_list_down
			synchronized(elevator.task_list_up){
				synchronized(elevator.task_list_down) {	
					
					
					if(elevator.get_next_floor() == 0){												// If find no next target floor
						elevator.set_des(0);															//elevator stop
					}else if(elevator.get_next_floor() == elevator.get_pos()) {						// If next target is this floor
						int remove_type = elevator.remove_task();									    // 1.Remove task and change 
						global.button_in[elevator.ele_name][elevator.get_pos()-1].							    // 2.buttons related to black
						setForeground(Color.black);														// 3.wait for a while
						
						// Change one or two buttons' color to black up to remove_type
						if(remove_type != 0){
							global.button_out[40 - 2 * elevator.get_pos() + (1 + elevator.get_des()) / 2].
							setForeground(Color.black);
							
						}else{
							global.button_out[40 - 2 * elevator.get_pos()].setForeground(Color.black);
							global.button_out[40 - 2 * elevator.get_pos() + 1].setForeground(Color.black);
						}
						//Change the wait_flag to true, so that this elevator will keep open for a while
						wait_flag = true;
					}else if(elevator.get_next_floor() > elevator.get_pos()) {						// If target is higher than this floor
						elevator.set_des(elevator.UP);												//Move up
						elevator.move(elevator.UP);
					}else {																			// If target is lower than this floor
						elevator.set_des(elevator.DOWN);											// Move down
						elevator.move(elevator.DOWN);									
					}
				}//task_list_down unlock
			}//task_list_up unlock
			
			// Keep open for a while 
			if(wait_flag){
				try{
					Thread.sleep(WAIT_TIME);
				}catch (InterruptedException e){}
				elevator.repaint();
				wait_flag = false;
			}
			
			// Delay
			try{
				Thread.sleep(DELAY);
			}catch (InterruptedException e){
				System.out.println(e.toString());
				}
		}
		
	}
	
	// instant variables
	private boolean wait_flag = false; // a flag stand for whether the elevator reach the target floor
	private MyElevator elevator = null; // the elevator controlled by this thread
	private GlobalInstants global = new GlobalInstants();
}
