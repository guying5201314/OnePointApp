package com.example.kaoshichayifen.onepointapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class TestActivity extends AppCompatActivity {
    private final String TAG = "testact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


//        Observable.just("hello world")
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.v(TAG,"onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.v(TAG,"onNext "+s);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.v(TAG,"onError");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.v(TAG,"onComplete");
//                    }
//                });

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {

                test(observableEmitter);
//                observableEmitter.onNext(3);
            }
        });

        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
//                observableEmitter.onNext(2);
                test1(observableEmitter);
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) throws Exception {
                return "测试："+integer+","+integer2;
            }}).map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return 134;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer s) throws Exception {
                    Log.v(TAG,""+s);
                }
            });


    }

    private void test(ObservableEmitter<Integer> emitter){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    emitter.onNext(3);
                }catch (Exception e){

                }finally {

                }
            }
        }).start();
    }

    private void test1(ObservableEmitter<Integer> emitter){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    emitter.onNext(3);
                }catch (Exception e){

                }finally {

                }
            }
        }).start();
    }

}
