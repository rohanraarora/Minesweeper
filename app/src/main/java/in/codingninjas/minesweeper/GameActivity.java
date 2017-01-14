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

public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    TableLayout tableLayout;

    private static int NO_OF_ROWS;
    private static int NO_OF_COLS;

    private static int NO_0F_MINES;

    private Square[][] squares;

    private TextView scoreTextView;

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

    @Override
    public void onClick(View view) {
        //TODO
    }

    @Override
    public boolean onLongClick(View view) {
        //TODO
        return false;
    }
}
