package com.starter.dinerssecrets.utilities.resolvers;

import android.content.Context;
import android.util.Log;

import com.starter.dinerssecrets.models.STCookbookDetail;
import com.starter.dinerssecrets.models.STCookbookMaterial;
import com.starter.dinerssecrets.models.STCookbookStep;
import com.starter.dinerssecrets.utilities.StringHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wulei on 2017/2/28.
 */

public class STCookbookDetailResolver {

    public final static String ST_RESOLVER_TAG = "STCookbookDetailResolver";

    public interface OnCookbookDetailCompletedListener {
        void onCompleted(STCookbookDetail detail);
    }

    private Context mContext;

    public STCookbookDetailResolver(Context context) {
        mContext = context;
    }

    public void resolveHtml(final String url, final OnCookbookDetailCompletedListener listener) {
        Observable.create(new ObservableOnSubscribe<STCookbookDetail>() {
            @Override
            public void subscribe(ObservableEmitter<STCookbookDetail> e) throws Exception {
                URL newUrl = new URL(url);
                URLConnection connect = newUrl.openConnection();
                DataInputStream dis = new DataInputStream(connect.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(dis,"UTF-8"));
                String html = "";
                String readLine = null;
                while((readLine = in.readLine()) != null) {
                    html += readLine;
                }
                in.close();
                STCookbookDetail detail = resolve(StringHelper.getCookingId(url), html);
                if(null != detail) {
                    e.onNext(detail);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<STCookbookDetail>() {
                    STCookbookDetail mDetail;

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(STCookbookDetail value) {
                        mDetail = value;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if(null != listener) {
                            listener.onCompleted(mDetail);
                        }
                    }
                });
    }

    private STCookbookDetail resolve(String bookId, String source) {
        Document htmlDoc = Jsoup.parse(source);
        STCookbookDetail detail = new STCookbookDetail();
        Elements elements = htmlDoc.select("img.cp-show-pic");
        if(elements.size() > 0) {
            detail.image = elements.get(0).attr("src");
        }
        elements = htmlDoc.select("table.cp-show-tab tbody tr");
        if(elements.size() > 0) {
            detail.time = elements.get(1).ownText();
        }

        detail.materials = getMaterials(htmlDoc);
        detail.steps = getSteps(htmlDoc);
        detail.tips = getTips(htmlDoc);

        return detail;
    }

    //解析菜谱的烹饪难度，时间和食材
    private List<STCookbookMaterial> getMaterials(Document htmlDoc) {
        STCookbookMaterial material = new STCookbookMaterial();
        Elements material_table = htmlDoc.select("table.cp-show-tab");
        return null;
    }

    private List<STCookbookStep> getSteps(Document htmlDoc) {
        Elements e_steps = htmlDoc.select("div.cp-show-main-step-item");
        ArrayList<STCookbookStep> steps = null;
        if(e_steps.size() > 0) {
            steps = new ArrayList<>();
            for(int i = 0; i < e_steps.size(); i++) {
                STCookbookStep step = new STCookbookStep();
                Element e_step = e_steps.get(i);
                step.order = Integer.valueOf(e_step.child(0).ownText());
                step.step = e_step.child(1).ownText();
                step.image = e_step.child(2).attr("src");
                steps.add(step);
            }
        }
        return steps;
    }

    private List<String> getTips(Document htmlDoc) {
        Elements tips = htmlDoc.select("div.cp-show-main-trick");
        if(tips.size() > 0) {
            Element ps = tips.get(0).child(1);

        }
        return null;
    }
}
