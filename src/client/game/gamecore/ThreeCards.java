package client.game.gamecore;

import client.game.nonsystem.Player;

import java.util.*;

public class ThreeCards extends Game{
    private static ThreeCards instance;

    List<Map<String, Integer>> scoreLeaderBoard = new ArrayList<>();

    private ThreeCards() {
        super();
    }

    public static ThreeCards getInstance() {
        if(instance == null) {
            instance = new ThreeCards();
        }
        return instance;
    }

    @Override
    public void addPlayer(Player player) {
        if(players.size() + 1 > 12) {
            System.out.println("Player full!");
            return;
        }
        players.add(player);
        System.out.println("Player added !");
        System.out.println("Current player: " + players.size());
    }


    public void remove10JQK() {
        // safe removal during iteration
        deck.getCards().removeIf(card -> card.getRank().getValue() >= 10 && card.getRank().getValue() <= 13);
        deck.shuffle();
    }

    @Override
    public void deal() {
        remove10JQK();
        Player current = players.poll();
        if(current == null) {
            System.out.println("No player to deal!");
        }
        else {
            while(current.getHand().size() < 3) {
                current.receiveCard(deck.dealCard());
                players.add(current);
                current = players.poll();
                if(current == null) {
                    System.out.println("No player to deal or deal finish");
                    break;
                }
            }
            players.add(current);
        }
    }

    @Override
    public void startGame() {
        deal();

        for(Player player : players) {
            int sum = 0;
            sum += player.getHand().get(0).getRank().getValue() +
                    player.getHand().get(1).getRank().getValue() +
                    player.getHand().get(2).getRank().getValue();

            Map<String, Integer> playerMap = new HashMap<>();
            if(sum % 10 == 0) playerMap.put(player.getName(), 10);
            else playerMap.put(player.getName(), sum % 10);
            scoreLeaderBoard.add(playerMap);

        }

        scoreLeaderBoard.sort(Comparator.comparingInt((Map<String, Integer> map) -> map.values().iterator().next())
                        .reversed());

        for(Map<String, Integer> m : scoreLeaderBoard) {
            String key = m.keySet().iterator().next();
            Integer value = m.get(key);
            System.out.println(key + ": " + value);
        }
    }
}
