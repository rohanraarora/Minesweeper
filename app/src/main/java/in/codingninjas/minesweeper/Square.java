package in.codingninjas.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

/**
 * Created by rohanarora on 12/01/17.
 *
 *
 */

public class Square extends Button {

    public static final int MINE = -1;

    private int row;
    private int column;
    private int value = 0;
    private int size;

    private boolean revealed = false;
    private boolean flagged = false;

    public Square(Context context, int size) {
        super(context);
        setPadding(0,0,0,0);
        this.size = size;
    }

    public void setUp(int row,int col,int value){
        this.row = row;
        this.column = col;
        this.value = value;
        revealed = false;
        flagged = false;
    }



    public void reveal(){
        revealed = true;
        flagged = false;
        invalidate();
    }

    public void setFlag(boolean flag){
        if(!revealed) {
            flagged = flag;
            invalidate();
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isMine(){
        return value == MINE;
    }

    public boolean isEmpty(){
        return value == 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(size, size);
    }


}
