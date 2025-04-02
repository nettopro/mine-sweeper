package cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import cm.controller.ExitException;
import cm.controller.ExplosionException;
import cm.model.Board;

public class BoardConsole {
    private Board board;
    private Scanner input = new Scanner(System.in);

    public BoardConsole(Board board){
        this.board = board;
        startGame();
    }

    private void startGame() {
        try{
            boolean keepPlaying = true;

            while(keepPlaying){
                gameLoop();

                System.out.println("Rematch? (Y/N)");
                String answer = input.nextLine();

                if("n".equalsIgnoreCase(answer)){
                    keepPlaying = false;
                } else {
                    board.restart();
                }
            }
        } catch (ExitException e) {
            System.out.println("Exiting Game.");
        } finally { 
            input.close(); 
        }
    }

    private void gameLoop() {
        try {
            while(!board.objectiveReached()){
                System.out.println(board);
                    
                String typed = getTypedValue("Type (x, y): ");
                    
                Iterator<Integer> xy = Arrays.stream(typed.split(","))
                    .map(i -> Integer.parseInt(i.trim())).iterator();

                typed = getTypedValue("1 - Clear or 2 - (Un)flag: ");
                if("1".equals(typed)){
                    board.clear(xy.next(),xy.next());
                } else if("2".equals(typed)){
                    board.flag(xy.next(),xy.next());
                }
            }
            System.out.println(board);
            System.out.println("You've won!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("You lost!");
        }
    }

    private String getTypedValue(String text){
        System.out.println(text);
        String typed = input.nextLine();

        if("exit".equalsIgnoreCase(typed)){
            throw new ExitException();
        }

        return typed;
    }
}
