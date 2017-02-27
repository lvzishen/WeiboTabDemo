package com.ifengart.tablistdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lvzishen on 17/2/22.
 */
public class UserTitle extends FrameLayout implements View.OnClickListener {
    RelativeLayout rl_activity;
    RelativeLayout rl_salt;
    TextView tv_activity;
    TextView tv_salt;
    View v_activity;
    View v_salt;
    OnTitleClickListener onTitleClickListener;

    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public UserTitle(Context context) {
        super(context);
        init(context);
    }

    public UserTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        View view = inflate(context, R.layout.activity_user_title, this);
        rl_activity = (RelativeLayout) view.findViewById(R.id.activity_user_title_tabactivies_rl);
        rl_activity.setOnClickListener(this);
        rl_salt = (RelativeLayout) view.findViewById(R.id.activity_user_title_tabsalt_rl);
        rl_salt.setOnClickListener(this);
        tv_activity = (TextView) view.findViewById(R.id.activity_user_title_tabactivies_tv);
        tv_salt = (TextView) view.findViewById(R.id.activity_user_title_tabsalt_tv);
        v_activity = view.findViewById(R.id.activity_user_title_tabactivies_view);
        v_salt = view.findViewById(R.id.activity_user_title_tabsalt_view);
        tv_activity.setSelected(true);
        v_activity.setVisibility(View.VISIBLE);
        tv_salt.setSelected(false);
        v_salt.setVisibility(View.GONE);
    }

    public void setTabActivity() {
        tv_activity.setSelected(true);
        v_activity.setVisibility(VISIBLE);
        tv_salt.setSelected(false);
        v_salt.setVisibility(GONE);
    }

    public void setTabSalt() {
        tv_salt.setSelected(true);
        v_salt.setVisibility(VISIBLE);
        tv_activity.setSelected(false);
        v_activity.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_user_title_tabactivies_rl:
                if (!tv_activity.isSelected()) {
                    tv_activity.setSelected(true);
                    v_activity.setVisibility(VISIBLE);
                    tv_salt.setSelected(false);
                    v_salt.setVisibility(GONE);
                    onTitleClickListener.onClickActivies(v);
                }
                break;
            case R.id.activity_user_title_tabsalt_rl:
                if (!tv_salt.isSelected()) {
                    tv_salt.setSelected(true);
                    v_salt.setVisibility(VISIBLE);
                    tv_activity.setSelected(false);
                    v_activity.setVisibility(GONE);
                    onTitleClickListener.onClickSalt(v);
                }
                break;
        }
    }

    public interface OnTitleClickListener {
        void onClickActivies(View view);

        void onClickSalt(View view);
    }
}
