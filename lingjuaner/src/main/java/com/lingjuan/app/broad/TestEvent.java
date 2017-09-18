package com.lingjuan.app.broad;

/**
 * Created by sks on 2017/8/27.
 */

public class TestEvent {
    private String nickname;
    private String figureurl_qq;
    public TestEvent(String nickname,String figureurl_qq) {
        this.nickname = nickname;
        this.figureurl_qq = figureurl_qq;
    }
    public String getNickname(){
        return nickname;
    }

    public String getFigureurl_qq(){
        return figureurl_qq;
    }
}