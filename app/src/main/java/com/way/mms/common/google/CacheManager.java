package com.way.mms.common.google;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * freeme.linqingwei, 20171123.
 */

public class CacheManager {
    private static final String TAG = "CacheManager";
    private static final String KEY_CACHE_UP_TO_DATE = "cache-up-to-date";
    private static HashMap<String, BlobCache> sCacheMap =
            new HashMap<String, BlobCache>();
    private static boolean sOldCheckDone = false;

    private static final boolean PUT_CACHE_ON_SDCARD = false;

    // Return null when we cannot instantiate a BlobCache, e.g.:
    // there is no SD card found.
    // This can only be called from data thread.
    public static BlobCache getCache(Context context, String filename,
                                     int maxEntries, int maxBytes, int version) {
        synchronized (sCacheMap) {
            if (!sOldCheckDone) {
                removeOldFilesIfNecessary(context);
                sOldCheckDone = true;
            }
            BlobCache cache = sCacheMap.get(filename);
            if (cache == null) {
                File cacheDir = PUT_CACHE_ON_SDCARD ? context.getExternalCacheDir()
                        : context.getCacheDir();
                String path = cacheDir.getAbsolutePath() + "/" + filename;
                Log.d(TAG, "Cache dir: " + path);
                try {
                    cache = new BlobCache(path, maxEntries, maxBytes, false,
                            version);
                    sCacheMap.put(filename, cache);
                } catch (IOException e) {
                    Log.e(TAG, "Cannot instantiate cache!", e);
                }
            }
            return cache;
        }
    }

    // Removes the old files if the data is wiped.
    private static void removeOldFilesIfNecessary(Context context) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        int n = 0;
        try {
            n = pref.getInt(KEY_CACHE_UP_TO_DATE, 0);
        } catch (Throwable t) {
            // ignore.
        }
        if (n != 0) return;
        pref.edit().putInt(KEY_CACHE_UP_TO_DATE, 1).apply();

        clear(context);
    }

    public static void clear(Context context) {
        File cacheDir = PUT_CACHE_ON_SDCARD ? context.getExternalCacheDir()
                : context.getCacheDir();
        String prefix = cacheDir.getAbsolutePath() + "/";

        BlobCache.deleteFiles(prefix + ImageCacheService.IMAGE_CACHE_FILE);
        sCacheMap.remove(ImageCacheService.IMAGE_CACHE_FILE);
    }
}
