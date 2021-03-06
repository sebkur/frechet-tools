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


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import de.topobyte.frechet.geometry.ui.geom.Coordinate;
import de.topobyte.frechet.geometry.ui.geom.Editable;
import de.topobyte.frechet.geometry.ui.polylineeditor.mousemode.MouseMode;

public class EditorMouseListener extends MouseAdapter
{
	private final PolyLineEditPane editPane;

	public EditorMouseListener(PolyLineEditPane editPane)
	{
		this.editPane = editPane;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		super.mouseClicked(e);

		Coordinate coord = new Coordinate(e.getX(), e.getY());

		if (editPane.getMouseMode() == MouseMode.EDIT) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				addCoordinateOrCreateNewLine(coord);
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				finishCurrentLine();
			}
		}
		if (editPane.getMouseMode() == MouseMode.SELECT) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				selectLine(coord);
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				finishCurrentLine();
			}
		}
	}

	private void addCoordinateOrCreateNewLine(Coordinate coord)
	{
		Editable line = editPane.getContent().getEditingLine();
		if (line == null) {
			line = new Editable();
			editPane.getContent().setEditingLine(line);
		}
		line.addPoint(coord);
		editPane.repaint();
	}

	private void finishCurrentLine()
	{
		Editable line = editPane.getContent().getEditingLine();
		if (line == null) {
			return;
		}
		Content content = editPane.getContent();
		content.addLine(line);
		editPane.getContent().setEditingLine(null);
		editPane.repaint();
	}

	private void selectLine(Coordinate coord)
	{
		Set<Editable> near = editPane.getContent().getEditablesNear(coord);
		if (near.size() > 0) {
			Editable editable = near.iterator().next();
			editPane.getContent().changeEditingLine(editable);
			editPane.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		super.mousePressed(e);
		
		Coordinate coord = new Coordinate(e.getX(), e.getY());
		
		if (editPane.getMouseMode() == MouseMode.MOVE) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				activateNodeForMove(coord);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		super.mouseReleased(e);
		
		if (editPane.getMouseMode() == MouseMode.MOVE) {
			currentMoveEditable = null;
		}
	}

	/*
	 * movement of nodes
	 */

	private Editable currentMoveEditable = null;
	private int currentMoveNodeId = 0;

	private void activateNodeForMove(Coordinate coord)
	{
		Set<Editable> near = editPane.getContent().getEditablesNear(coord);
		if (near.size() == 0) {
			currentMoveEditable = null;
			return;
		}
		Editable editable = near.iterator().next();
		System.out.println("pushed line: " + editable);
		int nodeId = editable.getNearestPointWithinThreshold(coord, 4);
		if (nodeId == -1) {
			currentMoveEditable = null;
			return;
		}
		System.out.println(nodeId);
		currentMoveEditable = editable;
		currentMoveNodeId = nodeId;
		editPane.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		Coordinate coord = new Coordinate(e.getX(), e.getY());

		if (editPane.getMouseMode() == MouseMode.MOVE) {
			if (currentMoveEditable != null){
				currentMoveEditable.setCoordinate(currentMoveNodeId, coord);
				editPane.getContent().fireContentChanged();
			}
		}
	}

}
