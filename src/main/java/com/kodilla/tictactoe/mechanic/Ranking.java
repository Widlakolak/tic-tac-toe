package com.kodilla.tictactoe.mechanic;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Ranking {
    private final File rankingFile = new File("Ranking.list");
    private final Map<String, Integer> ranking = new HashMap<>();

    public Ranking() {
        loadMap();
    }

    public void saveMap() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(rankingFile));
            oos.writeObject(ranking);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving ranking: " + e.getMessage());
        }
    }

    public void loadMap() {
        if (rankingFile.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rankingFile));
                Object readMap = ois.readObject();
                if (readMap instanceof HashMap) {
                    ranking.putAll((HashMap<String, Integer>) readMap);
                }
                ois.close();
            } catch (Exception e) {
                System.out.println("Error loading ranking: " + e.getMessage());
            }
        } else {
            ranking.put("X", 0);
            ranking.put("O", 0);
            ranking.put("Draw", 0);
        }
    }

    public void updateRanking(String player) {
        ranking.put(player, ranking.getOrDefault(player, 0) + 1);
        saveMap();
    }

    public void displayRanking() {
        System.out.println("Current Ranking:");
        System.out.println("X Wins: " + ranking.getOrDefault("X", 0));
        System.out.println("O Wins: " + ranking.getOrDefault("O", 0));
        System.out.println("Draws: " + ranking.getOrDefault("Draw", 0));
    }

    public void displayRankingFX(Stage stage) {
        if (stage == null || !stage.isShowing()) {
            stage = new Stage();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle("Current Ranking");
        alert.setHeaderText(null);
        alert.setContentText(getRankingText());
        alert.showAndWait();
    }

    public String getRankingText() {
        return "X Wins: " + ranking.getOrDefault("X", 0) + "\n"
                + "O Wins: " + ranking.getOrDefault("O", 0) + "\n"
                + "Draws: " + ranking.getOrDefault("Draw", 0);
    }

}

