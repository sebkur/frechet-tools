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

import geometry.serialization.util.FileFormat;
import geometry.serialization.util.GeometrySerializer;
import geometry.serialization.util.GeometrySerializerFactory;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;

import de.topobyte.frechet.geometry.ui.geom.Editable;
import de.topobyte.frechet.geometry.ui.polylineeditor.Content;
import de.topobyte.frechet.util.SwingUtil;

public class SaveAction extends BasicAction {
	private static final long serialVersionUID = -4452993048850158926L;

	static final Logger logger = LoggerFactory.getLogger(SaveAction.class);

	private final Content content;
	private final JComponent component;

	public SaveAction(JComponent component, Content content) {
		super("Save", "Save the current line to a file",
				"org/freedesktop/tango/22x22/actions/document-save.png");
		this.component = component;
		this.content = content;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Editable line = content.getEditingLine();
		if (line == null) {
			return;
		}

		JFrame frame = SwingUtil.getContainingFrame(component);
		JFileChooser chooser = new JFileChooser();
		int value = chooser.showSaveDialog(frame);
		if (value == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			logger.debug("attempting to write line to file: " + file);
			try {
				write(line, file);
			} catch (IOException e) {
				logger.debug("unable to write file.");
				logger.debug("Exception type: " + e.getClass().getSimpleName());
				logger.debug("Exception message: " + e.getMessage());
			}
		}
	}

	private void write(Editable line, File file) throws IOException {
		Geometry geometry = line.createGeometry();
		GeometrySerializer serializer = GeometrySerializerFactory
				.getInstance(FileFormat.WKT);
		serializer.serialize(geometry, file);
	}

}
