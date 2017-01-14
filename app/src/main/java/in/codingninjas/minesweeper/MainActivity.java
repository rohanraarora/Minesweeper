package in.codingninjas.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

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

    public void startGame(View view){
        int id = radioGroup.getCheckedRadioButtonId();
        int difficulty = MEDIUM;
        switch (id){
            case R.id.easyRadio:
                difficulty = EASY;
                Toast.makeText(this,"Easy",Toast.LENGTH_LONG).show();
                break;
            case R.id.hardRadio:
                difficulty = HARD;
                Toast.makeText(this,"Easy",Toast.LENGTH_LONG).show();
                break;
            case R.id.mediumRadio:
                difficulty = MEDIUM;
                Toast.makeText(this,"Easy",Toast.LENGTH_LONG).show();
                break;
        }

    }

}
