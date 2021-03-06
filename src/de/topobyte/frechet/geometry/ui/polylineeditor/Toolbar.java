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


import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import de.topobyte.frechet.geometry.ui.polylineeditor.action.Frechet2Action;
import de.topobyte.frechet.geometry.ui.polylineeditor.action.FrechetAction;
import de.topobyte.frechet.geometry.ui.polylineeditor.action.LoadAction;
import de.topobyte.frechet.geometry.ui.polylineeditor.action.MouseAction;
import de.topobyte.frechet.geometry.ui.polylineeditor.action.NewAction;
import de.topobyte.frechet.geometry.ui.polylineeditor.action.SaveAction;
import de.topobyte.frechet.geometry.ui.polylineeditor.mousemode.MouseMode;
import de.topobyte.frechet.geometry.ui.polylineeditor.mousemode.MouseModeProvider;

public class Toolbar extends JToolBar
{

	private static final long serialVersionUID = 8604389649262908523L;

	public Toolbar(Content content, MouseModeProvider mouseModeProvider)
	{	
		NewAction newAction = new NewAction(content);
		LoadAction loadAction = new LoadAction(this, content);
		SaveAction saveAction = new SaveAction(this, content);
		FrechetAction frechetAction = new FrechetAction(content);
		Frechet2Action frechet2Action = new Frechet2Action(content);
		
		MouseAction selectAction = new MouseAction("select", MouseMode.SELECT,
				mouseModeProvider);
		MouseAction editAction = new MouseAction("edit", MouseMode.EDIT,
				mouseModeProvider);
		MouseAction moveAction = new MouseAction("move", MouseMode.MOVE,
				mouseModeProvider);
		MouseAction deleteAction = new MouseAction("delete", MouseMode.DELETE,
				mouseModeProvider);

		JToggleButton buttonSelect = new JToggleButton(selectAction);
		JToggleButton buttonEdit = new JToggleButton(editAction);
		JToggleButton buttonMove = new JToggleButton(moveAction);
		JToggleButton buttonDelete = new JToggleButton(deleteAction);

		add(newAction);
		add(loadAction);
		add(saveAction);
		add(buttonSelect);
		add(buttonEdit);
		add(buttonMove);
		add(buttonDelete);
		add(frechetAction);
		add(frechet2Action);
	}
}
