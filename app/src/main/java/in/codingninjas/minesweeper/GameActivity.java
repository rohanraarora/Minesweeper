package in.codingninjas.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    TableLayout tableLayout;

    private static int NO_OF_ROWS;
    private static int NO_OF_COLS;

    private static int NO_0F_MINES;

    private Square[][] squares;

    private TextView scoreTextView;

    private static final int[][] NEIGHBOUR_COORDS = {
            {1,0},{1,1},{1,-1},{0,1},{0,-1},{-1,0},{-1,1},{-1,-1}
    };

    private int size;
    private int[][] board;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get the intent which started this
        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty",MainActivity.MEDIUM);
        switch (difficulty){
            case MainActivity.EASY:
                NO_OF_COLS = 6;
                NO_OF_ROWS = 7;
                NO_0F_MINES = 5;
                break;
            case MainActivity.MEDIUM:
                NO_OF_COLS = 8;
                NO_OF_ROWS = 10;
                NO_0F_MINES = 25;
                break;
            case MainActivity.HARD:
                NO_OF_COLS = 12;
                NO_OF_ROWS = 15;
                NO_0F_MINES = 60;
                break;
        }

        tableLayout = (TableLayout) findViewById(R.id.table);
        scoreTextView = (TextView)findViewById(R.id.scoreTextView);

        final Button reset = (Button)findViewById(R.id.resetButton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO reset
                reset();
            }
        });
        setUpBoard();
        initGame();
    }

    private void setUpBoard() {
        //Get the width of screen and divide the no of cols to get the width of square
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width  = displayMetrics.widthPixels;
        size = width/NO_OF_COLS;
        squares = new Square[NO_OF_ROWS][NO_OF_COLS];
        board = new int[NO_OF_ROWS][NO_OF_COLS];
        for(int i = 0;i<NO_OF_ROWS;i++){
            //Create a new table row
            TableRow row = new TableRow(GameActivity.this);
            for(int j = 0;j<NO_OF_COLS;j++){
                //Add squares in table row
                Square square = new Square(GameActivity.this,size);
                squares[i][j] = square;
                //Add Listeners
                square.setOnClickListener(GameActivity.this);
                square.setOnLongClickListener(GameActivity.this);
                row.addView(square);
            }
            tableLayout.addView(row);
        }
    }

    private void initGame(){
        //Set value of each sqaure = 0
        score = 0;
        for(int i = 0;i<NO_OF_ROWS;i++){
            for(int j = 0;j<NO_OF_COLS;j++ ){
                board[i][j] = 0;
            }
        }
        setRandomMines();
        refreshBoard();
    }

    private void setRandomMines(){
        //Set a specific no of mines
        int minesCount = 0;
        Random random = new Random();
        while (minesCount < NO_0F_MINES){
            int randomInt = random.nextInt(NO_OF_ROWS*NO_OF_COLS);
            int row = randomInt/NO_OF_COLS;
            int col = randomInt%NO_OF_COLS;
            if(board[row][col] != -1){
                board[row][col] = -1;
                //When setting a new mine increase the value of neighbour tiles by 1.

                increaseNeighbourValues(row,col);

                minesCount++;
            }
        }
    }

    private void increaseNeighbourValues(int row,int col){
        for(int i = 0;i<NEIGHBOUR_COORDS.length;i++){
            int[] neighbourCoords = NEIGHBOUR_COORDS[i];
            int neighbourRow = row + neighbourCoords[0];
            int neighbourCol = col + neighbourCoords[1];
            //Increase the value if neighbour sqaure is inside board and is not a mine
            if(isInBounds(neighbourRow,neighbourCol) && board[neighbourRow][neighbourCol] != -1){
                board[neighbourRow][neighbourCol]++;
            }
        }
    }

    private boolean isInBounds(int row,int col){
        //Check if it's inside the board
        return  row >=0 && row < NO_OF_ROWS && col >=0 && col < NO_OF_COLS;
    }

    private void refreshBoard() {
        //set value for each square
        for(int i = 0;i<NO_OF_ROWS;i++){
            for(int j = 0;j<NO_OF_COLS;j++){
                Square square = squares[i][j];
                square.setUp(i,j,board[i][j]);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Square square = (Square) view;
        //Response is sqaure is not revealed and not flagged
        if(!square.isRevealed() && !square.isFlagged()) {
            //Reveal
            square.reveal();
            if (square.isMine()) {
                //If the revealed tile is mine, then show message and reveal all the tiles
                Toast.makeText(this, "You Loose", Toast.LENGTH_LONG).show();
                revealAll();
                saveScore();
            } else {
                //Otherwise increase score
                score++;
                if (square.isEmpty()) {
                    //If the revealed tile is empty recursively reveal neighbour tiles until non empty tiles are revealed
                    revealNeighbours(square);
                }
            }
            //update score and check if game is complete
            updateScore();
            checkIfGameComplete();
        }
    }

    private void checkIfGameComplete() {
        //if the no of unrevealed tiles is equal to no of mines, then the user wins
        if(NO_0F_MINES == NO_OF_COLS*NO_OF_ROWS - score){
            Toast.makeText(this,"You win",Toast.LENGTH_LONG).show();
            revealAll();
            saveScore();
        }
    }

    private void updateScore() {
        scoreTextView.setText("Score: " + score);
    }

    private void revealNeighbours(Square square){
        int row = square.getRow();
        int col = square.getColumn();
        for(int i = 0;i<NEIGHBOUR_COORDS.length;i++){
            int[] neighbourCoords = NEIGHBOUR_COORDS[i];
            int neighbourRow = row + neighbourCoords[0];
            int neighbourCol = col + neighbourCoords[1];
            if(isInBounds(neighbourRow,neighbourCol)){
                Square neighbourSquare = squares[neighbourRow][neighbourCol];
                if(!neighbourSquare.isRevealed()) {
                    neighbourSquare.reveal();
                    score++;
                    if (neighbourSquare.isEmpty()) {
                        revealNeighbours(neighbourSquare);
                    }
                }
                board[neighbourRow][neighbourCol]++;
            }
        }
    }

    private void revealAll(){
        for(int i = 0;i<NO_OF_ROWS;i++){
            for(int j = 0;j<NO_OF_COLS;j++){
                Square square = squares[i][j];
                if(!square.isRevealed()) {
                    square.reveal();
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        //Toggle flag
        Square square = (Square)view;
        square.setFlag(!square.isFlagged());
        return true;
    }

    public void reset(){
        //restart game and update score
        initGame();
        updateScore();
    }

    private void saveScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("minesweeper",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score",score);
        editor.commit();
    }
}
