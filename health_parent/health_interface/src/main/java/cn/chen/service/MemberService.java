package cn.chen.service;

import cn.chen.pojo.Member;

/**
 * @author haixin
 * @time 2019-10-14
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member1);
}
