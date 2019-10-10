package cn.chen.jobs;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author haixin
 * @time 2019-10-07
 */
public class JobDemo {
    public void run(){
        System.out.println("run");
    }

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");
    }
}
