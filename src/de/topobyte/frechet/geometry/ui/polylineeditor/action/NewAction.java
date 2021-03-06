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

package de.topobyte.frechet.geometry.ui.polylineeditor.action;


import java.awt.event.ActionEvent;

import de.topobyte.frechet.geometry.ui.polylineeditor.Content;

public class NewAction extends BasicAction
{

	private static final long serialVersionUID = -4452993048850158926L;
	
	private final Content content;

	public NewAction(Content content)
	{
		super("New", "Start with a fresh and empty document",
				"org/freedesktop/tango/22x22/actions/document-new.png");
		this.content = content;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		content.setEditingLine(null);
		content.getLines().clear();
		content.fireContentChanged();
	}

}
