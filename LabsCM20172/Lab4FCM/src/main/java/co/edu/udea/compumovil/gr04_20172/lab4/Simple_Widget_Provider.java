package co.edu.udea.compumovil.gr04_20172.lab4;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

/**
 * Created by dairo on 24/10/17.
 */

public class Simple_Widget_Provider extends AppWidgetProvider {
    //
    private FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
    private DatabaseReference ref= firebaseDatabase.getReference("Apartment");
    private FirebaseStorage storage= FirebaseStorage.getInstance();
    private StorageReference storageRef= storage.getReference();
    RemoteViews views;

    public void mostrarDato(Apartment apartment,AppWidgetManager appWidgetManager,int appWidgetId)
    {
        views.setTextViewText(R.id.name_text, apartment.getShortDescription());
        views.setTextViewText(R.id.type_text, apartment.getType());
        views.setTextViewText(R.id.price_text, apartment.getPrice());
        views.setTextViewText(R.id.area_text, apartment.getArea());
        views.setTextViewText(R.id.description_text, apartment.getLargeDescription());
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    //
    /*
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        //



        //




        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number = String.format("%03d", (new Random().nextInt(900) + 100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            remoteViews.setTextViewText(R.id.textView, number);

            Intent intent = new Intent(context, Simple_Widget_Provider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
    */

    //Aqui DAiro

    void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                         final int appWidgetId) {

        //final CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
        views.setTextViewText(R.id.type_text, "Hola a todos");
        ref.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Apartment value = dataSnapshot.getValue(Apartment.class);
                mostrarDato(value,appWidgetManager,appWidgetId);
                Log.d("OnChild",value.getShortDescription());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Log.d("onUpdate","onUpdate ciclo");
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Apartment");
        storage= FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    @Override
    public void onDisabled(Context context) {

    }
}
