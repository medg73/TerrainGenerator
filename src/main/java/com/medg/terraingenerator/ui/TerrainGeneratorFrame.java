package com.medg.terraingenerator.ui;

import com.medg.terraingenerator.hexboard.HexBoard;
import com.medg.terraingenerator.hexboard.HexBoardFactory;
import com.medg.terraingenerator.dice.Dice;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class TerrainGeneratorFrame extends JFrame {

    private HexPanel hexPanel;
    private HexBoardFactory hexBoardFactory;
    private HexBoard hexBoard;
    private int mapHeight = 100;
    private int mapWidth = 100;
    private int hexSize = 40;
    private Dice dice;
    private JSlider zoomSlider;
    private JScrollPane scrollPane;

    public TerrainGeneratorFrame(HexBoardFactory hexBoardFactory, Dice dice) {
        this.hexBoardFactory = hexBoardFactory;
        this.hexBoard = hexBoardFactory.makeHexBoard(mapHeight, mapWidth, hexSize);
        this.hexPanel = new HexPanel(hexBoard);
        this.mapHeight = hexBoard.getHexMapHeight();
        this.mapWidth = hexBoard.getHexMapWidth();
        this.hexSize = hexBoard.getHexSize();
        this.dice = dice;

        hexPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                hexPanel.getBorder()));

        scrollPane = new JScrollPane(hexPanel);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                scrollPane.getBorder()));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        JPanel makeMapPanel = new JPanel();
        makeMapPanel.setLayout(new BoxLayout(makeMapPanel, BoxLayout.LINE_AXIS));
        makeMapPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                makeMapPanel.getBorder()));

        GenNewMapAction genNewMapAction = new GenNewMapAction();
        JButton makeNewMapButton = new JButton("make new map");
        makeNewMapButton.addActionListener(genNewMapAction);
        makeNewMapButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                makeNewMapButton.getBorder()));
        makeMapPanel.add(makeNewMapButton);


        JLabel heightLabel = new JLabel("map height");
        heightLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                heightLabel.getBorder()));
        makeMapPanel.add(heightLabel);

        NumberFormat mapSizeFormat = NumberFormat.getIntegerInstance();
        JFormattedTextField mapHeightField = new JFormattedTextField(mapSizeFormat);
        mapHeightField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                mapHeightField.getBorder()));
        mapHeightField.setColumns(4);
        mapHeightField.setMaximumSize(new Dimension(100, 20));
        MapHeightChangeListener mapHeightChangeListener = new MapHeightChangeListener();
        mapHeightField.setValue(mapHeight);
        mapHeightField.addPropertyChangeListener(mapHeightChangeListener);
        makeMapPanel.add(mapHeightField);

        JLabel widthLabel = new JLabel("map width");
        makeMapPanel.add(widthLabel);

        JFormattedTextField mapWidthField = new JFormattedTextField(mapSizeFormat);
        mapWidthField.setColumns(4);
        mapWidthField.setMaximumSize(new Dimension(100, 20));
        MapWidthChangeListener mapWidthChangeListener = new MapWidthChangeListener();
        mapWidthField.setValue(mapWidth);
        mapWidthField.addPropertyChangeListener(mapWidthChangeListener);
        makeMapPanel.add(mapWidthField);

        buttonPanel.add(makeMapPanel);

        WeatherMapAction weatherMapAction = new WeatherMapAction();
        JButton weatherMapButton = new JButton("weather map");
        weatherMapButton.addActionListener(weatherMapAction);
        buttonPanel.add(weatherMapButton);

        ToggleShowWaterLevelAction toggleShowWaterLevelAction = new ToggleShowWaterLevelAction();
        JCheckBox showWaterLevelCheckbox = new JCheckBox("show water level");
        showWaterLevelCheckbox.addActionListener(toggleShowWaterLevelAction);
        buttonPanel.add(showWaterLevelCheckbox);

        ToggleSelectAction toggleSelectAction = new ToggleSelectAction();
        JCheckBox toggleSelectCheckbox = new JCheckBox("click to select");
        toggleSelectCheckbox.addActionListener(toggleSelectAction);
        buttonPanel.add(toggleSelectCheckbox);

        JLabel zoomLabel = new JLabel("zoom");
        buttonPanel.add(zoomLabel);

        ZoomChange zoomChange = new ZoomChange();
        zoomSlider = new JSlider(1, 100, 1);
        zoomSlider.addChangeListener(zoomChange);
        buttonPanel.add(zoomSlider);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(buttonPanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);
        this.pack();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private class ToggleShowWaterLevelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(hexPanel.getShowWaterLevel()) {
                hexPanel.setShowWaterLevel(false);
            } else {
                hexPanel.setShowWaterLevel(true);
            }
            hexPanel.repaint();
        }
    }

    private class ToggleSelectAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(hexPanel.getClickToSelect()) {
                hexPanel.setClickToSelect(false);
            } else {
                hexPanel.setClickToSelect(true);
            }
        }
    }

    private class GenNewMapAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            hexBoard = hexBoardFactory.makeHexBoard(mapWidth, mapHeight, hexSize);
            hexPanel.loadNewBoard(hexBoard);
        }

    }

    private class WeatherMapAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hexBoard.weather();
            hexPanel.repaint();
        }
    }

    private class ZoomChange implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            hexPanel.setZoomFactor(zoomSlider.getValue());
            hexPanel.repaint();
        }
    }

    private class MapHeightChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JFormattedTextField heightField = (JFormattedTextField)evt.getSource();
            mapHeight = ((Number)heightField.getValue()).intValue();
        }
    }

    private class MapWidthChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JFormattedTextField widthField = (JFormattedTextField)evt.getSource();
            mapWidth = ((Number)widthField.getValue()).intValue();
        }
    }
}
