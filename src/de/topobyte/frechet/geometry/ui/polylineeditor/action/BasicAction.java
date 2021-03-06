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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import de.topobyte.frechet.util.ImageLoader;

public abstract class BasicAction extends AbstractAction {

	private static final long serialVersionUID = -617461996586787954L;
	private final String name;
	private final String description;
	// private final String iconPath;
	private final Icon icon;

	public BasicAction(String name, String description, String iconPath) {
		this.name = name;
		this.description = description;
		// this.iconPath = iconPath;
		icon = ImageLoader.load(iconPath);
	}

	@Override
	public Object getValue(String key) {
		if (key.equals(Action.SMALL_ICON)) {
			return icon;
		} else if (key.equals(Action.NAME)) {
			return name;
		} else if (key.equals(Action.SHORT_DESCRIPTION)) {
			return description;
		}
		return null;
	}
}
