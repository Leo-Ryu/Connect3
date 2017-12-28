package com.example.leomr.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This is game of tic tac toe, where opponents take turns placing a yellow or red counter into a
 * 3 x 3 grid. The first player to get three in a row wins.
 */

public class MainActivity extends AppCompatActivity {

    private int yellow;
    private int red;
    private int activePlayer;

    private boolean gameIsActive;

    private int unplayed;
    private int[] gameState;

    private int[][] winningPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yellow = 0;
        red = 1;
        activePlayer = yellow;
        gameIsActive = true;
        unplayed = 2;
        gameState = new int[9];

        for (int i = 0; i < 9; i++) {
            gameState[i] = unplayed;
        }

        winningPositions = new int[][]{{0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}};
    }

    /**
     * This places a yellow or red counter into its proper position and shows the change on the
     * board
     *
     * @param view the ImageView of the yellow or red counter
     */
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == unplayed && gameIsActive) {
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-2000);
            counter.animate()
                    .translationYBy(2000f)
                    .rotation(360f)
                    .setDuration(300);

            checkWinner();

            if (activePlayer == yellow) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = red;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = yellow;
            }
        }
    }

    /**
     * Checks if either a player has placed 3 counters in a row, or if the match is a draw
     */
    public void checkWinner() {

        boolean gameIsOver = true;

        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != unplayed) {

                TextView winnerMessage = findViewById(R.id.winnerMessage);
                String winner = "Red";

                if (gameState[winningPosition[0]] == yellow)
                    winner = "Yellow";

                winnerMessage.setText(winner + " has won!");

                LinearLayout layout = findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);

                gameIsActive = false;

            } else {
                for (int counterState : gameState) {
                    if (counterState == unplayed)
                        gameIsOver = false;
                }

                if (gameIsOver) {
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText("It's a draw!");

                    LinearLayout layout = findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                    gameIsActive = false;
                }
            }
        }
    }

    /**
     * Resets the board to start a new Connect 3 Game
     *
     * @param view the playAgain Linear Layout view
     */
    public void playAgain(View view) {
        LinearLayout layout = findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = yellow;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = unplayed;
        }

        GridLayout board = findViewById(R.id.board);
        for (int i = 0; i < board.getChildCount(); i++) {
            ((ImageView) board.getChildAt(i)).setImageResource(0);
        }

        gameIsActive = true;
    }
}