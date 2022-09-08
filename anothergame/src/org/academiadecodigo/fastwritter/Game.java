package org.academiadecodigo.fastwritter;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    public final int FINAL_SCORE = 15;
    public final int MAX_PLAYERS = 4;
    public String[] winners = new String[MAX_PLAYERS];
    private ServerSocket serverSocket;
    private final int port;
    public ArrayList<ClientHandler> players = new ArrayList<>();
    private int userReadyCounter;
    ExecutorService playerThreads;
    LinkedList<String> wordOcean = new LinkedList<>(Arrays.asList("colonel", "scissors", "quinoa", "address", "intelligence", "weird", "harass", "broadcast", "scarce", "inspire", "temperature", "specific", "suburban", "broccoli", "vacuum", "bourbon", "nauseous", "grateful", "lightning", "deviation", "congress", "wind", "pavement", "monstrous", "reception", "stride", "inhibition", "socialist", "discrimination", "approval", "answer", "gregarious", "dominate", "strikebreaker", "nomination", "technology", "conversation", "contraction", "dome", "possibility", "sunshine", "punish", "timetable", "accessible", "unrest", "spirit", "policeman", "utter", "weapon", "shortage", "experience", "audience", "operation", "emphasis", "credibility", "flourish", "majority", "vertical", "pumpkin", "version", "ecstasy", "steward", "healthy", "alcohol", "nightmare", "timetable", "digress", "measure", "marble", "witness", "restaurant", "disappear", "mosquito", "landowner", "landmower", "scenario", "industry", "shiver", "tragedy", "impound", "available", "transition", "demonstration", "paragraph", "prevalence", "joystick", "pornhub", "qualified", "wilderness", "survival", "enfix", "fortune", "mutual", "theory", "pattern", "premature", "temptation", "brainstorm", "empirical", "scramble", "elaborate", "judge", "characteristic", "cemetery", "recovery", "snatch", "sensitivity", "flourish", "electron", "pneumonia"));
    public String[] wordToPlay;

    public int getUserReadyCounter() {
        return userReadyCounter;
    }

    public void setUserReadyCounter(int userReadyCounter) {
        this.userReadyCounter = userReadyCounter;
    }

    public Game(int port) {
        this.port = port;
        this.wordToPlay = wordToCompare();

    }

    public void listen() {
        ClientHandler clientHandler;
        try {
            int connections = 0;
            this.serverSocket = new ServerSocket(this.port);

            playerThreads = Executors.newFixedThreadPool(MAX_PLAYERS);
            while (connections != MAX_PLAYERS) {
                Socket clientSocket = this.serverSocket.accept();

                clientHandler = new ClientHandler(clientSocket, this, "Player " + connections);
                connections++;


                String connectionMessage = connections + "/" + MAX_PLAYERS + " players connected.";
                broadCast(connectionMessage);
                players.add(clientHandler);

            }

            for (ClientHandler player : players) {
                playerThreads.submit(player);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    String[] wordToCompare() {
        LinkedList<String> wordToPlay = new LinkedList<>();
        for (int j = 0; j < FINAL_SCORE; j++) {
            String word = generateWord();

            while (wordToPlay.contains(word) && !wordToPlay.isEmpty()) {
                word = generateWord();
            }
            wordToPlay.add(word);
        }

        return wordToPlay.toArray(new String[0]);
    }

    private String generateWord() {
        int rand = (int) Math.abs(Math.random() * wordOcean.size());
        return wordOcean.get(rand);
    }


    public synchronized void broadCast(String message) {
        for (ClientHandler player : this.players) {

            try {
                player.outPlayer.println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    synchronized void sendMessage(ClientHandler player, String message) {
        for (ClientHandler player1 : this.players) {

            if (player == player1) {
                try {
                    player.outPlayer.println(message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
