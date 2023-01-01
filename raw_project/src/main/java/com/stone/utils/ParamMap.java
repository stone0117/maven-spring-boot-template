package com.stone.utils;

import java.util.HashMap;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class ParamMap extends HashMap<String, Object> {

@Override
public ParamMap put(String key, Object value) {
super.put(key, value);
return this;
}

public static ParamMap newInstance(){
return new ParamMap();
}
}
