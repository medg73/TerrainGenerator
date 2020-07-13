package com.medg.terraingenerator.ui;

import com.medg.terraingenerator.HexBoard;
import com.medg.terraingenerator.dice.Dice;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerrainGeneratorFrame extends JFrame {

    private HexPanel hexPanel;
    private HexBoard hexBoard;
    private int mapHeight;
    private int mapWidth;
    private int hexSize;
    private Dice dice;
    private JSlider zoomSlider;
    private JScrollPane scrollPane;

    public TerrainGeneratorFrame(HexBoard hexBoard, Dice dice) {
        this.hexBoard = hexBoard;
        this.hexPanel = new HexPanel(hexBoard);
        this.mapHeight = hexBoard.getHexMapHeight();
        this.mapWidth = hexBoard.getHexMapWidth();
        this.hexSize = hexBoard.getHexSize();
        this.dice = dice;

        scrollPane = new JScrollPane(hexPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(hexSize);

        JPanel buttonPanel = new JPanel();

        GenNewMapAction genNewMapAction = new GenNewMapAction();
        JButton makeNewMapButton = new JButton("make new map");
        makeNewMapButton.addActionListener(genNewMapAction);
        buttonPanel.add(makeNewMapButton);

        WeatherMapAction weatherMapAction = new WeatherMapAction();
        JButton weatherMapButton = new JButton("weather map");
        weatherMapButton.addActionListener(weatherMapAction);
        buttonPanel.add(weatherMapButton);

        ToggleShowWaterLevelAction toggleShowWaterLevelAction = new ToggleShowWaterLevelAction();
        JCheckBox showWaterLevelCheckbox = new JCheckBox("show water level");
        showWaterLevelCheckbox.addActionListener(toggleShowWaterLevelAction);
        buttonPanel.add(showWaterLevelCheckbox);

        ZoomChange zoomChange = new ZoomChange();
        zoomSlider = new JSlider(1, 100, 1);
        zoomSlider.addChangeListener(zoomChange);
        buttonPanel.add(zoomSlider);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.pack();
        this.setSize(500,500);
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

    private class GenNewMapAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            hexBoard = new HexBoard(dice, mapHeight, mapWidth, hexSize);
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
}
