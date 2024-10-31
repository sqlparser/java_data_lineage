package com.gudusoft.datalineage.demo.dto;

import javax.validation.constraints.NotEmpty;

public class DataflowRequest {
    @NotEmpty(message = "dbVendor not empt")
    private String dbVendor;
    @NotEmpty(message = "sqlText not empt")
    private String  sqlText;
    private boolean indirect = true;
    private boolean showConstantTable = true;
    private boolean simpleShowFunction = true;
    private String showResultSetTypes;
    private boolean ignoreRecordSet = true;

    private boolean showTransform = false;
    private boolean tableLevel = false;

    private String delimiter = ",";

    public String getDbVendor() {
        return dbVendor;
    }

    public void setDbVendor(String dbVendor) {
        this.dbVendor = dbVendor;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public boolean isIndirect() {
        return indirect;
    }

    public void setIndirect(boolean indirect) {
        this.indirect = indirect;
    }

    public boolean isShowConstantTable() {
        return showConstantTable;
    }

    public void setShowConstantTable(boolean showConstantTable) {
        this.showConstantTable = showConstantTable;
    }

    public boolean isSimpleShowFunction() {
        return simpleShowFunction;
    }

    public void setSimpleShowFunction(boolean simpleShowFunction) {
        this.simpleShowFunction = simpleShowFunction;
    }

    public String getShowResultSetTypes() {
        return showResultSetTypes;
    }

    public void setShowResultSetTypes(String showResultSetTypes) {
        this.showResultSetTypes = showResultSetTypes;
    }

    public boolean isIgnoreRecordSet() {
        return ignoreRecordSet;
    }

    public void setIgnoreRecordSet(boolean ignoreRecordSet) {
        this.ignoreRecordSet = ignoreRecordSet;
    }

    public boolean isTableLevel() {
        return tableLevel;
    }

    public void setTableLevel(boolean tableLevel) {
        this.tableLevel = tableLevel;
    }

    public boolean isShowTransform() {
        return showTransform;
    }

    public void setShowTransform(boolean showTransform) {
        this.showTransform = showTransform;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
