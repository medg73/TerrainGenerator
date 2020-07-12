package com.medg.terraingenerator.ui;

import com.medg.terraingenerator.HexBoard;
import com.medg.terraingenerator.dice.Dice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerrainGeneratorFrame extends JFrame {

    private HexPanel hexPanel;
    private HexBoard hexBoard;
    private int mapHeight;
    private int mapWidth;
    private Dice dice;

    public TerrainGeneratorFrame(HexBoard hexBoard, Dice dice) {
        this.hexBoard = hexBoard;
        this.hexPanel = new HexPanel(hexBoard);
        this.mapHeight = hexBoard.getHexMapHeight();
        this.mapWidth = hexBoard.getHexMapWidth();
        this.dice = dice;

        JScrollPane scrollPane = new JScrollPane(hexPanel);

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
            hexBoard = new HexBoard(dice, mapHeight, mapWidth);
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
}
