package com.jzsec.broker.ui.market;

/**
 * Created by zhaopan on 16/9/3.
 * e-mail: kangqiao610@gmail.com
 */
public interface ComplexRightChangeListener {
    //当前状态的复权是前复权还是后复权 -1普通状态 1 前复权 2 后复权
    int TYPE_COMPLEX_EX_RiGHT = -1;
    int TYPE_COMPLEX_BEFORE_RIGHT = 1;
    int TYPE_COMPLEX_AFTER_RiGHT = 2;
    int TYPE_COMPLEX_RiGHT_NONE = -1000;

    void changeComplexRight(int complexRightType);

    int getComplexRightType();
}