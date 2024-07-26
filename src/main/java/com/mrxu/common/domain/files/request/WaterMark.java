package com.mrxu.common.domain.files.request;

/**
 * 水印信息
 *
 * @author feng.chuang
 * @date 2021-05-05 21:18
 */
public class WaterMark {
    /**
     * 水印类型
     */
    private  Integer type;

    /**
     * 水印值
     */
    private String value;

    /**
     * 颜色与透明度(例如rgba(255,0,255,0.6))
     */
    private String fillstyle;

    /**
     * 字体
     */
    private String font;

    /**
     * 字体倾斜度
     */
    private String rotate;

    /**
     * 水平距离
     */
    private Integer horizontal;

    /**
     * 垂直距离
     */
    private  Integer vertical;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFillstyle() {
        return fillstyle;
    }

    public void setFillstyle(String fillstyle) {
        this.fillstyle = fillstyle;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    public Integer getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Integer horizontal) {
        this.horizontal = horizontal;
    }

    public Integer getVertical() {
        return vertical;
    }

    public void setVertical(Integer vertical) {
        this.vertical = vertical;
    }
}
