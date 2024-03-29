package com.xiaxinyu.controller;

import com.xiaxinyu.entity.Evaluation;
import com.xiaxinyu.entity.Member;
import com.xiaxinyu.service.MemberService;
import com.xiaxinyu.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/register.html")
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
    }

    @PostMapping("/registe")
    @ResponseBody
    public Map register(String vc, String username, String password, String nickname, HttpServletRequest request) {
        //正确验证码
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        //验证码对比
        Map result = new HashMap();
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                memberService.createMember(username, password, nickname);
                result.put("code", 0);
                result.put("msg", "success");
            } catch (BussinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session) {
        //正确验证码
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");
        //验证码对比
        Map result = new HashMap();
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                Member member = memberService.checkLogin(username, password);
                session.setAttribute("loginMember", member);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BussinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }

    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId, Long bookId, Integer readState) {
        Map result = new HashMap();
        try {
            memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code","0");
            result.put("msg","success");
        }catch(BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }

    @PostMapping("/evaluate")
    @ResponseBody
    public Map evaluate(Long memberId, Long bookId,Integer score,String content){
        Map result = new HashMap();
        try {
            Evaluation evaluation = memberService.evaluate(memberId, bookId, score,content);
            result.put("code","0");
            result.put("msg","success");
            result.put("evaluation",evaluation);
        }catch(BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }

    @PostMapping("/enjoy")
    @ResponseBody
    public Map evaluate(String evaluationId){
        Map result = new HashMap();
        Long nEvaluationLongId = Long.valueOf(evaluationId.replaceAll(",", ""));
        try {
            Evaluation evaluation = memberService.enjoy(nEvaluationLongId);
            System.out.println("evaluationId : "  + evaluationId);
            System.out.println(evaluation.getEnjoy());
            result.put("code","0");
            result.put("msg","success");
            result.put("evaluation",evaluation);
        }catch(BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }


}
