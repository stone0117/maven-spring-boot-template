package com.stone.utils.freemarker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class ToPrettyJson implements TemplateMethodModelEx {
@Override
public Object exec(List arguments) throws TemplateModelException {
StringBuilder stringBuilder = new StringBuilder();
for (Object argument : arguments) {
if (argument == null) { continue;}
try {
Class<?> aClass = argument.getClass();
// dlog(aClass, "aClass");
// dlog(aClass, "aClass");
// dlog(aClass, "aClass");
// dlog(argument, "argument");
// dlog(argument, "argument");
// dlog(argument, "argument");
if (argument instanceof SimpleSequence) {
SimpleSequence simpleSequence = (SimpleSequence) argument;
//noinspection deprecation
List list = simpleSequence.toList();
// String jsonString = JSON.toJSONString(list, true);
String jsonString = stringify(list);
stringBuilder.append(jsonString).append("\n");
} else if (argument instanceof StringModel) {
StringModel stringModel   = (StringModel) argument;
Object      wrappedObject = stringModel.getWrappedObject();
// String      jsonString    = JSON.toJSONString(wrappedObject, true);
String jsonString = stringify(wrappedObject);
stringBuilder.append(jsonString).append("\n");
} else if (argument instanceof SimpleHash) {
SimpleHash simpleHash = (SimpleHash) argument;
Map        map        = simpleHash.toMap();
Optional   first      = map.keySet().stream().findFirst();
if (first.isPresent()) {
Object o = first.get();
if (!(o instanceof String)) { throw new Exception("!!!错误: Freemarker map key必须是String类型");}
}
String jsonString = stringify(map);
stringBuilder.append(jsonString).append("\n");
} else if (argument instanceof SimpleNumber) {
SimpleNumber simpleNumber = (SimpleNumber) argument;
String       s            = simpleNumber.toString();
stringBuilder.append(s).append("\n");
} else if (argument instanceof SimpleScalar) {
SimpleScalar simpleScalar = (SimpleScalar) argument;
String       s            = simpleScalar.toString();
stringBuilder.append(String.format("\"%s\"", s)).append("\n");
}
} catch (Exception e) {
e.printStackTrace();
stringBuilder.append(e.getMessage()).append("\n");
}
}
return stringBuilder.toString();
}

private String stringify(Object object) {
GsonBuilder gsonBuilder = new GsonBuilder();
gsonBuilder.setDateFormat("yyyy-MM-dd");
Gson   gson       = gsonBuilder.setPrettyPrinting().create();
String jsonString = gson.toJson(object);
return jsonString;
}
}

