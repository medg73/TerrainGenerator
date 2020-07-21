package com.medg.terraingenerator.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

    private JSlider zoomSlider;
    private HexPanel hexPanel;
    private MakeMapPanel makeMapPanel;
    private JScrollPane scrollPane;
    private int minZoom = 1;
    private int maxZoom = 10;

    ButtonPanel(HexPanel hexPanel, MakeMapPanel makeMapPanel, JScrollPane scrollPane) {
        this.hexPanel = hexPanel;
        this.scrollPane = scrollPane;
        this.makeMapPanel = makeMapPanel;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(makeMapPanel);

        AddRandomTerrainAction addRandomTerrainAction = new AddRandomTerrainAction();
        JButton addRandomTerrainButton = new JButton("add random terrain");
        addRandomTerrainButton.addActionListener(addRandomTerrainAction);
        this.add(addRandomTerrainButton);

        WeatherMapAction weatherMapAction = new WeatherMapAction();
        JButton weatherMapButton = new JButton("weather map");
        weatherMapButton.addActionListener(weatherMapAction);
        this.add(weatherMapButton);

        ToggleShowWaterLevelAction toggleShowWaterLevelAction = new ToggleShowWaterLevelAction();
        JCheckBox showWaterLevelCheckbox = new JCheckBox("show water level");
        showWaterLevelCheckbox.addActionListener(toggleShowWaterLevelAction);
        this.add(showWaterLevelCheckbox);

        ToggleSelectAction toggleSelectAction = new ToggleSelectAction();
        JCheckBox toggleSelectCheckbox = new JCheckBox("click to select");
        toggleSelectCheckbox.addActionListener(toggleSelectAction);
        this.add(toggleSelectCheckbox);

        JLabel zoomLabel = new JLabel("zoom");
        this.add(zoomLabel);

        ZoomChange zoomChange = new ZoomChange();
        zoomSlider = new JSlider(minZoom, maxZoom, minZoom);
        zoomSlider.addChangeListener(zoomChange);
        this.add(zoomSlider);

    }

    private class AddRandomTerrainAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hexPanel.addRandomTerrain();
            hexPanel.repaint();
        }
    }

    private class WeatherMapAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hexPanel.weatherMap();
            hexPanel.repaint();
        }
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

    private class ZoomChange implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            hexPanel.setZoomFactor(zoomSlider.getValue());
            hexPanel.repaint();
        }
    }

}
