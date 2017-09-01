package in.codingninjas.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


//This screen is for seleting the difficulty
public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLastScore();
    }

    public void startGame(View view){
        Intent intent = new Intent(this,GameActivity.class);
        int id = radioGroup.getCheckedRadioButtonId();
        int difficulty = MEDIUM;
        switch (id){
            case R.id.easyRadio:
                difficulty = EASY;
                break;
            case R.id.hardRadio:
                difficulty = HARD;
                break;
            case R.id.mediumRadio:
                difficulty = MEDIUM;
                break;
        }
        intent.putExtra("difficulty",difficulty);
        startActivity(intent);

    }

    private void showLastScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("minesweeper",MODE_PRIVATE);
        int score = sharedPreferences.getInt("score",0);
        TextView scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        scoreTextView.setText("Last Score: " + score);

    }

}
