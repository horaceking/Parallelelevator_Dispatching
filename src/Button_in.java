import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button_in extends JButton {
	public Button_in(String name, int floor, MyElevator ele) {
		button = new JButton(name);
		this.setText(name);
		floor_num = floor;
		my_ele = ele;
	}
	
	public int get_floor() {
		return floor_num;
	}
	
	public void set_task_in(int floor) {
		my_ele.set_my_task_at(floor);
	}

	private int floor_num = 0;
	JButton button = null;
	MyElevator my_ele = null;
}
