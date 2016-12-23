package ru.ksu.motygullin.chucknorris;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.json.JSONException;


public class MyFragment extends Fragment {
    private TaskInterface taskInterface;
    private MyAsyncTask task;

    public boolean isRunning(){
        return task!=null;
    }

    @Override
    public void onAttach(Context context) {
        setTaskInterface(context);
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        setTaskInterface(activity);
        super.onAttach(activity);
    }

    public void startTask(){
        if(task ==null){
            task = new MyAsyncTask();
            task.execute();
        }
    }

    public void stopTask(){
        if(task != null){
            task.cancel(true);
            task = null;
        }
    }

    private void setTaskInterface(Context context){
        if(context instanceof TaskInterface) {
            taskInterface = (TaskInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskInterface = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                int randomProgress = 0;
                // Simulate some heavy work.
                while (randomProgress < 100 && !isCancelled()) {
                    randomProgress += 5;
                    publishProgress(randomProgress);
                    Thread.sleep(200);
                }

                HttpClient client = new HttpClient();
                return JSONWeatherParser.getJoke(client.getData());

            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
            return ":)";
        }

        @Override
        protected void onPostExecute(String s) {
            task = null;
            if(taskInterface!=null){
                taskInterface.onFinish(s);
            }

        }

        @Override
        protected void onPreExecute() {
            if(taskInterface!=null){
                taskInterface.onTaskStart();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(taskInterface !=null){
                taskInterface.onUpgrade(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            task = null;
            if(taskInterface !=null){
                taskInterface.onTaskCancel();
            }
        }
    }
}
