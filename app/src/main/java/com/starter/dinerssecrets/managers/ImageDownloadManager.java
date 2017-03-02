package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.starter.dinerssecrets.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wulei on 2017/2/27.
 */

public class ImageDownloadManager {

    public final static String THUMB_IMAGE_DIR = "/thumbs";
    public final static String IMAGE_DIR = "/images";

    public final static int IMAGE_TYPE_THUMB = 0;
    public final static int IMAGE_TYPE_ORIGIN = 1;

    private Context mContext;

    private boolean mIsCancel = false;

    public void setCancel(boolean isCancel) {
        mIsCancel = isCancel;
    }

    public ImageDownloadManager(Context context) {
        mContext = context;
    }

    private void downloadImageFromUrl(final String imageName, final String url, final String cooking_id, final int type, final ImageView imageView) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                if(!NetwrokManager.isNetworkAvailable(mContext)) {
                    e.onNext(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
                    return;
                }

                InputStream in = null;
                try {
                    URL l_url = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) l_url.openConnection();
                    in = conn.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    String path = mContext.getFilesDir() + THUMB_IMAGE_DIR;
                    if(type == IMAGE_TYPE_ORIGIN) {
                        path = mContext.getFilesDir() + IMAGE_DIR + "/" + cooking_id;
                        options.inSampleSize = 1;
                    }
                    File dir = new File(path);
                    if(!dir.exists()) {
                        dir.mkdir();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);

                    path += "/" + imageName;
                    File file = new File(path);
                    if (!file.exists() && null != bitmap) {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);
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

    private boolean downloadImageFromAssets(String imageName, int type, ImageView imageView) {
        boolean result = false;

        if(null != mContext) {
            AssetManager manager = mContext.getAssets();
            InputStream in = null;
            try {
                String path = imageName;
                if(type == IMAGE_TYPE_THUMB) {
                    path = "thumbs/" + imageName;
                }
                in = manager.open(path);
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

    private boolean downloadImageFromLocal(String imageName, int type, ImageView imageView) {
        boolean result = false;
        if(null != mContext) {
            try {
                String path = mContext.getFilesDir() + "/" + imageName;
                if(type == IMAGE_TYPE_THUMB) {
                    path = mContext.getFilesDir() + "/thumbs/" + imageName;
                }

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

    public void downloadImage(String imageName, String url, String cooking_id, int type, ImageView imageView) {
        if(!downloadImageFromAssets(imageName, type, imageView)) {
            if(!downloadImageFromLocal(imageName, type, imageView)) {
                downloadImageFromUrl(imageName, url, cooking_id, type, imageView);
            }
        }
    }
}
