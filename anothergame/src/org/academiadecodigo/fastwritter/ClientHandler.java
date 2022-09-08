package org.academiadecodigo.fastwritter;


import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler implements Runnable {

    private final PrintStream outGame;
    public int score;
    public PrintWriter outPlayer;
    private String name;
    private final Socket clientSocket;
    private Game game;
    private ArrayList<ClientHandler> players;
    private String[] winners;
    BufferedReader in;
    String[] wordToPlay;
    static int playersEnded;
    Prompt prompt;


    public ClientHandler(Socket clientSocket, Game game, String name) throws IOException {
        this.clientSocket = clientSocket;
        this.name = name;
        this.game = game;
        this.players = game.players;
        this.winners = game.winners;
        this.outPlayer = new PrintWriter(this.getClientSocket().getOutputStream(), true);
        this.outGame = new PrintStream(this.getClientSocket().getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.getClientSocket().getInputStream()));
        this.wordToPlay = game.wordToPlay;
    }

    @Override
    public void run() {

        try {
            this.prompt = new Prompt(this.clientSocket.getInputStream(), outGame);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            setName();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            ruleCountdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            startCountdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("start");
        roundStart();
        checkWinner();
        endGame();


    }


    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setName() throws IOException, InterruptedException {
        game.sendMessage(this, "Choose a name");
        this.name = in.readLine();
        if(this.name != ""){
            this.setName(this.name);}
        Thread.currentThread().setName(this.name);
        game.setUserReadyCounter((game.getUserReadyCounter() + 1));
        game.sendMessage(this, "Your chosen name is " + this.getName() + ", wait for all players to chose a name.");
        while (!(game.getUserReadyCounter() == game.MAX_PLAYERS)) {
            emptyMethod();
        }


    }
    public void emptyMethod(){
        double i = Math.random()*100;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }





    public void roundStart() {
        Set<String> current0 = new HashSet<>();

        StringInputScanner round1Word = new StringSetInputScanner(current0);
        round1Word.setError("Dumbass..");

        current0.add(wordToPlay[0]);
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET + "\n" + "\n" +"Type it fast: " + wordToPlay[0] + "\n" + "\n" + Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET+"\n");
        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[0]);
        current0.add(wordToPlay[1]);


        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +  "\n" + "                     "+ wordToPlay[1] + "\n\n" + Assets.showcaseHand + "\n\n"+ Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[1]);
        current0.add(wordToPlay[2]);

        round1Word.setError(Assets.trollface + "\n\n" + "I think something is wrong... no? \n\n");
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: " +  addStart(wordToPlay[2]) + "\n\n"  +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[2]);
        current0.add(wordToPlay[3]);

        round1Word.setError("Dumbass..");
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"Type it fast: " + wordToPlay[3] + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[3]);
        current0.add(wordToPlay[4]);


        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: " + wordToPlay[4] + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[4]);
        current0.add(wordToPlay[5]);

        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: " + wordToPlay[5] + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[5]);
        current0.add(wordToPlay[6]);

        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: " + wordToPlay[6] + "\n"+ "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[6]);
        current0.add(wordToPlay[7]);

        round1Word.setError(Assets.trollface + "\n\n" + "I think something is wrong... no? \n\n");
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ addEnd(wordToPlay[7]) + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[7]);
        current0.add(wordToPlay[8]);

        round1Word.setError("Dumbass..");
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ wordToPlay[8] + "\n" +"\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[8]);
        current0.add(wordToPlay[9]);

        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ wordToPlay[9] + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[9]);
        current0.add(wordToPlay[10]);

        round1Word.setError(Assets.girl2+"\n\n"+"Loool, try typing it in reverse :) \n\n");
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ reverse(wordToPlay[10]) + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");


        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[10]);
        current0.add(wordToPlay[11]);

        round1Word.setError(Assets.okSign);
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ wordToPlay[11] + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[11]);
        current0.add(wordToPlay[12]);

        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ wordToPlay[12] + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[12]);
        current0.add(wordToPlay[13]);

        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ wordToPlay[13] + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[13]);
        current0.add(wordToPlay[14]);

        round1Word.setError(Assets.girl + "\n\n" + "He He He!!! Try swapping first letter with last ;)  \n\n");
        round1Word.setMessage("\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n\n" +"Type it fast: "+ swappingLetter(wordToPlay[14]) + "\n" + "\n" +Colors.ANSI_PURPLE + Assets.divider + Colors.ANSI_RESET +"\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[14]);

        game.sendMessage(this,"You have finished, wait for others to finish :)");








        }




    public String reverse(String word){
        String input = word;

        StringBuilder stringVar = new StringBuilder();

        stringVar.append(input);

        stringVar.reverse();

        return String.valueOf(stringVar);
    }
    public String addEnd(String word){
        String input = word;

        return input+"¯|_( ツ )_/¯";
    }
    public String addStart(String word){
        String input = word;

        return "¯|_( ツ )_/¯"+input;
    }
    public String swappingLetter(String word) {
        String finalWord;
        if (word.length() <= 1) {
            return word;


        } else {

            char first = word.charAt(0);
            char last = word.charAt(word.length() - 1);
            String mid = word.substring(1, word.length() - 1);
            finalWord = last + mid + first;
        }


        return finalWord;
    }
    synchronized void startCountdown() throws InterruptedException {


        game.sendMessage(this,Colors.ANSI_CYAN+"Game will start in...");
        wait(1000);
        game.sendMessage(this,Colors.ANSI_GREEN + "5" + Colors.ANSI_RESET);
        wait(1000);
        game.sendMessage(this,Colors.ANSI_GREEN+"4"+ Colors.ANSI_RESET);
        wait(1000);
        game.sendMessage(this,Colors.ANSI_YELLOW + "3" + Colors.ANSI_RESET);
        wait(1000);
        game.sendMessage(this,Colors.ANSI_RED + "2" + Colors.ANSI_RESET);
        wait(1000);
        game.sendMessage(this,Colors.ANSI_RED + "1" + Colors.ANSI_RESET);
        wait(1000);

    }
    synchronized void ruleCountdown() throws InterruptedException {

        game.sendMessage(this,Assets.banner1);
        game.sendMessage(this,"\t\t" +Assets.banner2);

        game.sendMessage(this,Colors.ANSI_CYAN + "Welcome to Typewriter!" + "\n\n" + "Developed by:" + "\n" + "\n" + "Dare Sousa" + "\n" + "Mike Zhang" + "\n" + "Chavier Medeiros" + "\n\n" + Colors.ANSI_RESET);
        wait(3000);
        game.sendMessage(this,Colors.ANSI_GREEN + "It's a simple game." + "\n\n" +Colors.ANSI_RESET);
        wait(2000);
        game.sendMessage(this,Colors.ANSI_GREEN + "You are racing against 3 other players..." + "\n\n"+ Colors.ANSI_RESET);
        wait(2000);
        game.sendMessage(this,Colors.ANSI_GREEN + "We will give you a word each round" +"\n\n" +  Colors.ANSI_RESET);
        wait(2000);
        game.sendMessage(this,Colors.ANSI_GREEN + "Type it as fast as you can" + "\n\n"+ Colors.ANSI_RESET);
        wait(2000);
        game.sendMessage(this,Colors.ANSI_GREEN + "First to finish a set of words wins" +"\n\n" + Colors.ANSI_RESET);
        wait(2000);
        game.sendMessage(this,Colors.ANSI_RED + "Tip: look carefully ;)" +"\n\n" + Colors.ANSI_RESET);
        wait(2000);
    }

    void checkWinner() {
        String won = this.getName();

        if (game.winners[0] == null) {
            playersEnded++;
            game.winners[0] = won;
            return;

        }
        if (game.winners[1] == null) {
            playersEnded++;
            game.winners[1] = won;
            return;

        }
        if (game.winners[2] == null) {
            playersEnded++;
            game.winners[2] = won;
            return;

        }
        if (game.winners[3] == null) {
            playersEnded++;
            game.winners[3] = won;
        }

    }

    void endGame() {
        while (playersEnded != game.MAX_PLAYERS) {
            emptyMethod();
        }

        game.sendMessage(this,("\n\n\n\n\n\n\n\n\t\t\t\t     " +Colors.ANSI_YELLOW+ game.winners[0]));
        game.sendMessage(this,Colors.ANSI_YELLOW+Assets.podium1);

        game.sendMessage(this,("\n\n\n\n\n\n\n\n\t\t        " + Colors.ANSI_GREEN+ game.winners[1]));
        game.sendMessage(this,Colors.ANSI_GREEN+Assets.podium2);

        game.sendMessage(this,("\n\n\n\n\n\n\n\n\t        " + Colors.ANSI_RED+ game.winners[2]));
        game.sendMessage(this,Colors.ANSI_RED+Assets.podium3 + Colors.ANSI_RESET);


        game.sendMessage(this, "\n\n\n\n                                                               "+game.winners[3] + " is an absolute dogshit player :)");
    }
}

