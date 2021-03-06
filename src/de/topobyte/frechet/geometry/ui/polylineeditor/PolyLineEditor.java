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


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.topobyte.frechet.geometry.ui.polylineeditor.action.MouseAction;
import de.topobyte.frechet.geometry.ui.polylineeditor.mousemode.MouseMode;
import de.topobyte.frechet.geometry.ui.polylineeditor.scale.Scale;
import de.topobyte.frechet.geometry.ui.polylineeditor.scale.ScaleX;
import de.topobyte.frechet.geometry.ui.polylineeditor.scale.ScaleY;

public class PolyLineEditor extends JPanel
{

	private static final long serialVersionUID = 8780613881909508056L;

	private PolyLineEditPane editPane;

	public PolyLineEditor()
	{
		editPane = new PolyLineEditPane();
		Scale scaleX = new ScaleX();
		Scale scaleY = new ScaleY();

		ScaleMouseListener scaleMouseListener = new ScaleMouseListener(
				scaleX, scaleY);
		editPane.addMouseMotionListener(scaleMouseListener);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(editPane, c);

		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 0.0;
		add(scaleX, c);

		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 1.0;
		add(scaleY, c);

		/*
		 * setup actions
		 */

		InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = getActionMap();

		inputMap.put(KeyStroke.getKeyStroke('a'), "a");
		inputMap.put(KeyStroke.getKeyStroke('s'), "s");
		inputMap.put(KeyStroke.getKeyStroke('d'), "d");
		inputMap.put(KeyStroke.getKeyStroke('f'), "f");

		MouseAction selectAction = new MouseAction("select", MouseMode.SELECT,
				editPane);
		MouseAction editAction = new MouseAction("edit", MouseMode.EDIT,
				editPane);
		MouseAction moveAction = new MouseAction("move", MouseMode.MOVE,
				editPane);
		MouseAction deleteAction = new MouseAction("delete", MouseMode.DELETE,
				editPane);

		actionMap.put("a", selectAction);
		actionMap.put("s", editAction);
		actionMap.put("d", moveAction);
		actionMap.put("f", deleteAction);
	}

	public PolyLineEditPane getEditPane()
	{
		return editPane;
	}
}
