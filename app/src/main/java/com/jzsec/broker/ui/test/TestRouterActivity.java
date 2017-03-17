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
// imaster://?actiontype=pfs&loginmobile=13300000010&token=aaaabbbbccccddddaaaabbbbccccdddd&actid=A020001&bs_flag=0&stock_code=000018&bs_quantity=300&bs_price=28.61032
@Router(value = {"broker2://www.jzsec.com/trade", "trade", "http://www.jzsec.com/trade"},
        stringParams = {"token", "loginmobile"},
        longParams = "actiontype",
        transfer = "actiontype=>type"
)
public class TestRouterActivity extends BaseActivity {

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
        tvTitle.setText("TestRouterActivity11111111");
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
