package com.example.lenovo.eastofbeijing.inter;

import android.view.View;

public interface IBase {
    int getContentLayout();

    void inject();

    void initView(View view);
}
