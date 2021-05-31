package sharedClasses.utils;

import java.io.Serializable;

public class WrapperForObjects implements Serializable {
    private final Object object;
    private final String description;

    public WrapperForObjects(Object object, String description) {
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
