package com.medg.terraingenerator.ui;

import com.medg.terraingenerator.hexboard.HexBoard;
import com.medg.terraingenerator.hexboard.HexBoardFactory;
import com.medg.terraingenerator.hexlib.Orientation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class MakeMapPanel extends JPanel {

    private int hexMapHeight = 10;
    private int hexMapWidth = 5;
    private HexPanel hexPanel;
    private JScrollPane scrollPane;
    private HexBoardFactory hexBoardFactory;
    private int hexSize = 40;
    private Orientation orientation;

    MakeMapPanel(HexBoardFactory hexBoardFactory, HexPanel hexPanel, JScrollPane scrollPane) {
        this.hexBoardFactory = hexBoardFactory;
        this.orientation = HexBoardFactory.DefaultOrientation;
        this.hexPanel = hexPanel;
        this.scrollPane = scrollPane;

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                this.getBorder()));

        GenNewMapAction genNewMapAction = new GenNewMapAction();
        JButton makeNewMapButton = new JButton("make new map");
        makeNewMapButton.addActionListener(genNewMapAction);
        makeNewMapButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                makeNewMapButton.getBorder()));
        this.add(makeNewMapButton);

        JLabel heightLabel = new JLabel("map height");
        heightLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                heightLabel.getBorder()));
        this.add(heightLabel);

        NumberFormat mapSizeFormat = NumberFormat.getIntegerInstance();
        JFormattedTextField mapHeightField = new JFormattedTextField(mapSizeFormat);
        mapHeightField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                mapHeightField.getBorder()));
        mapHeightField.setColumns(4);
        mapHeightField.setMaximumSize(new Dimension(100, 20));
        MapHeightChangeListener mapHeightChangeListener = new MapHeightChangeListener();
        mapHeightField.setValue(hexMapHeight);
        mapHeightField.addPropertyChangeListener(mapHeightChangeListener);
        this.add(mapHeightField);

        JLabel widthLabel = new JLabel("map width");
        this.add(widthLabel);

        JFormattedTextField mapWidthField = new JFormattedTextField(mapSizeFormat);
        mapWidthField.setColumns(4);
        mapWidthField.setMaximumSize(new Dimension(100, 20));
        MapWidthChangeListener mapWidthChangeListener = new MapWidthChangeListener();
        mapWidthField.setValue(hexMapWidth);
        mapWidthField.addPropertyChangeListener(mapWidthChangeListener);
        this.add(mapWidthField);

        ToggleOrientationAction toggleOrientationAction = new ToggleOrientationAction();
        JCheckBox orientationCheckBox = new JCheckBox("flat side up?");
        orientationCheckBox.addActionListener(toggleOrientationAction);
        this.add(orientationCheckBox);

    }

    private class ToggleOrientationAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(orientation.equals(Orientation.LAYOUT_POINTY)) {
                orientation = Orientation.LAYOUT_FLAT;
            } else {
                orientation = Orientation.LAYOUT_POINTY;
            }
        }
    }

    private class GenNewMapAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            HexBoard hexBoard = hexBoardFactory.makeHexBoard(hexMapHeight, hexMapWidth, hexSize, orientation);
            hexPanel.loadNewBoard(hexBoard);
            hexPanel.repaint();
            scrollPane.updateUI();
        }

    }

    private class MapHeightChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JFormattedTextField heightField = (JFormattedTextField)evt.getSource();
            hexMapHeight = ((Number)heightField.getValue()).intValue();
        }
    }

    private class MapWidthChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JFormattedTextField widthField = (JFormattedTextField)evt.getSource();
            hexMapWidth = ((Number)widthField.getValue()).intValue();

        }
    }

}
