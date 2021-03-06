/* This file is part of Frechet tools. 
 * 
 * Copyright (C) 2012  Sebastian Kuerten
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.topobyte.frechet.geometry.ui.polylineeditor;


import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import de.topobyte.frechet.geometry.ui.frechet.lines.FrechetDiagram;
import de.topobyte.frechet.geometry.ui.geom.Editable;
import de.topobyte.frechet.geometry.ui.lineeditor.EpsilonChangedListener;
import de.topobyte.frechet.geometry.ui.lineview.ControlledLineView;
import de.topobyte.frechet.geometry.ui.lineview.LineView;

public class FrechetDialog2 implements ContentChangedListener {

	final static int STEP_SIZE = 1;
	final static int STEP_SIZE_BIG = 10;

	private FrechetDiagram diagram = null;
	private LineView lineView = null;

	private int epsilon = 100;
	private Editable line1 = null;
	private Editable line2 = null;

	public FrechetDialog2(final Content content) {
		List<Editable> lines = content.getLines();
		if (lines.size() < 2) {
			System.out.println("not enough lines");
			return;
		}
		System.out.println("showing frechet diagram");

		line1 = lines.get(0);
		line2 = lines.get(1);

		int maxEpsilon = 300;
		final JSlider slider = new JSlider(0, maxEpsilon);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(10);
		slider.setValue(epsilon);
		slider.setBorder(new TitledBorder("epsilon"));

		diagram = new FrechetDiagram(epsilon, line1, line2);
		diagram.setBorder(new TitledBorder("Free space"));
		lineView = new LineView(epsilon, line1, line2, true, false, true, false);
		ControlledLineView controlledLineView = new ControlledLineView(lineView);
		controlledLineView.setBorder(new TitledBorder("Curves"));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = c.weighty = 1.0;
		JPanel panel = new JPanel(new GridBagLayout());
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(slider, c);

		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridy = 1;
		c.gridx = 0;
		panel.add(diagram, c);
		c.weightx = 0.0;
		c.gridx = 1;
		panel.add(controlledLineView, c);

		final JDialog dialog = new JDialog((JFrame) null, "Fréchet distance");
		dialog.setContentPane(panel);
//		dialog.setSize(900, 600);
		dialog.setSize(850, 450);
		dialog.setVisible(true);

		slider.addChangeListener(new EpsilonChangedListener(diagram));
		slider.addChangeListener(new EpsilonChangedListener(lineView));

		content.addContentChangedListener(this);

		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				content.removeContentChangedListener(FrechetDialog2.this);
			}
		});

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent e) {
				if (e.getSource() != dialog) {
					return;
				};
				MouseWheelEvent event = (MouseWheelEvent) e;

				int modifiers = event.getModifiers();
				boolean big = (modifiers & InputEvent.CTRL_MASK) != 0;

				int rotation = event.getWheelRotation();
				int value = slider.getValue();
				int newValue = value + rotation
						* (big ? STEP_SIZE_BIG : STEP_SIZE);
				slider.setValue(newValue);
			}
		}, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
	}

	@Override
	public void contentChanged() {
		diagram.updateSegmentsFromLines();
		diagram.repaint();
		lineView.repaint();
	}

}
