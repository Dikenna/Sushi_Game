package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver {

	private SushiGameModel game_model;
	private JLabel display;
	private JList<String> compare_options;

	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);

		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);

		setLayout(new GridLayout(0, 1));
		add(display);

		createCompareList();

		display.setText(makeScoreboardHTML());

	}

	public void createCompareList(){

		JLabel l = new JLabel("Sorting Preference:");

		DefaultListModel<String> options = new DefaultListModel<String>();
		options.addElement("Balance (Highest to Lowest)");
		options.addElement("Consumed Food (Highest to Lowest)");
		options.addElement("Spoiled Food (Lowest to Highest)");

		compare_options = new JList<String>(options);
		compare_options.setMinimumSize(new Dimension(100, 100));
		compare_options.setPreferredSize(new Dimension(100, 100));
		compare_options.setSelectedIndex(0);

		add(l);
		add(compare_options);

	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		
		String s = compare_options.getSelectedValue();
		
		switch(s){
		case "Balance (Highest to Lowest)":
			Arrays.sort(chefs, new HighToLowBalanceComparator());
			for (Chef c : chefs) {
				sb_html += c.getName() + " $" + Math.round(c.getBalance()*100.0)/100.0 + " <br>";
			}
			break;
		case "Consumed Food (Highest to Lowest)":
			Arrays.sort(chefs, new FoodConsumedComparator());
			for (Chef c : chefs) {
				sb_html += c.getName() + " " + Math.round(c.getConsumedAmount()*100.0)/100.0 + " ounces <br>";
			}
			break;
		case "Spoiled Food (Lowest to Highest)":
			Arrays.sort(chefs, new FoodSpoiltComparator());
			for (Chef c : chefs) {
				sb_html += c.getName() + " " + Math.round(c.getSpoiledAmount()*100.0)/100.0 + " ounces <br>";
			}
			break;
		}

		return sb_html;
	}

	public void refresh() {
		display.setText(makeScoreboardHTML());		
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}
}
