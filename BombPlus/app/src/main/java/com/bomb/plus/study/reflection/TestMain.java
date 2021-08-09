package com.bomb.plus.study.reflection;

import android.util.Log;

import com.bomb.plus.study.reflection.proxy.IWorkDispose;
import com.bomb.plus.study.reflection.proxy.PersonA;
import com.bomb.plus.study.reflection.proxy.PersonWorkProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestMain {


    public void test() throws Exception {
        Log.d("reflection--->", "start");

        int o=180<<2;

        int s=o>>5;
        Log.d("reflection--->", "计算  "+o+ "   2222   "+s);



        TestObserver testObserver = new TestObserver();

        Class testObserverClass = testObserver.getClass();


        Object object = testObserverClass.getConstructor(new Class[]{}).newInstance(new Object[]{});

        Field[] fields = testObserverClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {

            Field field = fields[i];
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();

            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            String setMethodName = "set" + firstLetter + fieldName.substring(1);

            Method getMethod = testObserverClass.getMethod(getMethodName, new Class[]{});
            Method setMethod = testObserverClass.getMethod(setMethodName, new Class[]{field.getType()});

            Object get = getMethod.invoke(object, new Object[]{});
            Log.d("reflection--->", "----->" + get);

        }

    }


    public void mainX() {
        testProxy();
    }

    public void testThread() {
        Thread t1 = new Thread("线程111") {
            @Override
            public void run() {
                super.run();
                Log.d("reflection--->", "----->" + getName());
            }
        };

        Thread t2 = new Thread("线程222") {
            @Override
            public void run() {
                super.run();
                Log.d("reflection--->", "----->0" + getName());
             /*   try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                Log.d("reflection--->", "----->1" + getName());
            }
        };
        t1.start();
        t2.start();

    }


    public void testProxy() {
        PersonA personA = new PersonA();

     //   PersonWorkProxy personWorkProxy = new PersonWorkProxy(personA);

       // personWorkProxy.toWork();

        IWorkDispose proxy = (IWorkDispose) Proxy.newProxyInstance(personA.getClass().getClassLoader(), personA.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d("proxy--->", "来活了");

                Object result = method.invoke(personA, args);
                Log.d("proxy--->", "活没了");

                return result;
            }
        });

        proxy.toWork();

        proxy.dayForRest();

    }
}
