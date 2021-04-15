package io.github.donut.proj.data;

public abstract class AbstractDataType implements IDataType {
    protected String type;
    public abstract String getType();
    public abstract void setType(String type);
}
