package elink.suezShimla.water.crm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorModel.ErrorLogModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;

public class ErrorClass {

    public static void errorData(Context context, String activityName, String eventName, String errorMsg) {
        try {
            RealmOperations realmOperations = new RealmOperations(context);
            Number errorIdNum = realmOperations.getErrorIDCount();

            int errorNextId;
            if (errorIdNum == null) {
                errorNextId = 1;
            } else {
                errorNextId = errorIdNum.intValue() + 1;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            String timeStamp = simpleDateFormat.format(new Date());
            String app_version = UtilitySharedPreferences.getPrefs(context, Constants.APPVERSION);
            // Decrypt EmpCode
            ErrorLogModel errorLogModel = new ErrorLogModel(errorNextId, new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode()), PreferenceUtil.getImei(), Build.MODEL, Build.MANUFACTURER, Build.VERSION.SDK_INT,
                    app_version, activityName, eventName, errorMsg, 0, timeStamp, "2");
            realmOperations.insertErrorLogData(errorLogModel);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
