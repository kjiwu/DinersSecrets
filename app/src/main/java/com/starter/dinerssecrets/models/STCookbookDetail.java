package com.starter.dinerssecrets.models;

import java.util.List;

/**
 * Created by wulei on 2017/2/28.
 */

public class STCookbookDetail {
    public String title;
    public String cooking_id;
    public String image;
    public String time;
    public String difficulty;
    public String intro;
    public List<STCookbookMaterial> materials;
    public List<STCookbookStep> steps;
    public List<String> tips;
    public List<String> complete_pic;
}
