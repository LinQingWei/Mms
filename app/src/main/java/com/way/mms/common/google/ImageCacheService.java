package com.way.mms.common.google;

import android.content.Context;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * freeme.linqingwei, 20171123.
 */

public class ImageCacheService {
    @SuppressWarnings("unused")
    private static final String TAG = "ImageCacheService";

    public static final String IMAGE_CACHE_FILE = "imgcache";
    private static final int IMAGE_CACHE_MAX_ENTRIES = 500;
    private static final int IMAGE_CACHE_MAX_BYTES = 20 * 1024 * 1024;
    private static final int IMAGE_CACHE_VERSION = 3;

    private BlobCache mCache;

    private static long[] sCrcTable = new long[256];
    private static final long POLY64REV = 0x95AC9329AC4BC9B5L;
    private static final long INITIALCRC = 0xFFFFFFFFFFFFFFFFL;

    private Context mContext;

    public ImageCacheService(Context context) {
        mCache = CacheManager.getCache(context, IMAGE_CACHE_FILE,
                IMAGE_CACHE_MAX_ENTRIES, IMAGE_CACHE_MAX_BYTES,
                IMAGE_CACHE_VERSION);
        mContext = context;
    }

    public static class ImageData {
        public ImageData(byte[] data, int offset) {
            mData = data;
            mOffset = offset;
        }

        private byte[] mData;
        private int mOffset;

        public byte[] getData() {
            return mData;
        }

        public void setData(byte[] mData) {
            this.mData = mData;
        }

        public int getOffset() {
            return mOffset;
        }

        public void setOffset(int mOffset) {
            this.mOffset = mOffset;
        }
    }

    public ImageData getImageData(String path, int type) {
        byte[] key = makeKey(path, type);
        long cacheKey = crc64Long(key);
        try {
            byte[] value = null;
            synchronized (mCache) {
                value = mCache.lookup(cacheKey);
            }
            if (value == null) return null;
            if (isSameKey(key, value)) {
                int offset = key.length;
                return new ImageData(value, offset);
            }
        } catch (IOException ex) {
            // ignore.
        }
        return null;
    }

    public void putImageData(String path, int type, byte[] value) {
        byte[] key = makeKey(path, type);
        long cacheKey = crc64Long(key);
        ByteBuffer buffer = ByteBuffer.allocate(key.length + value.length);
        buffer.put(key);
        buffer.put(value);
        synchronized (mCache) {
            try {
                mCache.insert(cacheKey, buffer.array());
            } catch (IOException ex) {
                // ignore.
            }
        }
    }

    public void clear() {
        CacheManager.clear(mContext);
    }

    private static byte[] makeKey(String path, int type) {
        return getBytes(path + "+" + type);
    }

    private static boolean isSameKey(byte[] key, byte[] buffer) {
        int n = key.length;
        if (buffer.length < n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (key[i] != buffer[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * A function thats returns a 64-bit crc for string
     *
     * @param in input string
     * @return a 64-bit crc value
     */
    public static final long crc64Long(String in) {
        if (in == null || in.length() == 0) {
            return 0;
        }
        return crc64Long(getBytes(in));
    }

    static {
        // http://bioinf.cs.ucl.ac.uk/downloads/crc64/crc64.c
        long part;
        for (int i = 0; i < 256; i++) {
            part = i;
            for (int j = 0; j < 8; j++) {
                long x = ((int) part & 1) != 0 ? POLY64REV : 0;
                part = (part >> 1) ^ x;
            }
            sCrcTable[i] = part;
        }
    }

    public static final long crc64Long(byte[] buffer) {
        long crc = INITIALCRC;
        for (byte aBuffer : buffer) {
            crc = sCrcTable[(((int) crc) ^ aBuffer) & 0xff] ^ (crc >> 8);
        }
        return crc;
    }

    public static byte[] getBytes(String in) {
        byte[] result = new byte[in.length() * 2];
        int output = 0;
        for (char ch : in.toCharArray()) {
            result[output++] = (byte) (ch & 0xFF);
            result[output++] = (byte) (ch >> 8);
        }
        return result;
    }
}
