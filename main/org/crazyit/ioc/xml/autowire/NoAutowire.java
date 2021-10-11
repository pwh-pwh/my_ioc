package org.crazyit.ioc.xml.autowire;

public class NoAutowire implements Autowire{
    private String value;
    @Override
    public String getValue() {
        return "no";
    }
    public NoAutowire(String value) {
        this.value = value;
    }

}
