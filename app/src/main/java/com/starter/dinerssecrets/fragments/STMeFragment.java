package com.starter.dinerssecrets.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.managers.CacheManager;
import com.starter.dinerssecrets.managers.YouMiADManager;
import com.starter.dinerssecrets.utilities.StringHelper;

import net.youmi.android.normal.spot.SpotManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.starter.dinerssecrets.managers.CacheManager.getCacheSize;

/**
 * Created by wulei on 2017/2/27.
 */

public class STMeFragment extends STBaseFragment {

    private TextView mVersionTextView;

    private RelativeLayout mClearItemLayout;
    private TextView mCacheSizeTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_me, null);

        mVersionTextView = (TextView) view.findViewById(R.id.textview_version);
        mVersionTextView.setText(StringHelper.getAppVersionName(getActivity()));

       initCacheSize(view);

        YouMiADManager.insertADView(getActivity());

        mClearItemLayout = (RelativeLayout) view.findViewById(R.id.cache_item_container);
        mClearItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTitle(R.string.app_name);
                dialog.setMessage(getContext().getResources().getString(R.string.about_clear_cache_dialog_content));
                dialog.show();

                Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        CacheManager.clearCache(getActivity());
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                dialog.dismiss();
                                mCacheSizeTextView.setText(getCacheSize(getActivity()));
                            }
                        })
                        .subscribe();
            }
        });

        return view;
    }

    private void initCacheSize(View view) {
        mCacheSizeTextView = (TextView) view.findViewById(R.id.textview_about_cache_size);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                 String size = CacheManager.getCacheSize(getActivity());
                e.onNext(size);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        mCacheSizeTextView.setText(o);
                    }
                }).subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        SpotManager.getInstance(getActivity()).onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        SpotManager.getInstance(getActivity()).onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(getActivity()).onDestroy();
    }
}
