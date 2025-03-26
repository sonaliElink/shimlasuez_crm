package elink.suezShimla.water.crm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import elink.suezShimla.water.crm.Splash.SplashScreen;

public class SessionActivity extends AppCompatActivity {

	//public static final long DISCONNECT_TIMEOUT = 60000; // 300 sec = 300 * 1000 ms
	public static final long DISCONNECT_TIMEOUT = 1200000000; // 300 sec = 300 * 1000 ms 300000

	public static  String APPNAMECODE = "W1";
	public static  String DEVICENAME = "";
	public static  String MODEL = "";
	public static  String VERSION = "";
	public static  String timeStamp="";

	private Handler disconnectHandler = new Handler() {
		public void handleMessage(Message msg) {
		}
	};

	private Runnable r = new Runnable() {


		@Override
		public void run() {

			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SessionActivity.this);
			dialogBuilder.setTitle(getResources().getString(R.string.alert));
			dialogBuilder.setMessage(getResources().getString(R.string.session_timeout));
			dialogBuilder.setCancelable(false);

			dialogBuilder.setNegativeButton(getResources().getString(R.string.ok), null);

			final AlertDialog alertDialog = dialogBuilder.create();
			alertDialog.show();


			Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
			// override the text color of negative button
			negativeButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
			// provides custom implementation to negative button click
			negativeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(SessionActivity.this, SplashScreen.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					//finish();
					alertDialog.cancel();				}
			});


			/*AlertDialog.Builder alertDialog = new AlertDialog.Builder(SessionActivity.this);
			alertDialog.setCancelable(false);
			alertDialog.setTitle(getResources().getString(R.string.alert));
			alertDialog.setMessage(getResources().getString(R.string.session_timeout));








			alertDialog.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(SessionActivity.this, SplashActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							//finish();
							dialog.cancel();
						}
					});

			alertDialog.show();*/

			// Perform any required operation on disconnect
		}
	};

	public void mobileDeviceInformation(){
		MODEL= Build.MODEL;
		DEVICENAME= Build.MANUFACTURER;
		VERSION = String.valueOf(Build.VERSION.SDK_INT);

	}

	public void DataTimeFormate(){

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
		timeStamp = simpleDateFormat.format(new Date());
		Log.d("MainActivity", "Current Timestamp: " + timeStamp);

	}

	public void resetDisconnectTimer() {
		disconnectHandler.removeCallbacks(r);
		disconnectHandler.postDelayed(r, DISCONNECT_TIMEOUT);
	}

	public void stopDisconnectTimer() {
		disconnectHandler.removeCallbacks(r);
	}
/*
	@Override
	public void onUserInteraction() {
		resetDisconnectTimer();
	}*/

	@Override
	public void onResume() {
		super.onResume();
		resetDisconnectTimer();
	}

	@Override
	public void onStop() {
		super.onStop();
		stopDisconnectTimer();
	}
	@Override
	public void onUserInteraction() {
		// TODO Auto-generated method stub
		super.onUserInteraction();
		stopHandler();//stop first and then start
		startHandler();
	}

	public void stopHandler() {
		Log.d("HandlerRun", "stopHandlerMain");
		disconnectHandler.removeCallbacks(r);
	}

	public void startHandler() {
		Log.d("HandlerRun", "startHandlerMain");
		disconnectHandler.postDelayed(r, 1 * 60 * 5000); //for 5 minutes
	}
	public void deviceAuthorizedDialog() {

		MaterialDialog dialog = new MaterialDialog.Builder(this)
				.title(R.string.alert)
				.titleColorRes(R.color.red_500)
				.content(R.string.device_authorization)
				.contentColor(this.getResources().getColor(R.color.colorPrimary))
				.canceledOnTouchOutside(false)
				.positiveText(R.string.ok)
				.onPositive(new MaterialDialog.SingleButtonCallback() {
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

						Intent intent = new Intent(SessionActivity.this, SplashScreen.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
						dialog.dismiss();
					}
				}).show();
	}

	public void adminLogout() {

		MaterialDialog dialog = new MaterialDialog.Builder(this)
				.title(R.string.alert)
				.titleColorRes(R.color.red_500)
				.content(R.string.admin_logout)
				.contentColor(this.getResources().getColor(R.color.colorPrimary))
				.canceledOnTouchOutside(false)
				.positiveText(R.string.ok)
				.onPositive(new MaterialDialog.SingleButtonCallback() {
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

						Intent intent = new Intent(SessionActivity.this, SplashScreen.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
						dialog.dismiss();
					}
				}).show();

	}
	public void timeoutMessage() {

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				SessionActivity.this);
		builder.setMessage("msg").setTitle("title").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// stopHandler();//stop first and then start
						// startHandler();
//                        handler.postDelayed(r, 1 * 60 * 1000); //for 5 minutes
						dialog.dismiss();

					}

				});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//stopHandler();
				startActivity(new Intent(getApplicationContext(), SplashScreen.class));
				finish();
			}
		});
		android.app.AlertDialog alert = builder.create();
		alert.show();
	}


}
