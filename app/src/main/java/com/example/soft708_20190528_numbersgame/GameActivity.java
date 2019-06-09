package com.example.soft708_20190528_numbersgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private int Score;
    private int Chance;
    private int Mode;
    private int[] Number;
    private int CorrectAnswer;
    private Button[] ButtonAnswers;
    private Button ButtonTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Number = new int[4];
        ButtonAnswers = new Button[4];
        Intent intent = getIntent();
        Mode = intent.getIntExtra("mode", 2);

        for (int i = 0; i < 4; i++) {
            Number[i] = 0;
        }

        ButtonAnswers[0] = findViewById(R.id.btnAnswer0);
        ButtonAnswers[1] = findViewById(R.id.btnAnswer1);
        ButtonAnswers[2] = findViewById(R.id.btnAnswer2);
        ButtonAnswers[3] = findViewById(R.id.btnAnswer3);
        ButtonTryAgain = findViewById(R.id.btnTryAgain);

        ButtonAnswers[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer(0);
            }
        });
        ButtonAnswers[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer(1);
            }
        });
        ButtonAnswers[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer(2);
            }
        });
        ButtonAnswers[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer(3);
            }
        });

        ButtonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame();
            }
        });

        initializeGame();
    }

    private void initializeGame() {
        Score = 0;
        Chance = 3;

        generateNumbers();

        ButtonAnswers[0].setVisibility(View.VISIBLE);
        ButtonAnswers[1].setVisibility(View.VISIBLE);
        switch (Mode) {
            case 2:
                ButtonAnswers[2].setVisibility(View.GONE);
                ButtonAnswers[3].setVisibility(View.GONE);
                break;

            case 4:
                ButtonAnswers[2].setVisibility(View.VISIBLE);
                ButtonAnswers[3].setVisibility(View.VISIBLE);
                break;
        }
        findViewById(R.id.btnTryAgain).setVisibility(View.GONE);

        updateStatusText();
    }
    private void Answer(int answer) {
        if (answer == CorrectAnswer) {
            Score += Number[CorrectAnswer];
            generateNumbers();
        } else {
            if (--Chance == 0) {
                ButtonAnswers[0].setVisibility(View.GONE);
                ButtonAnswers[1].setVisibility(View.GONE);
                ButtonAnswers[2].setVisibility(View.GONE);
                ButtonAnswers[3].setVisibility(View.GONE);
                findViewById(R.id.btnTryAgain).setVisibility(View.VISIBLE);
            } else {
                generateNumbers();
            }
        }
        updateStatusText();
    }
    private void generateNumbers() {
        Random r = new Random();
        CorrectAnswer = 0;
        for (int i = 0; i < Mode; i++) {
            int n;
            boolean loop;
            do {
                loop = false;
                n = r.nextInt((Score / 8) + 30) + (Score / 10) + 10;
                for (int j = 0; j < i; j++) {
                    if (Number[j] == n) {
                        loop = true;
                        break;
                    }
                }
            } while (loop);
            Number[i] = n;
            if (Number[CorrectAnswer] < Number[i]) {
                CorrectAnswer = i;
            }
        }
    }

    private void updateStatusText() {
        TextView t;

        t = (TextView)findViewById(R.id.lblChance);
        t.setText(Chance == 1 ? "Chance" : "Chances");

        t = (TextView)findViewById(R.id.txtScore);
        t.setText(Integer.toString(Score));

        t = (TextView)findViewById(R.id.txtChance);
        t.setText(Integer.toString(Chance));

        ButtonAnswers[0].setText(Integer.toString(Number[0]));
        ButtonAnswers[1].setText(Integer.toString(Number[1]));
        ButtonAnswers[2].setText(Integer.toString(Number[2]));
        ButtonAnswers[3].setText(Integer.toString(Number[3]));

        t = (TextView)findViewById(R.id.lblCorrectAnswer);
        t.setText(Integer.toString(CorrectAnswer));
    }
}
