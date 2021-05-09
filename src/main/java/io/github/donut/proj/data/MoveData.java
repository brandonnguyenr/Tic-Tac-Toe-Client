package io.github.donut.proj.data;

import io.github.donut.proj.utils.GsonWrapper;

import java.io.InvalidClassException;

public class MoveData extends AbstractDataType {
    public final String id;
    public final int x;
    public final int y;
    public final String token;

    private MoveData(String id, int x, int y, String token) {
        setType("MoveData");
        this.id = id;
        this.x = x;
        this.y = y;
        this.token = token;
    }

    private MoveData(MoveData temp) {
        this(temp.id, temp.x, temp.y, temp.token);
    }

    public static IDataType of(String id, int x, int y, String token) {
        return new MoveData(id, x, y, token);
    }

    public static IDataType of(String json) throws InvalidClassException {
        MoveData obj = GsonWrapper.fromJson(json, MoveData.class);
        if (obj == null) throw new InvalidClassException("incompatible json -> { not of type MoveData.class }");
        return new MoveData(obj);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        super.type = type;
    }
}
