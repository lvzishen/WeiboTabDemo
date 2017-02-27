package com.ifengart.tablistdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifengart.tablistdemo.R;
import com.ifengart.tablistdemo.UserNickname;
import com.ifengart.tablistdemo.UserNumber;
import com.ifengart.tablistdemo.recyclerview.WrapperRecyclerAdapter;


/**
 * Created by lvzishen on 16/11/2.
 */
public class DemoWrapperRecyclerAdapter extends WrapperRecyclerAdapter {
    private final int TYPE_ONE = 10;
    private final int TYPE_TWO = 11;

    public DemoWrapperRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderNormal(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_normal, parent, false);
            return new NormalHolder(view);
        }
        if (viewType == TYPE_TWO) {
            View view2 = LayoutInflater.from(context).inflate(R.layout.item_normal_two, parent, false);
            return new NormalHolder2(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolderNormal(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalHolder) {
            final UserNumber art = (UserNumber) list.get(position);
            ((NormalHolder) holder).textView.setText(art.follows);
            ((NormalHolder) holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("position", position + "");
                }
            });
        }
        if (holder instanceof NormalHolder2) {
            final UserNickname huodong = (UserNickname) list.get(position);
            ((NormalHolder2) holder).textView.setText(huodong.uname);
//            Glide.with(context)
//                    .load(huodong.image)
//                    .placeholder(R.color.default_image)
//                    .into(((NormalHolder2) holder).imageView);
            ((NormalHolder2) holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("position", position + "");
                }
            });
        }
    }


    @Override
    public int getItemViewTypeNormal(int position) {
        if (list.get(position) instanceof UserNickname) {
            return TYPE_TWO;
        } else {
            return TYPE_ONE;
        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public NormalHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_textview);
        }


    }

    class NormalHolder2 extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public NormalHolder2(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_textview);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }


    }

}
