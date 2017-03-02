package com.starter.dinerssecrets.utilities.resolvers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.starter.dinerssecrets.databases.STMaterialsDBHelper;
import com.starter.dinerssecrets.models.STMaterialsItem;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wulei on 2017/3/2.
 */

public class STMaterialsResolver {
    private final static  String url = "http://www.chinacaipu.com/shicai/";

    private Context mContext;

    public STMaterialsResolver(Context context) {
        mContext = context;
    }

    public void resolveHtml() {
        Observable.create(new ObservableOnSubscribe<Map<String, List<STMaterialsItem>>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, List<STMaterialsItem>>> e) throws Exception {
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
                resolveMaterials(html);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("MaterialResolver", "下载食材完成");
                    }
                })
                .subscribe();
    }

    private Map<String, List<STMaterialsItem>> resolveMaterials(String html) {
        Document htmlDoc = Jsoup.parse(html);
        Map<String, List<STMaterialsItem>> items = null;
        Elements e_types = htmlDoc.select("dl.fd_dl");
        if(e_types.size() > 0) {
            STMaterialsDBHelper dbHelper = new STMaterialsDBHelper(mContext);
            items = new HashMap<>();
            for (Element e_type : e_types) {
                String type_name = e_type.child(0).ownText();
                Elements e_materials = e_type.select("img");
                if(e_materials.size() > 0) {
                    ArrayList<STMaterialsItem> materials = new ArrayList<>();
                    for (Element e_material : e_materials) {
                        STMaterialsItem material = new STMaterialsItem();
                        material.type = type_name;
                        material.name = e_material.attr("alt");
                        material.image = e_material.attr("src");
                        dbHelper.insert(material);
                        materials.add(material);
                    }
                    items.put(type_name, materials);
                }
            }
        }
        return items;
    }
}
