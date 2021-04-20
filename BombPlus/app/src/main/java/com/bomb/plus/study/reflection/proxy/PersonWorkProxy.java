package com.bomb.plus.study.reflection.proxy;

import android.util.Log;

public class PersonWorkProxy implements IWorkDispose {
    private PersonA personA;

    public PersonWorkProxy(PersonA personA) {
        this.personA = personA;
    }


    @Override
    public void toWork() {
        Log.d("proxy--->", "来活了");


        personA.toWork();
        Log.d("proxy--->", "活没了");
    }

    @Override
    public boolean dayForRest() {

        return false;
    }
}
