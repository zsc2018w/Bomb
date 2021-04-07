package com.bomb.plus.study.basic;

import java.util.ArrayList;
import java.util.List;

public class TestMain {


    public void test() {

        List<Person> personList = new ArrayList<>();

        personList.add(new Person());
        personList.add(new Boy());
        personList.add(new Girl());

        toWork(personList);


        //---------------------------------

        List<Boy> boyList = new ArrayList<>();
        boyList.add(new Boy());

        List<Girl> girlList = new ArrayList<>();
        girlList.add(new Girl());


        toWork(boyList);
        toWork(girlList);

        toWork2(boyList);

    }

    /**
     * 协变
     * 上界限定
     * person 及其子类
     *
     * @param list
     */
    private void toWork(List<? extends Person> list) {
        //  list.add(new Boy());
        for (Person item : list) {
            if (item != null) {
                item.showName();
            }
        }

    }

    /**
     * 逆变
     * 下界限定
     * Bot 及其子类
     *
     * @param list
     */
    private void toWork2(List<? super Boy> list) {
        list.add(new Boy());
        list.add(new SmallBoy());
        for (Object item : list) {
            if (item != null) {
                // item.showName();
            }
        }

    }


}
