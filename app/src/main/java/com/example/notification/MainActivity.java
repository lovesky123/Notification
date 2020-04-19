package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_notification1, tv_notification2, tv_notification3;
    private static final int NO_1 = 0x1;
    private static final int NO_2 = 0x2;
    int num = 1;//初始通知数量为1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_notification1 = findViewById(R.id.tv_notification1);
        tv_notification2 = findViewById(R.id.tv_notification2);
        tv_notification3 = findViewById(R.id.tv_notification3);
        tv_notification1.setOnClickListener(this);
        tv_notification2.setOnClickListener(this);
        tv_notification3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_notification1) {
            show1(tv_notification1);
        } else if (view.getId() == R.id.tv_notification2) {
            show2(tv_notification2);
        } else if (view.getId() == R.id.tv_notification3) {
            show3();
        }
    }

    //按钮点击事件（通知栏）
    public void show1(View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("新消息");
        builder.setContentText("你有一条新的消息");
        builder.setNumber(num++);
        //设置点击通知跳转页面后，通知消失
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, Main2Activity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NO_1, notification);
    }

    public void show2(View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.message);
        rv.setTextViewText(R.id.tv, "泡沫");//修改自定义View中的歌名
        //修改自定义View中的图片(两种方法)
        //rv.setImageViewResource(R.id.iv,R.mipmap.ic_launcher);
        rv.setImageViewBitmap(R.id.iv, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContent(rv);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NO_2, notification);
    }
    public void show3() {
        //1.获取系统通知的管理者
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //2.初始化一个notification的对象
        Notification.Builder mBuilder = new Notification.Builder(this);
        //android 8.0 适配     需要配置 通知渠道NotificationChannel
        NotificationChannel b;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            b = new NotificationChannel("1", "乱七八糟的其他信息", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(b);
            mBuilder.setChannelId("1");
        }
        //TODO 设置点击通知跳转页面后，通知消失
        Intent intent = new Intent(this, Main2Activity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        //TODO 添加自定义视图  activity_notification
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.message);
        //TODO 为自定义布局中的控件赋值
        mRemoteViews.setTextViewText(R.id.tv, "那么骄傲");
        mRemoteViews.setImageViewResource(R.id.iv, R.mipmap.ic_launcher);
        //TODO 为布局中的子控件设置点击事件
        mRemoteViews.setOnClickPendingIntent(R.id.btn2, pi);
        //TODO 主要设置setContent参数  其他参数 按需设置
        mBuilder.setContent(mRemoteViews);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setOngoing(true);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mBuilder.setAutoCancel(true);
        //TODO 更新Notification
        mNotificationManager.notify(1, mBuilder.build());
    }


}
