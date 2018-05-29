package com.example.lenovo.eastofbeijing;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.classify.ClassifyFragment;
import com.example.lenovo.eastofbeijing.ui.find.FragmentFaXian;
import com.example.lenovo.eastofbeijing.ui.homepage.FragmentHome;
import com.example.lenovo.eastofbeijing.ui.mine.FragmentMy;
import com.example.lenovo.eastofbeijing.ui.shopcart.FragmentShoppingCart;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private FragmentHome fragmentHome;
    private ClassifyFragment classifyFragment;
    private FragmentFaXian fragmentFaXian;
    private FragmentShoppingCart fragmentShoppingCart;
    private FragmentMy fragmentMy;
    private FragmentManager manager;
    private RadioButton rbHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rbHomepage = findViewById(R.id.rbHomepage);
        radioGroup = findViewById(R.id.rg);
        manager = getSupportFragmentManager();
        fragmentHome = new FragmentHome();
        manager.beginTransaction().add(R.id.flContent, fragmentHome).commit();
        rbHomepage.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        FragmentTransaction transaction = manager.beginTransaction();

        if (fragmentHome != null) {
            transaction.hide(fragmentHome);
        }
        if (classifyFragment != null) {
            transaction.hide(classifyFragment);
        }
        if (fragmentFaXian != null) {
            transaction.hide(fragmentFaXian);
        }
        if (fragmentShoppingCart != null) {
            transaction.hide(fragmentShoppingCart);
        }
        if (fragmentMy != null) {
            transaction.hide(fragmentMy);
        }


        switch (checkedId) {
            case R.id.rbHomepage:
                if (fragmentHome == null) {
                    fragmentHome = new FragmentHome();
                    transaction.add(R.id.flContent, fragmentHome);
                }else {
                    transaction.show(fragmentHome);
                }
                break;
            case R.id.rbClass:
                if (classifyFragment == null) {
                    classifyFragment = new ClassifyFragment();
                    transaction.add(R.id.flContent, classifyFragment);
                }else {
                    transaction.show(classifyFragment);
                }
                break;
            case R.id.rbFind:
                if (fragmentFaXian == null) {
                    fragmentFaXian = new FragmentFaXian();
                    transaction.add(R.id.flContent, fragmentFaXian);
                }else {
                    transaction.show(fragmentFaXian);
                }
                break;
            case R.id.rbShopCar:
                if (fragmentShoppingCart == null) {
                    fragmentShoppingCart = new FragmentShoppingCart();
                    transaction.add(R.id.flContent, fragmentShoppingCart);
                }else {
                    transaction.show(fragmentShoppingCart);
                }
                break;
            case R.id.rbMine:
                if (fragmentMy == null) {
                    fragmentMy = new FragmentMy();
                    transaction.add(R.id.flContent, fragmentMy);
                }else {
                    transaction.show(fragmentMy);
                }
                break;

            default:
                break;
        }

        transaction.commit();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void inject() {

    }
}
