package com.xiaxinyu.service;

import com.xiaxinyu.entity.Evaluation;
import com.xiaxinyu.entity.Member;
import com.xiaxinyu.entity.MemberReadState;

public interface MemberService {
    /**
     * 会员注册，创建新会员
     * @param username 用户名
     * @param password 密码
     * @param nickName 昵称
     * @return 新会员对象
     */
    public Member createMember(String username,String password,String nickName);

    /**
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆对象
     */
    public Member checkLogin(String username,String password);

    /**
     * 获取阅读状态
     * @param memberId 会员编号
     * @param bookId 图书编号
     * @return 阅读状态对象
     */
    public MemberReadState selectMemberReadState(Long memberId,Long bookId);

    /**
     * 更新阅读状态
     * @param memberId 会员编号
     * @param bookId 图书编号
     * @param readState 阅读状态
     * @return
     */
    public MemberReadState updateMemberReadState(Long memberId,Long bookId,Integer readState);

    /**
     * 发布新的短评
     * @param memberId 会员编号
     * @param bookId 图书编号
     * @param score 评分
     * @param content 短评内容
     * @return 短评对象
     */
    public Evaluation evaluate(Long memberId,Long bookId,Integer score,String content);

    /**
     * 短评点在
     * @param evaluationId 短评编号
     * @return 短评对象
     */
    public Evaluation enjoy(Long evaluationId);
}
