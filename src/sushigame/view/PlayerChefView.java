package sushigame.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401.sushi.AvocadoPortion;
import comp401.sushi.CrabPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.Plate;
import comp401.sushi.RedPlate;
import comp401.sushi.RicePortion;
import comp401.sushi.Roll;
import comp401.sushi.Sashimi;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.ShrimpPortion;
import comp401.sushi.Sushi;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private int belt_size;

	private JRadioButton red_plate, green_plate, blue_plate, gold_plate, sashimi_plate, roll_plate, nigiri_plate;
	private JSlider pslider, gold_slider, eel_slider, rice_slider,avo_slider, shrimp_slider, crab_slider, seaweed_slider;
	private JLabel goldprice_view;
	private JTextField roll_name; 
	private JList<String> ing_list;

	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout (new GridLayout(0, 4));

		setView();

	}

	/* Arranges the first row of plate radio buttons
	 * and assigns action listeners and commands
	 */
	public void setPlateRadioButtons(){
		red_plate = new JRadioButton(" Red Plate");
		red_plate.addActionListener(this);
		red_plate.setActionCommand("red_plate");

		blue_plate = new JRadioButton(" Blue Plate");
		blue_plate.addActionListener(this);
		blue_plate.setActionCommand("blue_plate");

		green_plate = new JRadioButton(" Green Plate");
		green_plate.addActionListener(this);
		green_plate.setActionCommand("green_plate");

		gold_plate = new JRadioButton(" Gold Plate");	
		gold_plate.addActionListener(this);
		gold_plate.setActionCommand("gold_plate");

		ButtonGroup group = new ButtonGroup();
		group.add(red_plate);
		group.add(green_plate);
		group.add(blue_plate);
		group.add(gold_plate);

		add(red_plate);
		add(green_plate);
		add(blue_plate);
		add(gold_plate);
	}

	public void setView(){
		//First row
		setPlateRadioButtons();

		//Second row or components
		//Plate Position Slider
		pslider = new JSlider();
		pslider.setValue(0);
		JLabel platepos_view = new JLabel("Plate Position:  " + String.valueOf(pslider.getValue()));

		pslider.setMaximum(19);
		pslider.setMinimum(0);
		pslider.setMinorTickSpacing(1);
		pslider.setMajorTickSpacing(5);
		pslider.setPaintTicks(true);
		pslider.setPaintLabels(true);
		pslider.setSnapToTicks(true);

		//Show plate position on JLabel as pslider changes
		pslider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				platepos_view.setText("Plate Position:  " + String.valueOf(pslider.getValue()));
			}
		});
		add(pslider);
		add(platepos_view);

		//Gold price slider (Only enabled if gold is selected)	
		gold_slider = new JSlider();
		gold_slider.setValue(5);
		goldprice_view = new JLabel("Gold Plate Price: $ " + String.valueOf(gold_slider.getValue()));

		gold_slider.setMaximum(1000);
		gold_slider.setMinimum(500);
		gold_slider.setMinorTickSpacing(50);
		gold_slider.setMajorTickSpacing(100);
		gold_slider.setPaintTicks(true);
		gold_slider.setPaintLabels(true);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( 500 ), new JLabel("5.0") );
		labelTable.put( new Integer( 600 ), new JLabel("6.0") );
		labelTable.put( new Integer( 700 ), new JLabel("7.0") );
		labelTable.put( new Integer( 800 ), new JLabel("8.0") );
		labelTable.put( new Integer( 900 ), new JLabel("9.0") );
		labelTable.put( new Integer( 1000 ), new JLabel("10.0") );
		gold_slider.setLabelTable( labelTable );

		//Show gold price on JLabel as gold_slider changes;		
		gold_slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				goldprice_view.setText("Gold Plate Price: $ " + String.valueOf( (gold_slider.getValue()/100.0)));
			}
		});
		add(gold_slider);
		add(goldprice_view);

		//Keep gold slider disabled unless gold_plate is selected
		gold_slider.setEnabled(false);
		goldprice_view.setEnabled(false);

		/*
		 * Third row with nigiri, sashimi and roll sushi options
		 */
		nigiri_plate = new JRadioButton(" Nigiri"); 
		nigiri_plate.addActionListener(this);
		nigiri_plate.setActionCommand("nigiri_plate");

		sashimi_plate = new JRadioButton(" Sashimi");
		sashimi_plate.addActionListener(this);
		sashimi_plate.setActionCommand("sashimi_plate");

		roll_plate = new JRadioButton(" Roll");
		roll_plate.addActionListener(this);
		roll_plate.setActionCommand("roll_plate");

		//Ingredient list for Nigiri/Sashimi type
		DefaultListModel<String> nigirisashimitype = new DefaultListModel<String>();
		nigirisashimitype.addElement("TUNA");
		nigirisashimitype.addElement("SALMON");
		nigirisashimitype.addElement("EEL");
		nigirisashimitype.addElement("CRAB");
		nigirisashimitype.addElement("SHRIMP");

		ing_list = new JList<String>(nigirisashimitype);
		ing_list.setToolTipText("Choose Nigiri Type");
		ing_list.setVisibleRowCount(1);
		ing_list.setSelectedIndex(0);

		ing_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ing_list.setLayoutOrientation(JList.VERTICAL);
		ing_list.setVisibleRowCount(3);
		JScrollPane listScroller = new JScrollPane(ing_list);

		ButtonGroup group1 = new ButtonGroup();
		group1.add(nigiri_plate);
		group1.add(sashimi_plate);
		group1.add(roll_plate);

		add(nigiri_plate);
		add(sashimi_plate);
		add(roll_plate);
		add(ing_list);

		/*
		 * 4th to 8th rows are only enabled if Roll Option is selected
		 * These rows have sliders to determine each ingredients' amount
		 */
		JLabel l = new JLabel(" Roll Name: ");
		roll_name = new JTextField(1);
		roll_name.setPreferredSize(new Dimension(1, 10));
		add(l);
		add(roll_name);
		add(new JLabel("Choose Roll Ingredients Below:  ")); 
		add(new JLabel());

		//Roll Ingredient sliders
		eel_slider = new JSlider();
		setRollSlider(eel_slider, "EEL");

		crab_slider = new JSlider();
		setRollSlider(crab_slider, "CRAB");

		shrimp_slider = new JSlider();
		setRollSlider(shrimp_slider, "SHRIMP");

		seaweed_slider = new JSlider();
		setRollSlider(seaweed_slider, "SEAWEED");

		avo_slider = new JSlider();
		setRollSlider(avo_slider, "AVOCADO");

		rice_slider = new JSlider();
		setRollSlider(rice_slider, "RICE");

		/*
		 * 8th row with button to create sushi
		 * When "Create Sushi" is clicked, reset all sushi components
		 */
		JButton createSushi = new JButton("CREATE SUSHI");
		add(createSushi);
		createSushi.setActionCommand("create_sushi");
		createSushi.addActionListener(this);	
	}

	public void setRollSlider(JSlider slider, String ing){
		JLabel amount = new JLabel("  " + ing + " OUNCES: 0.00");
		slider.setMaximum(150);
		slider.setMinimum(0);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(50);
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( 0 ), new JLabel("0.00") );
		labelTable.put( new Integer( 50 ), new JLabel("0.50") );
		labelTable.put( new Integer( 100 ), new JLabel("1.00") );
		labelTable.put( new Integer( 150 ), new JLabel("1.50") );
		slider.setLabelTable( labelTable );

		slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				amount.setText("  " + ing + " OUNCES: " + String.valueOf(slider.getValue() / 100.0));
			}

		});

		add(slider); 
		add(amount);
	}

	/*
	 * The next five methods enable and disable differnet components 
	 * of the ChefView so unnecessary values are not set
	 */
	public void disableRollLabels(JLabel[] l){
		for(int i = 0; i < l.length; i++){
			l[i].setEnabled(false);
		}
	}

	public void disableGoldSlider(JSlider gold_slider, JLabel l){
		gold_slider.setEnabled(false);
		gold_slider.setValue(0);
		l.setEnabled(false);
	}

	public void enableGoldSlider(JSlider gold_slider, JLabel l){
		gold_slider.setEnabled(true);
		l.setEnabled(true);
	}

	public void disableRollSettings(JSlider[] rollsliders, JTextField rolltext, JList list){
		for(int i = 0; i < rollsliders.length; i++){
			rollsliders[i].setEnabled(false);
			rollsliders[i].setValue(0);
			rolltext.setEnabled(false);
		}
		list.setEnabled(true);

	}

	public void enableRollSettings(JSlider[] rollsliders, JTextField rolltext, JList list){
		for(int i = 0; i < rollsliders.length; i++){
			rollsliders[i].setEnabled(true);
			rolltext.setEnabled(true);
		}
		list.setEnabled(false);
	}

	public PlayerChefView(int belt_size, int i){
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	/*
	 * The next three methods create sushi by taking
	 *  in the selected ingredient as a parameter
	 */
	public Sushi NigiriSushiCreate(String type){
		Sushi nigirisushi = null;
		switch(type){
		case "EEL":
			nigirisushi = new Nigiri(Nigiri.NigiriType.EEL);
			break;
		case "TUNA":
			nigirisushi = new Nigiri(Nigiri.NigiriType.TUNA);
			break;
		case "SALMON":
			nigirisushi =  new Nigiri(Nigiri.NigiriType.SALMON);
			break;
		case "CRAB":
			nigirisushi =  new Nigiri(Nigiri.NigiriType.CRAB);
			break;
		case "SHRIMP":
			nigirisushi =  new Nigiri(Nigiri.NigiriType.SHRIMP);
			break;
		}
		return nigirisushi ;
	}

	public Sushi SashimiSushiCreate(String type){
		Sushi sashimisushi = null;
		switch(type){
		case "EEL":
			sashimisushi = new Sashimi(Sashimi.SashimiType.EEL);
			break;
		case "TUNA":
			sashimisushi = new Sashimi(Sashimi.SashimiType.TUNA);
			break;
		case "SALMON":
			sashimisushi =  new Sashimi(Sashimi.SashimiType.SALMON);
			break;
		case "CRAB":
			sashimisushi =  new Sashimi(Sashimi.SashimiType.CRAB);
			break;
		case "SHRIMP":
			sashimisushi =  new Sashimi(Sashimi.SashimiType.SHRIMP);
			break;
		}
		return sashimisushi ;
	}

	public Sushi RollSushiCreate(){

		List<IngredientPortion> ing_p = new ArrayList<IngredientPortion>();

		if(eel_slider.getValue() > 0.00) ing_p.add(new EelPortion(eel_slider.getValue() / 100.0));
		if(crab_slider.getValue() > 0.00) ing_p.add(new CrabPortion(crab_slider.getValue() / 100.0));
		if(shrimp_slider.getValue() > 0.00) ing_p.add(new ShrimpPortion(shrimp_slider.getValue() / 100.0));
		if(seaweed_slider.getValue() > 0.00) ing_p.add(new SeaweedPortion(seaweed_slider.getValue() / 100.0));
		if(rice_slider.getValue() > 0.00) ing_p.add(new RicePortion(rice_slider.getValue() / 100.0));
		if(avo_slider.getValue() > 0.00) ing_p.add(new AvocadoPortion(avo_slider.getValue() / 100.0));

		IngredientPortion[] ingp = ing_p.toArray(new IngredientPortion[ing_p.size()]);

		return new Roll(roll_name.getText(), ingp);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "gold_plate":
			enableGoldSlider(gold_slider, goldprice_view);
			break;
		case "red_plate":
			disableGoldSlider(gold_slider, goldprice_view);
			break;
		case "blue_plate":
			disableGoldSlider(gold_slider, goldprice_view);
			break;
		case "green_plate":
			disableGoldSlider(gold_slider, goldprice_view);
			break;
		case "roll_plate":
			JSlider[] l = {eel_slider, rice_slider, avo_slider, shrimp_slider, crab_slider, seaweed_slider};
			enableRollSettings(l, roll_name, ing_list);
			break;
		case "nigiri_plate":
			JSlider[] m = {eel_slider, rice_slider, avo_slider, shrimp_slider, crab_slider, seaweed_slider};
			disableRollSettings(m, roll_name, ing_list);
			break;
		case "sashimi_plate":
			JSlider[] n = {eel_slider, rice_slider, avo_slider, shrimp_slider, crab_slider, seaweed_slider};
			disableRollSettings(n, roll_name, ing_list);
			break;
		case "create_sushi":
			//For Nigiri
			if(nigiri_plate.isSelected()){
				if(gold_plate.isSelected()) makeGoldPlateRequest(NigiriSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue(), gold_slider.getValue()/100.0);
				if(red_plate.isSelected()) makeRedPlateRequest(NigiriSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue());
				if(blue_plate.isSelected()) makeBluePlateRequest(NigiriSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue());
				if(green_plate.isSelected()) makeGreenPlateRequest(NigiriSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue());
			}
			//For Sashimi
			if(sashimi_plate.isSelected()){
				if(gold_plate.isSelected()) makeGoldPlateRequest(SashimiSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue(), gold_slider.getValue()/100.0);
				if(red_plate.isSelected()) makeRedPlateRequest(SashimiSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue());
				if(blue_plate.isSelected()) makeBluePlateRequest(SashimiSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue());
				if(green_plate.isSelected()) makeGreenPlateRequest(SashimiSushiCreate(ing_list.getSelectedValue().toString()), 
						pslider.getValue());
			}
			//For Roll
			if(roll_plate.isSelected()){
				if(gold_plate.isSelected())
					makeGoldPlateRequest(RollSushiCreate(), pslider.getValue(), gold_slider.getValue()/100.0);
				if(red_plate.isSelected())
					makeRedPlateRequest(RollSushiCreate(), pslider.getValue());
				if(blue_plate.isSelected())
					makeBluePlateRequest(RollSushiCreate(), pslider.getValue());
				if(green_plate.isSelected())
					makeGreenPlateRequest(RollSushiCreate(), pslider.getValue());
			}
			break;
		}
	}
}
