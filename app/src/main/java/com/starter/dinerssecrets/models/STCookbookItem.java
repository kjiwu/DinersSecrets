package com.starter.dinerssecrets.models;

import java.io.Serializable;

/**
 * Created by wulei on 2017/2/27.
 */
public class STCookbookItem implements Serializable {
    private static final long serialVersionUID = 518872429669715930L;

    public String cooking_id;

    public String name;

    public String intro;

    public String image;

    public String imageName;

    public String url;

    public String difficulty;

    public boolean isCollection;
}
