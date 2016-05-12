import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button_out extends JButton {
	public Button_out(String name, int floor, int destination) {
		button = new JButton(name);
		this.setText(name);
		floor_num = floor;
		des = destination;
	}
	
	public int get_floor() {
		return floor_num;
	}
	
	public int get_des() {
		return des;
	}

	private int floor_num = 0;
	private int des = 0;
	JButton button = null;
}
