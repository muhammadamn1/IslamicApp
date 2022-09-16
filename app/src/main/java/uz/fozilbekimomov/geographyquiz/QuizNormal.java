package uz.fozilbekimomov.geographyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.progressview.ProgressView;

import java.util.ArrayList;

public class QuizNormal extends AppCompatActivity {

    private final ArrayList<QuestionData> data = new ArrayList<>();
    private final int MAX_TIME = 20;
    private final int MIN_TIME = 1;
    private final int DELTA_TIME = 1;
    boolean isFinished = false;
    private ProgressView progressView;
    private TextView currentView, totalView, finishButton, checkButton, questionView;
    private RadioGroup answerGroup;
    private RadioButton variantA;
    private RadioButton variantB;
    private RadioButton variantC;
    private QuestionManager manager;
    private boolean isAnswered = false;
    private final CountDownTimer timer = null;
    private ValueAnimator valueAnimator;

    private Button button;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_normal);


        loadViews();
        setStateViews();
        setListener();
        loadData();
        manager = new QuestionManager(data);
        startQuiz();
    }

    private void startQuiz() {

        questionView.setText(manager.getQuestion());
        variantA.setText(manager.getVariantA());
        variantB.setText(manager.getVariantB());
        variantC.setText(manager.getVariantC());

        currentView.setText(String.valueOf(manager.getCurrentLevel()));
        totalView.setText(String.valueOf(manager.getTotal()));

     /*   valueAnimator = new ValueAnimator();

        valueAnimator.setDuration(20_000);
        valueAnimator.setFloatValues(100, 1);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.d("QuizActivityTAG", "onTick: " + valueAnimator.getAnimatedFraction());
                int percentage = 100 - (int) ((valueAnimator.getAnimatedFraction() * 100) % 100);
                progressView.setProgress(percentage);
                progressView.progressAnimate();
            }
        });
        valueAnimator.start();
*/

      /*  if (timer != null) {
            timer.cancel();
        }



        timer = new CountDownTimer(100_000, 1_000) {
            @Override
            public void onTick(long current) {
                Log.d("QuizActivityTAG", "onTick: "+current);
                double progress = current * 1.0 / 100_000 * 100;
                seekBar.setProgress((int) progress);
            }

            @Override
            public void onFinish() {

            }
        };

        timer.start();*/

    }

    private void clearView() {
        variantA.setBackgroundResource(R.drawable.yellow);
        variantA.setEnabled(true);
        variantB.setBackgroundResource(R.drawable.yellow);
        variantB.setEnabled(true);
        variantC.setBackgroundResource(R.drawable.yellow);
        variantC.setEnabled(true);
        answerGroup.clearCheck();

    }

    private void loadData() {
        data.add(new QuestionData(
                "Injil qaysi Payg'ambarga tushirilgan?",
                "Iso (a.s)",
                "Iso (a.s)",
                "Muso(a.s)",
                "Solih(a.s)"
        ));
        data.add(new QuestionData(
                "Ayollarga ruxsat etilgan, Erkaklarga ma'n etilgan narsa bu?",
                "oltin",
                "Kumush",
                "oltin",
                "Chugun"
        ));
        data.add(new QuestionData(
                "Dinimizada nechi kundan ortiq arazlashib yurish mumkin emas?",
                "3 kun",
                "3 kun",
                "5 kun",
                "9 kun"
        ));
        data.add(new QuestionData(
                "Payg'ambarimiz (s.a.v) birinchi Ayollari!",
                "Hadicha r.a ",
                "Hadicha r.a ",
                "Oyisha r.a",
                "Hafsa r.a"
        ));
        data.add(new QuestionData(
                "Payg'ambarimizni (s.a.v)ni nechita o'g'illari bo'lgan?",
                "3ta",
                "7ta",
                "3ta",
                "4ta"
        ));
        data.add(new QuestionData(
                "Payg'ambarimizga (s.a.v) vahiy kelganda nechi yoshda edilar?",
                "40 yoshda",
                "30 yoshda",
                "35 yoshda",
                "40 yoshda"
        ));
        data.add(new QuestionData(
                "Payg'ambarimiz (s.a.v) tushirilgan birinchi sura?",
                "Alaq",
                "Fotiha",
                "Alaq",
                "Ixlos"
        ));
        data.add(new QuestionData(
                "Islomdagi eng qisqa sura?",
                "Kavsar",
                "Asr",
                "Ixlos",
                "Kavsar"
        ));
        data.add(new QuestionData(
                "Islomdagi eng uzun sura?",
                "Baqara",
                "Oliy Iron",
                "Baqara",
                "Yasin"
        ));
        data.add(new QuestionData(
                "Islomda nechta mashab bor?",
                "4ta",
                "4ta",
                "5ta",
                "2ta"
        ));
    }

    private void setListener() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int trueCount = manager.getTotalTrues();
                int falseCount = manager.getTotalFalse();

                int key = 1;

                Bundle bundle = new Bundle();
                bundle.putInt(ResultActivity.KEY_TRUES,trueCount);
                bundle.putInt(ResultActivity.KEY_MISTAKES, falseCount);

                Intent intent = new Intent(QuizNormal.this, ResultActivity.class);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasPressed = variantA.isChecked() || variantB.isChecked() || variantC.isChecked();
                if (isFinished) {
                    finish();
                } else {
                    if (hasPressed) {
                        if (isAnswered) {
                            if (!manager.isFinish()) {
                                clearView();
                                startQuiz();
                                checkButton.setText("Tanlash");
                            } else {
                                isFinished = true;
                                checkButton.setText("Result");

                                frameLayout.setVisibility(View.VISIBLE);

                            }
                            isAnswered = false;
                        } else {
                            RadioButton button = findViewById(answerGroup.getCheckedRadioButtonId());
                            String answer = button.getText().toString();
                            boolean isTrue = manager.checkAnswer(answer);

                            if (isTrue) {
                                button.setBackgroundResource(R.drawable.green);
                            } else {
                                button.setBackgroundResource(R.drawable.folse);
                            }

                            variantA.setEnabled(variantA.isChecked());
                            variantB.setEnabled(variantB.isChecked());
                            variantC.setEnabled(variantC.isChecked());

                            checkButton.setText("Keyingisi");
                            isAnswered = true;
                        }

                    } else {
                        Toast.makeText(QuizNormal.this, "Choose one of answers !!!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    private void loadViews() {

        frameLayout = findViewById(R.id.finish_view);
        button = findViewById(R.id.button);

        progressView = findViewById(R.id.state_view);
        currentView = findViewById(R.id.current_question);
        totalView = findViewById(R.id.total_question);

        finishButton = findViewById(R.id.finish_test);
        checkButton = findViewById(R.id.check_answer);
        questionView = findViewById(R.id.question_view);

        answerGroup = findViewById(R.id.answer_group);

        variantA = findViewById(R.id.variant_a);
        variantB = findViewById(R.id.variant_b);
        variantC = findViewById(R.id.variant_c);

        progressView.setDuration(20_000);
        progressView.setMax(100);

    }

    private void setStateViews() {
        progressView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public void openResult(View view) {

    }

}