package in.codingninjas.minesweeper;

import android.content.Intent;
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
            }
        });
        setUpBoard();
        initGame();
    }

    private void setUpBoard() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width  = displayMetrics.widthPixels;
        size = width/NO_OF_COLS;
        squares = new Square[NO_OF_ROWS][NO_OF_COLS];
        board = new int[NO_OF_ROWS][NO_OF_COLS];
        for(int i = 0;i<NO_OF_ROWS;i++){
            TableRow row = new TableRow(GameActivity.this);
            for(int j = 0;j<NO_OF_COLS;j++){
                Square square = new Square(GameActivity.this,size);
                squares[i][j] = square;
                square.setOnClickListener(GameActivity.this);
                square.setOnLongClickListener(GameActivity.this);
                row.addView(square);
            }
            tableLayout.addView(row);
        }
    }

    private void initGame(){
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
        int minesCount = 0;
        Random random = new Random();
        while (minesCount < NO_0F_MINES){
            int randomInt = random.nextInt(NO_OF_ROWS*NO_OF_COLS);
            int row = randomInt/NO_OF_COLS;
            int col = randomInt%NO_OF_COLS;
            if(board[row][col] != -1){
                board[row][col] = -1;

                //Increasing value of neighbours of mine by 1 if not a mine
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
            if(isInBounds(neighbourRow,neighbourCol) && board[neighbourRow][neighbourCol] != -1){
                board[neighbourRow][neighbourCol]++;
            }
        }
    }

    private boolean isInBounds(int row,int col){
        return  row >=0 && row < NO_OF_ROWS && col >=0 && col < NO_OF_COLS;
    }

    private void refreshBoard() {
        for(int i = 0;i<NO_OF_ROWS;i++){
            for(int j = 0;j<NO_OF_COLS;j++){
                Square square = squares[i][j];
                square.setUp(i,j,board[i][j]);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Square square = (Square)view;
        Toast.makeText(this,"Click\nRow: " + square.getRow() + "\nCol: " + square.getColumn(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view) {
        Square square = (Square)view;
        Toast.makeText(this,"Long Press\nRow: " + square.getRow() + "\nCol: " + square.getColumn(),Toast.LENGTH_SHORT).show();

        return true;
    }
}
