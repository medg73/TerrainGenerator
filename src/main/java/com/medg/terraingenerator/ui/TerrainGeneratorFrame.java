package com.medg.terraingenerator.ui;

import com.medg.terraingenerator.hexboard.HexBoard;
import com.medg.terraingenerator.hexboard.HexBoardFactory;
import com.medg.terraingenerator.dice.Dice;

import javax.swing.*;
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
    private int mapHeight = 10;
    private int mapWidth = 5;
    private int hexSize = 40;
    private Dice dice;
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


        MakeMapPanel makeMapPanel = new MakeMapPanel(hexBoardFactory, hexPanel, scrollPane);
        ButtonPanel buttonPanel = new ButtonPanel(hexPanel, makeMapPanel, scrollPane);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(buttonPanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);
        this.pack();
        this.setSize(1000,1000);
        this.setVisible(true);
    }
}
