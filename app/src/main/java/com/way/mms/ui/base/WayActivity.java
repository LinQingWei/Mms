package com.way.mms.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.way.mms.common.utils.UiUtils;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.31
 *     desc  :
 * </pre>
 */

public class WayActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UiUtils.redirectToPermissionCheckIfNeeded(this)) {
            return;
        }
        super.onCreate(savedInstanceState);
    }

}
