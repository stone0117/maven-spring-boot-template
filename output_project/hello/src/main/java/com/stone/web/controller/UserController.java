package com.stone.web.controller;

import com.stone.domain.PageResult;
import com.stone.domain.User;
import com.stone.pojo.qo.UserQueryObject;
import com.stone.service.UserService;
import com.stone.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* Created by stone on 2023/01/01
*
* @author stone
*/
@RestController
@RequestMapping(path = "/user")
// @CrossOrigin
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/saveOrUpdate")
  public Object saveOrUpdate(User user) {
    userService.saveOrUpdate(user);
    return JsonResult.success();
  }

  @RequestMapping("/delete")
  public Object delete(@RequestParam("id") Integer id) {
    // User user = new User();
    // user.setId(id);
    // userService.removeByIdWithFill(user);
    userService.removeById(id);
    return JsonResult.success();
  }

  @RequestMapping("/update")
  public Object update(User user) {
    boolean success = userService.updateById(user);
    return JsonResult.success();
  }

  @RequestMapping("/list")
  public Object list(UserQueryObject qo) {
    PageResult<User> pageResult = userService.queryForList(qo);
    return JsonResult.success(pageResult);
  }

  @RequestMapping("/input")
  public Object input(@RequestParam("id") Integer id) {
    if (id != null) {
      User user = userService.getById(id);
      return JsonResult.success(user);
    }
    else {
      return JsonResult.success();
    }
  }
}


