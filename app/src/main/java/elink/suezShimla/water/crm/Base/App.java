package elink.suezShimla.water.crm.Base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;


import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import elink.suezShimla.water.crm.ServiceClass;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class App extends Application {
    private TimerTask mActivityTransitionTimerTask;
    private Timer mActivityTransitionTimer;
    public boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 1200000000;
    private static Context mCon;
    public JSONObject jsonObjecMaterialDetails = new JSONObject();
    public static JSONArray jsonArrayFleets = new JSONArray();
    public static String backPress="N" ,backPressMMGFragment="N",backPressNSCFragment="N",backPressMaterialFragment="N",backPressComplaintragment="N";




    @Override
    public void onCreate() {
        super.onCreate();
        mCon = this;

        //old working code
      /*  Realm.init(mCon);
        RealmConfiguration config = new RealmConfiguration.Builder().name("crm.realm").allowWritesOnUiThread(true).build();
        Realm.setDefaultConfiguration(config);*/

        //new realm code
        Realm.init(mCon); // Initialize Realm

// Define the RealmConfiguration with a schema version and migration
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("crm.realm")                 // Set the database name
                .schemaVersion(2)                   // Increment the schema version (2 because it's the second version)
                .allowWritesOnUiThread(true)        // Allow writes on the UI thread
                .migration(new RealmMigration() {   // Define the migration block
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        // Migration logic for version 1 -> version 2 (Adding the 'ConnectionStatusModel')
                        if (oldVersion == 1) {
                            // Add 'ConnectionStatusModel' class with its fields
                            realm.getSchema()
                                    .create("ConnectionStatusModel")  // Create the new class
                                    .addField("DTM_DISCONN_TAG_ID", String.class)
                                    .addField("DTM_DISCONN_TAG_NAME", String.class);
                            oldVersion++;  // Increment oldVersion to 2 after the migration
                        }
                    }
                })
                .build();

// Set the Realm configuration as the default configuration
        Realm.setDefaultConfiguration(config);


        // ******************** Comment this code before launching app on play store ********************
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))

                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .withLimit(15000)
                                .build())
                        .build());

        Intent serviceIntent = new Intent(this, ServiceClass.class);
        this.startService(serviceIntent);
    }

    public static Context getContext() {

        return mCon;
    }

    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                App.this.wasInBackground = true;
            }
        };

        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);  // app logs out after 20 min
    }


    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }

        this.wasInBackground = false;
    }

    public JSONObject getJsonObjectMaterialDetails() {
        return jsonObjecMaterialDetails;
    }
}
