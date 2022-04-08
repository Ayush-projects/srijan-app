package in.srijanju.androidapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import static in.srijanju.androidapp.SrijanApp.ANDROID_APP_VERSION_CODE_STRING;
import static in.srijanju.androidapp.SrijanApp.ANDROID_APP_VERSION_NAME_STRING;

public class SrijanActivity extends AppCompatActivity {

  FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

  @Override
  protected void onResume() {
	super.onResume();

	/*
	 * Check for app updates
	 */
	long vCode = mFirebaseRemoteConfig.getLong(ANDROID_APP_VERSION_CODE_STRING);
	long curCode = 0;
	try {
		curCode = this.getPackageManager().getPackageInfo(this.getPackageName(),0).versionCode;
	} catch (PackageManager.NameNotFoundException e) {
		curCode = 1000 ;
	}

	// ***** CODE TO UPDATE THE APP ******** //

	if (vCode != curCode ) {
	  new AlertDialog.Builder(SrijanActivity.this).setTitle("App update available")
			  .setIcon(R.mipmap.ic_launcher)
			  .setMessage("Update to continue using the app.").setPositiveButton(
			  "Update", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				  final String appPackageName = getPackageName();
				  try {
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("market://details?id=" + appPackageName)));
				  } catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
				  }
				}
			  }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		  Toast.makeText(SrijanActivity.this, "You need to update the app first!",
				  Toast.LENGTH_LONG)
				  .show();
		  finish();
		}
	  }).setCancelable(false).create().show();
	}
  }
}
