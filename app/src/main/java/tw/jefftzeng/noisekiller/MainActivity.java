package tw.jefftzeng.noisekiller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{
    EliminateNoise eliminateNoise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton buttonPrev= (ImageButton)findViewById(R.id.buttonPrev);
        final ImageButton buttonNext= (ImageButton)findViewById(R.id.buttonNext);
        final ImageButton buttonSilence = (ImageButton)findViewById(R.id.buttonSilence);
        buttonPrev.setOnClickListener(selectMode);
        buttonNext.setOnClickListener(selectMode);
        buttonSilence.setOnClickListener(applyMode);
    }

    View.OnClickListener selectMode = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

        }
    };

    View.OnClickListener applyMode = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            eliminateNoise = new EliminateNoise();
            eliminateNoise.Start();
        }
    };
}
