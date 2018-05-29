package com.example.lenovo.eastofbeijing.component;

import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.classify.ClassifyFragment;
import com.example.lenovo.eastofbeijing.ui.details.ListDetailsActivity;
import com.example.lenovo.eastofbeijing.ui.dingdan.MakeSureOrderActivity;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentAllOrder;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentAlreadyPayOrder;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentCacelOrder;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentWaitOrder;
import com.example.lenovo.eastofbeijing.ui.find.FragmentFaXian;
import com.example.lenovo.eastofbeijing.ui.homepage.FragmentHome;
import com.example.lenovo.eastofbeijing.ui.listofgoods.ListActivity;
import com.example.lenovo.eastofbeijing.ui.login.LoginActivity;
import com.example.lenovo.eastofbeijing.ui.mine.FragmentMy;
import com.example.lenovo.eastofbeijing.ui.mine.UserInfoActivity;
import com.example.lenovo.eastofbeijing.ui.regist.RegistActivity;
import com.example.lenovo.eastofbeijing.ui.shopcart.FragmentShoppingCart;
import com.example.lenovo.eastofbeijing.ui.shopcart.ShoppingCartActivity;

import dagger.Component;

@Component(modules = HttpModule.class)
public interface HttpComponent {

    void inject(LoginActivity loginActivity);

    void inject(FragmentHome homePageFragment);

    void inject(ClassifyFragment classifyFragment);

    void inject(ListActivity listActivity);

    void inject(ListDetailsActivity listDetailsActivity);

    void inject(FragmentFaXian fragmentFaXian);

    void inject(ShoppingCartActivity shoppingCartActivity);

    void inject(FragmentShoppingCart FragmentShoppingCart);

    void inject(FragmentMy fragmentMy);

    void inject(UserInfoActivity userInfoActivity);

    void inject(FragmentAllOrder fragmentAllOrder);

    void inject(FragmentWaitOrder fragmentWaitOrder);

    void inject(FragmentAlreadyPayOrder fragmentAlreadyPayOrder);

    void inject(FragmentCacelOrder fragmentCacelOrder);

    void inject(MakeSureOrderActivity makeSureOrderActivity);

    void inject(RegistActivity registActivity);
}
