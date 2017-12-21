package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import comp401.sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver, ActionListener {

	private Belt belt;
	private JLabel[] belt_labels;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);

		setLayout(new GridLayout(belt.getSize(), 1));

		belt_labels = new JLabel[belt.getSize()];

		for (int i = 0; i < belt.getSize(); i++) {

			JLabel plabel = new JLabel("");
			plabel.setMinimumSize(new Dimension(300, 20));
			plabel.setPreferredSize(new Dimension(300, 20));
			plabel.setOpaque(true);
			plabel.setBackground(Color.GRAY);

			add(plabel);
			belt_labels[i] = plabel;

			int present_plate = i;

			belt_labels[i].addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					PlateWidget pw = new PlateWidget(belt, present_plate);
					pw.SetWidget();
					pw.openWidget();
				}

			});

		}

		refresh();

	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JLabel plabel = belt_labels[i];

			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.GRAY);
				plabel.setToolTipText("");
			} else {
				plabel.setText("(" + i + ")  "  + p.getChef().getName() + "'s " + p.getContents().getName());

				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED); break;
				case GREEN:
					plabel.setBackground(Color.GREEN); break;
				case BLUE:
					plabel.setBackground(Color.BLUE); break;
				case GOLD:
					plabel.setBackground(Color.YELLOW); break;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

