package ru.ksu.motygullin.chucknorris;

/**
 * Created by UseR7 on 12.11.2016.
 */

public interface TaskInterface {
    void onTaskStart();
    void onUpgrade(int i);
    void onFinish(String s);
    void onTaskCancel();
}
