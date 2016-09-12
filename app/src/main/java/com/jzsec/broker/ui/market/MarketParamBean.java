package com.jzsec.broker.ui.market;

/**
 * Created by zhaopan on 16/8/25.
 * e-mail: kangqiao610@gmail.com
 */
public class MarketParamBean {

    /**
     * 证券名称
     */
    protected String name = "";
    /**
     * 证券代码
     */
    protected String code = "";
    /**
     * 证券市场
     */
    protected String market = "";
    /**
     * 证券类型
     */
    protected int type = -999;

    public MarketParamBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MarketParamBean{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", market='" + market + '\'' +
                ", type=" + type +
                '}';
    }
}
