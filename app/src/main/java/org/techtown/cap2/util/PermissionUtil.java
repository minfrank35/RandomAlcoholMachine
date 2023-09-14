package org.techtown.cap2.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 권한 요청 유틸
 */
public class PermissionUtil {
    /**
     * checkPermission targetsdk 33 올리면서 만든 함수
     * sdk 33에서만 작동하는 함수입니다.
     * 권한 하나만 요청가능.
     *
     * @author 김성민
     * @since 2023/06/30 4:26 PM
     *
     * @param activity 액티비티
     * @param permission Manifest.permission.권한이름
     */

    @RequiresApi(Build.VERSION_CODES.M)
    public static void checkPermission(Activity activity, String permission, int requestCode, PermissionCallback permissionCallback) {
        if (ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED) {
            permissionCallback.onPermissionGranted();
            // You can use the API that requires the permission.
        } else if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            permissionCallback.onPermissionRotationed();
        } else {
            // You can directly ask for the permission.
            activity.requestPermissions(new String[]{permission}, requestCode);
        }
    }
}
