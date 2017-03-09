package com.example.miaoz.xgpush;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.miaoz.xgpush.model.School;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by miaoz on 2017/3/8.
 */
public class RXTest extends Activity {


    int drawableRes = 1;
    ImageView imageView ;

    public void rx() {

        String[] names= {"1","2","3"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("tag",s);
                    }
                });


        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getTheme().getDrawable(drawableRes));
//                subscriber.onNext("drawable");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }
                });


        Observable.just(1,2,3,4)
                .subscribeOn(Schedulers.io())  // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("tag", "number:" + integer);
                    }
                });

        Observable.just("images/logo.png")
                .map(new Func1<String, Bitmap>() {

                    @Override
                    public Bitmap call(String s) {

                        return null;
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {

                    }
                });


        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {

                return Observable.just("deferObservable");
            }
        });

        Observable.interval(1, TimeUnit.SECONDS);
        Observable.range(10,5);

        Observable.create(new Observable.OnSubscribe<Date>() {
            @Override
            public void call(Subscriber<? super Date> subscriber) {
                Date date = new Date();
                subscriber.onNext(date);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Date>() {
                    @Override
                    public void call(Date date) {
                        //更新UI
                    }
                });

        Observable.just("FilePath").map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String path) {
                return getBitmapByPath(path);
            }
        }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                //得到bitMap
            }
        });

        Observable.just("12345678").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s.substring(0, 4);//只要前四位
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("mytag", s);
            }
        });
//Map：最常用且最实用的操作符之一，将对象转换成另一个对象发射出去，应用范围非常广，如数据的转换，数据的预处理等。
        //因为Map是一对一的关系
        List<School> schoolList = new ArrayList<>();
        Observable.from(schoolList).map(new Func1<School, String>() {
            @Override
            public String call(School school) {
                return school.getName();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
//                Log.i("mytag",schoolName);
            }
        });
//FlatMap：和Map很像但又有所区别，Map只是转换发射的数据类型，而FlatMap可以将原始Observable转换成另一个Observable。
        //FlatMap可以改变原始Observable变成另外一个Observable
        Observable.from(schoolList).flatMap(new Func1<School, Observable<School.Student>>() {
            @Override
            public Observable<School.Student> call(School school) {
                return Observable.from(school.getStudentList());
            }
        }).subscribe(new Action1<School.Student>() {
            @Override
            public void call(School.Student student) {
                Log.i("mytag",student.getName());
            }
        });
//Buffer：缓存，可以设置缓存大小，缓存满后，以list的方式将数据发送出去；例：
        Observable.just(1,2,3).buffer(2).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                Log.i("mytag","size:"+integers.size());
            }
        });
//在开发当中，个人经常将Buffer和Map一起使用，常发生在从后台取完数据，对一个List中的数据进行预处理后，再用Buffer缓存后一起发送，保证最后数据接收还是一个List，如下：
        Observable.from(schoolList).map(new Func1<School, School>() {
            @Override
            public School call(School school) {
                school.setName("NB大学");
                return school;
            }
        }).buffer(schoolList.size()).subscribe(new Action1<List<School>>() {
            @Override
            public void call(List<School> schools) {
                //获取school的list
            }
        });
//Take：发射前n项数据，还是用上面的例子，假设不要改所有学校的名称了，就改前四个学校的名称：
        Observable.from(schoolList).take(4).map(new Func1<School, School>() {

            @Override
            public School call(School school) {
                school.setName("NB大学");
                return school;
            }
        }).buffer(4).subscribe(new Action1<List<School>>() {
            @Override
            public void call(List<School> schools) {
                //获取schoolList数组
            }
        });
//Distinct:去掉重复的项，比较好理解：
        Observable.just(1,2,1,1,2,3)
                .distinct()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Next: " + integer);
                    }
                });
//Filter：过滤，通过谓词判断的项才会被发射，例如，发射小于4的数据：
        Observable.just(1,2,3,4,5)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer<4;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Next: " + integer);
                    }
                });


    }

    public Bitmap getBitmapByPath(String path) {
        Bitmap bitmap = null ;
        return bitmap;
    }
}
