package com.stone.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

import java.util.Date;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/


@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

private void fill(MetaObject metaObject, String fieldName, Consumer<String> consumer) {
if (this.getFieldValByName(fieldName, metaObject) == null && metaObject.hasSetter(fieldName)) {
consumer.accept(fieldName);
}
}

@Override
public void insertFill(MetaObject metaObject) {
fill(metaObject, "createTime", fieldName -> this.strictInsertFill(metaObject, fieldName, Date.class, new Date()));

// 3.3.0 版本之后
// this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
// this.fillStrategy(metaObject,"createTime",new Date());

// 3.3.0 版本之前
// this.setInsertFieldValByName("createTime", new Date(), metaObject);
// this.setFieldValByName("createTime", new Date(), metaObject);
}

@Override
public void updateFill(MetaObject metaObject) {
fill(metaObject, "updateTime", fieldName -> this.strictUpdateFill(metaObject, fieldName, Date.class, new Date()));

// 3.3.0 版本之后
// this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
// this.fillStrategy(metaObject,"updateTime",new Date());

// 3.3.0 版本之前
// this.setUpdateFieldValByName("updateTime", new Date(), metaObject);
// this.setFieldValByName("updateTime", new Date(), metaObject);
}
}
