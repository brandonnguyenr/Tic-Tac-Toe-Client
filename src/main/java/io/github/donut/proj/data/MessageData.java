package io.github.donut.proj.data;

public class MessageData extends AbstractDataType {
    public final String message;

    private MessageData(String message) {
        setType("MessageData");
        this.message = message;
    }

    public static IDataType of(String message) {
        return new MessageData(message);
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
