package io.github.donut.proj.data;

import io.github.donut.proj.utils.GsonWrapper;

import java.io.InvalidClassException;

public class AccountData extends AbstractDataType {
    public final String flag;
    public String userName;
    public String password;
    public String firstName;
    public String lastName;

    private AccountData(String userName, String password, String firstName, String lastName, String flag) {
        setType("Account");
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.flag = flag;
    }

    private AccountData(AccountData temp) {
        this(temp.userName, temp.password, temp.firstName, temp.lastName, temp.flag);
    }

    public static IDataType of(String userName, String password, String firstName, String lastName, String flag) {
        return new AccountData(userName, password, firstName, lastName, flag);
    }

    public static IDataType of(String json) throws InvalidClassException {
        AccountData obj = GsonWrapper.fromJson(json, AccountData.class);
        if (obj == null) throw new InvalidClassException("incompatible json -> { not of type AccountData.class }");
        return new AccountData(obj);
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
