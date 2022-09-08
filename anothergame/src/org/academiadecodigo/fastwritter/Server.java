package org.academiadecodigo.fastwritter;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Server {
    public static void main(String[] args) throws IOException {

        final int PORT = 6969;
         Game game = new Game(PORT);
         System.out.println("Running on port: " + PORT);
         game.listen();

    }
}
