package ru.ksu.motygullin.chucknorris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TaskInterface {

    ProgressBar pb;
    TextView joke_tv;
    Button refresh_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar2);
        joke_tv = (TextView) findViewById(R.id.joke);
        refresh_btn = (Button) findViewById(R.id.refresh_btn);

        if(getFragment().isRunning()){
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
        if(savedInstanceState!=null){
            joke_tv.setText(savedInstanceState.getString("joke"));

        }

        refresh_btn.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                getFragment().startTask();
            }
        });
    }

    @Override
    public void onTaskStart() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpgrade(int i) {
        pb.setProgress(i);
    }

    @Override
    public void onFinish(String s) {
        pb.setVisibility(View.GONE);
        if(s!=null){
            joke_tv.setText(s);
        }
    }

    @Override
    public void onTaskCancel() {
        pb.setVisibility(View.GONE);
    }


    private MyFragment getFragment(){
        MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentByTag(MyFragment.class.getName());
        if(fragment ==null){
            fragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().add(fragment, MyFragment.class.getName()).commit();
        }
        return fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("joke", joke_tv.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
