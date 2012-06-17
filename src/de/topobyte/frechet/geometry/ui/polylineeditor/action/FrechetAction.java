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
import java.util.List;

import de.topobyte.frechet.geometry.ui.geom.Editable;
import de.topobyte.frechet.geometry.ui.polylineeditor.Content;
import de.topobyte.frechet.geometry.ui.polylineeditor.FrechetDialog;

public class FrechetAction extends BasicAction
{

	private final Content content;

	public FrechetAction(Content content)
	{
		super("frechet", "show frechet diagram", null);
		this.content = content;
	}

	private static final long serialVersionUID = -942636484363711122L;

	@Override
	public void actionPerformed(ActionEvent e)
	{
		List<Editable> lines = content.getLines();
		if (lines.size() < 2) {
			System.out.println("not enough lines");
			return;
		}
		new FrechetDialog(content);
	}

}
