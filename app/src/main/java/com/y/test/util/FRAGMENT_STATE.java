package com.y.test.util;

import java.util.HashMap;
import java.util.Map;

public enum  FRAGMENT_STATE {
    TEXT ("text"), IMAGE("image"), URL("url");
    private String stateValue;
    private static final Map<String, FRAGMENT_STATE> FRAGMENT_STATE_MAP = new HashMap<>();
    FRAGMENT_STATE(String value) { this.stateValue = value;}

    public String getStateValue() {
        return stateValue;
    }
    static {
        for (FRAGMENT_STATE state : FRAGMENT_STATE.values()){
            FRAGMENT_STATE_MAP.put(state.getStateValue(), state);
        }
    }
    public static FRAGMENT_STATE get(String stateValue){
        return FRAGMENT_STATE_MAP.get(stateValue);
    }
    public static FRAGMENT_STATE setStateValue(String mValue) {
        for(FRAGMENT_STATE status : values()) {
            if (status.stateValue == mValue) {
                return status;
            }
        }
        return TEXT;
    }
    public String getState(){
        return stateValue;
    }
}
