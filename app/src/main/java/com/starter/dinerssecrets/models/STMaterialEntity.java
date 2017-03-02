package com.starter.dinerssecrets.models;

import java.util.ArrayList;

/**
 * Created by wulei on 2017/3/2.
 */

public class STMaterialEntity {
    public ArrayList<MaterialGroup> allMaterials;

    public class MaterialGroup {
        public String type;
        public ArrayList<STMaterialsItem> materials;
    }
}
