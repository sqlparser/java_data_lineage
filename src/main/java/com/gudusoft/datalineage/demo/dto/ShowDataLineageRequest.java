package com.gudusoft.datalineage.demo.dto;

import javax.validation.constraints.NotEmpty;

public class ShowDataLineageRequest {
    @NotEmpty(message = "dbVendor not empt")
    private String dbVendor;
    @NotEmpty(message = "text not empt")
    private String  text;
    @NotEmpty(message = "format not empt")
    private String  format;

    public String getDbVendor() {
        return dbVendor;
    }

    public void setDbVendor(String dbVendor) {
        this.dbVendor = dbVendor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
