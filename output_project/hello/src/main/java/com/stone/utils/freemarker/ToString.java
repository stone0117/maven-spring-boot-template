package com.stone.utils.freemarker;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class ToString implements TemplateMethodModelEx {
@Override
public Object exec(List arguments) throws TemplateModelException {
StringBuilder stringBuilder = new StringBuilder();
for (Object argument : arguments) {
if (argument == null) { continue;}
try {
Class<?> aClass = argument.getClass();
if (argument instanceof SimpleSequence) {
SimpleSequence simpleSequence = (SimpleSequence) argument;
//noinspection deprecation
List list = simpleSequence.toList();
String string = list.toString();
stringBuilder.append(string).append("\n");
} else if (argument instanceof StringModel) {
StringModel stringModel   = (StringModel) argument;
Object      wrappedObject = stringModel.getWrappedObject();
String string = wrappedObject.toString();
stringBuilder.append(string).append("\n");
}
} catch (Exception e) { e.printStackTrace();}
}
return stringBuilder.toString();
}
}

