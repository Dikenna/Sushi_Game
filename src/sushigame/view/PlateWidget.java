package sushigame.view;

import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import comp401.sushi.Plate;
import sushigame.model.Belt;

public class PlateWidget extends JPanel{

	private Belt belt_;
	private JFrame WidgetView;


	public PlateWidget(Belt belt, int position){
		belt_ = belt;
		Plate plate = belt.getPlateAtPosition(position);

		WidgetView = new JFrame();

		setLayout(new GridLayout(0, 1));

		//For some space at the top of the window
		add(new JLabel());

		JLabel plate_chef = new JLabel("Plate Chef: " + plate.getChef().getName());
		add(plate_chef);

		JLabel plate_age =  new JLabel("Plate Age: " + String.valueOf(belt.getAgeOfPlateAtPosition(position)));
		add(plate_age);

		//If plate is nigiri or salami
		if(plate.getContents().getName().contains("nigiri") || plate.getContents().getName().contains("sashimi")){
			JLabel plate_type = new JLabel("Plate Type: " + belt.getPlateAtPosition(position).getContents().getName());
			add(plate_type);
		}

		//If plate is roll
		if(!plate.getContents().getName().contains("nigiri") && !plate.getContents().getName().contains("sashimi")){
			JLabel roll_type = new JLabel("Roll Name: " + belt.getPlateAtPosition(position).getContents().getName());
			add(roll_type);

			//Create list for Roll ingredients
			DefaultListModel<String> roll_ingredients = new DefaultListModel<String>();

			for (int i = 0; i < plate.getContents().getIngredients().length; i++){
				roll_ingredients.addElement((plate.getContents().getIngredients()[i].getAmount()*100.0)/100.0 + " ounces of "
						+ plate.getContents().getIngredients()[i].getName());
			}

			JList<String> Roll_list = new JList(roll_ingredients);
			Roll_list.setVisibleRowCount(3);
			Roll_list.setSize(200, 50);

			add(Roll_list);
		}

	}

	public void openWidget(){
		WidgetView.pack();
		WidgetView.setLocation(MouseInfo.getPointerInfo().getLocation());
		WidgetView.setVisible(true);
	}

	public void SetWidget(){
		WidgetView.add(this);

		WidgetView.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) WidgetView.dispose();		
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});

	}

}
