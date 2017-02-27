package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.ScriptGroup;
import android.widget.ImageView;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wulei on 2017/2/27.
 */

public class ImageDownloadManager {

    private Context mContext;

    private boolean mIsCancel = false;

    public void setCancel(boolean isCancel) {
        mIsCancel = isCancel;
    }

    public ImageDownloadManager(Context context) {
        mContext = context;
    }

    private void downloadImageFromUrl(final String imageName, final String url, final ImageView imageView) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                URL l_url = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) l_url.openConnection();
                InputStream in = null;
                try {
                    in = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    String path = mContext.getFilesDir() + "/" + imageName;
                    File file = new File(path);
                    if (!file.exists() && null != bitmap) {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
                        out.flush();
                        out.close();
                    }
                    e.onNext(bitmap);
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
                finally {
                    if(null != in) {
                        in.close();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        if (null != bitmap && null != imageView) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
    }

    private boolean downloadImageFromAssets(String imageName, ImageView imageView) {
        boolean result = false;

        if(null != mContext) {
            AssetManager manager = mContext.getAssets();
            InputStream in = null;
            try {
                in = manager.open(imageName);
                if(null != in) {
                    result = true;
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    if (null != bitmap && null != imageView) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(null != in) {
                    try {
                        in.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    private boolean downloadImageFromLocal(String imageName, ImageView imageView) {
        boolean result = false;
        if(null != mContext) {
            try {
                String path = mContext.getFilesDir() + "/" + imageName;
                File file = new File(path);
                result = file.exists();
                if(result) {
                    FileInputStream in = new FileInputStream(path);
                    if (null != in) {
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        if (null != bitmap && null != imageView) {
                            imageView.setImageBitmap(bitmap);
                        }
                        in.close();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void downloadImage(String imageName, String url, ImageView imageView) {
        if(!downloadImageFromAssets(imageName, imageView)) {
            if(!downloadImageFromLocal(imageName, imageView)) {
                downloadImageFromUrl(imageName, url, imageView);
            }
        }
    }
}
