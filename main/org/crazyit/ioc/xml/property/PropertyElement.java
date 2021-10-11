package org.crazyit.ioc.xml.property;

import org.crazyit.ioc.xml.construct.DataElement;

public class PropertyElement {
    private String name;
    private DataElement value;

    public PropertyElement(String name, DataElement value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public DataElement getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(DataElement value) {
        this.value = value;
    }
}
