package com.zheng.ucenter.web.controller;

import com.zheng.common.base.BaseController;
import com.zheng.common.util.AESUtil;
import com.zheng.ucenter.common.constant.UcenterResult;
import com.zheng.ucenter.common.constant.UcenterResultConstant;
import com.zheng.ucenter.dao.model.UcenterUser;
import com.zheng.ucenter.dao.model.UcenterUserExample;
import com.zheng.ucenter.rpc.api.UcenterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 注册控制器
 * Created by shuzheng on 2017/5/2.
 */
@Controller
@RequestMapping("/userLogin")
public class SignController extends BaseController {

    @Autowired
    private UcenterUserService ucenterUserService;

    private static Logger _log = LoggerFactory.getLogger(SignController.class);

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {

        return jsp("/reg");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public UcenterResult signup(UcenterUser user ) {
        if (null==user){
            return new UcenterResult(UcenterResultConstant.FAILED, "用户名密码不能为空!");
        }

        user.setPassword(AESUtil.AESEncode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        int i=ucenterUserService.insert(user);

        if (i<=0){
            return new UcenterResult(UcenterResultConstant.FAILED, "用户注册失败!");
        }

        return new UcenterResult(UcenterResultConstant.SUCCESS, "注册成功！");
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Model model) {

        return thymeleaf("/login");
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public UcenterResult signin(UcenterUser user) {
        UcenterUserExample ucenterUserExample = new UcenterUserExample();
        ucenterUserExample.createCriteria().andNicknameEqualTo(user.getNickname());
        List<UcenterUser> ucenterUsers =ucenterUserService.selectByExample(ucenterUserExample);
        if (ucenterUsers==null||ucenterUsers.isEmpty()){
            return  new UcenterResult(UcenterResultConstant.FAILED,"The user named "+user.getNickname()+" doesn't exit;");
        }

        UcenterUser ucenterUser = ucenterUsers.get(0);
        if (!Objects.equals(AESUtil.AESEncode(user.getPassword()), ucenterUser.getPassword())){
            return  new UcenterResult(UcenterResultConstant.FAILED,"Wrong password!");

        }

        return new UcenterResult(UcenterResultConstant.SUCCESS, "Login succeed!");
    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    @ResponseBody
    public String index(Model model) {

        return "signout";
    }

    @RequestMapping(value = "/password_reset", method = RequestMethod.GET)
    public String password_reset(Model model) {

        return thymeleaf("/password");
    }

}