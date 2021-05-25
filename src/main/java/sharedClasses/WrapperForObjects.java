package sharedClasses;

import java.io.Serializable;

public class WrapperForObjects implements Serializable {
    private Object object;
    private String description;

    public WrapperForObjects(Object object, String description){
        this.object = object;
        this.description = description;
    }

    public Object getObject() {
        return object;
    }

    public String getDescription() {
        return description;
    }
}
