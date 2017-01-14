package in.codingninjas.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty",MainActivity.MEDIUM);
        Toast.makeText(this,difficulty+"",Toast.LENGTH_LONG).show();

        tableLayout = (TableLayout) findViewById(R.id.table);
    }
}
