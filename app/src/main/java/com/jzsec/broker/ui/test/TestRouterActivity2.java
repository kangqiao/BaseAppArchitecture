package com.jzsec.broker.ui.test;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseActivity;

import java.util.Set;

import butterknife.BindView;

/**
 * Created by zhaopan on 2017/3/9.
 * e-mail: kangqiao610@gmail.com
 */
@Router(value = {"broker2://www.jzsec.com/trade2", "trade2", "http://www.jzsec.com/trade2"},
        stringParams = {"actiontype", "loginmobile", "token", "actid", "bs_flag", "stock_code"},
        intParams = {"bs_quantity","page_index"},
        doubleParams = "bs_price",
        transfer = {"stock_code=>code", "bs_quantity=>amount", "bs_price=>price", "page_index=>key_index"}
)
public class TestRouterActivity2 extends BaseActivity {

    @BindView(R.id.tv_param_info)
    TextView textView;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.act_test_router;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("TestRouterActivity2");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Set<String> keys = extras.keySet();

            for (String key : keys) {
                textView.append(key + "=>");
                Object v = extras.get(key);
                if (v != null) {
                    textView.append(v + "=>" + v.getClass().getSimpleName());
                } else {
                    textView.append("null");
                }
                textView.append("\n\n");
            }

        }
    }
}
