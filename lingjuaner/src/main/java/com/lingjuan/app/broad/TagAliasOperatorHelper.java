package com.lingjuan.app.broad;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.lingjuan.app.uitls.ExampleUtil;
import com.lingjuan.app.uitls.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;

/**
 * 处理tagalias相关的逻辑
 * */
public class TagAliasOperatorHelper {
    private static final String TAG = "JIGUANG-TagAliasHelper";
    public static int sequence = 1;
    /**增加*/
    public static final int ACTION_ADD = 1;
    /**覆盖*/
    public static final int ACTION_SET = 2;
    /**删除部分*/
    public static final int ACTION_DELETE = 3;
    /**删除所有*/
    public static final int ACTION_CLEAN = 4;
    /**查询*/
    public static final int ACTION_GET = 5;

    public static final int ACTION_CHECK = 6;

    public static final int DELAY_SEND_ACTION = 1;
    private Context context;

    private static TagAliasOperatorHelper mInstance;
    private TagAliasOperatorHelper(){
    }
    public static TagAliasOperatorHelper getInstance(){
        if(mInstance == null){
            synchronized (TagAliasOperatorHelper.class){
                if(mInstance == null){
                    mInstance = new TagAliasOperatorHelper();
                }
            }
        }
        return mInstance;
    }
    public void init(Context context){
        if(context!=null && context != null) {
            this.context = context.getApplicationContext();
        }
    }
    private HashMap<Integer,TagAliasBean> tagAliasActionCache = new HashMap<Integer,TagAliasBean>();

    public TagAliasBean get(int sequence){
        return tagAliasActionCache.get(sequence);
    }
    public TagAliasBean remove(int sequence){
        return tagAliasActionCache.get(sequence);
    }
    public void put(int sequence,TagAliasBean tagAliasBean){
        tagAliasActionCache.put(sequence,tagAliasBean);
    }
    private Handler delaySendHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DELAY_SEND_ACTION:
                    if(msg.obj !=null && msg.obj instanceof  TagAliasBean){
                        Logger.i(TAG,"on delay time");
                        sequence++;
                        TagAliasBean tagAliasBean = (TagAliasBean) msg.obj;
                        tagAliasActionCache.put(sequence, tagAliasBean);
                        if(context!=null) {
                            handleAction(context, sequence, tagAliasBean);
                        }else{
                            Logger.e(TAG,"#unexcepted - context was null");
                        }
                    }else{
                        Logger.w(TAG,"#unexcepted - msg obj was incorrect");
                    }
                    break;
            }
        }
    };
    /**
     * 处理设置tag
     * */
    public void handleAction(Context context,int sequence, TagAliasBean tagAliasBean){
        init(context);
        if(tagAliasBean == null){
            Logger.w(TAG,"tagAliasBean was null");
            return;
        }
        put(sequence,tagAliasBean);
        if(tagAliasBean.isAliasAction){
            switch (tagAliasBean.action){
                case ACTION_GET:
                    JPushInterface.getAlias(context,sequence);
                    break;
                case ACTION_DELETE:
                    JPushInterface.deleteAlias(context,sequence);
                    break;
                case ACTION_SET:
                    JPushInterface.setAlias(context,sequence,tagAliasBean.alias);
                    break;
                default:
                    Logger.w(TAG,"unsupport alias action type");
                    return;
            }
        }else {
            switch (tagAliasBean.action) {
                case ACTION_ADD:
                    JPushInterface.addTags(context, sequence, tagAliasBean.tags);
                    break;
                case ACTION_SET:
                    JPushInterface.setTags(context, sequence, tagAliasBean.tags);
                    break;
                case ACTION_DELETE:
                    JPushInterface.deleteTags(context, sequence, tagAliasBean.tags);
                    break;
                case ACTION_CHECK:
                    //一次只能check一个tag
                    String tag = (String)tagAliasBean.tags.toArray()[0];
                    JPushInterface.checkTagBindState(context,sequence,tag);
                    break;
                case ACTION_GET:
                    JPushInterface.getAllTags(context, sequence);
                    break;
                case ACTION_CLEAN:
                    JPushInterface.cleanTags(context, sequence);
                    break;
                default:
                    Logger.w(TAG,"unsupport tag action type");
                    return;
            }
        }
    }
    private boolean RetryActionIfNeeded(int errorCode,TagAliasBean tagAliasBean){
        if(!ExampleUtil.isConnected(context)){
            Logger.w(TAG,"no network");
            return false;
        }
        //返回的错误码为6002 超时,6014 服务器繁忙,都建议延迟重试
        if(errorCode == 6002 || errorCode == 6014){
            Logger.d(TAG,"need retry");
            if(tagAliasBean!=null){
                Message message = new Message();
                message.what = DELAY_SEND_ACTION;
                message.obj = tagAliasBean;
                delaySendHandler.sendMessageDelayed(message,1000*60);
                String logs =getRetryStr(tagAliasBean!=null?tagAliasBean.isAliasAction:false,tagAliasBean!=null?tagAliasBean.action:-1,errorCode);
                ExampleUtil.showToast(logs, context);
                return true;
            }
        }
        return false;
    }

    private String getRetryStr(boolean isAliasAction,int actionType,int errorCode){
        String str = "Failed to %s %s due to %s. Try again after 60s.";
        str = String.format(Locale.ENGLISH,str,getActionStr(actionType),(isAliasAction? "alias" : " tags") ,(errorCode == 6002 ? "timeout" : "server too busy"));
        return str;
    }

    private String getActionStr(int actionType){
        switch (actionType){
            case ACTION_ADD:
                return "add";
            case ACTION_SET:
                return "set";
            case ACTION_DELETE:
                return "delete";
            case ACTION_GET:
                return "get";
            case ACTION_CLEAN:
                return "clean";
            case ACTION_CHECK:
                return "check";
        }
        return "unkonw operation";
    }
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        Logger.i(TAG,"action - onTagOperatorResult, sequence:"+sequence+",tags:"+jPushMessage.getTags());
        Logger.i(TAG,"tags size:"+jPushMessage.getTags().size());
        init(context);
        //根据sequence从之前操作缓存中获取缓存记录
        TagAliasBean tagAliasBean = tagAliasActionCache.get(sequence);
        if(tagAliasBean == null){
            ExampleUtil.showToast("获取缓存记录失败", context);
            return;
        }
        if(jPushMessage.getErrorCode() == 0){
            Logger.i(TAG,"action - modify tag Success,sequence:"+sequence);
            tagAliasActionCache.remove(sequence);
            String logs = getActionStr(tagAliasBean.action)+" tags success";
            Logger.i(TAG,logs);
            ExampleUtil.showToast(logs, context);
        }else{
            String logs = "Failed to " + getActionStr(tagAliasBean.action)+" tags";
            if(jPushMessage.getErrorCode() == 6018){
                //tag数量超过限制,需要先清除一部分再add
                logs += ", tags is exceed limit need to clean";
            }
            logs += ", errorCode:" + jPushMessage.getErrorCode();
            Logger.e(TAG, logs);
            if(!RetryActionIfNeeded(jPushMessage.getErrorCode(),tagAliasBean)) {
                ExampleUtil.showToast(logs, context);
            }
        }
    }
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage){
        int sequence = jPushMessage.getSequence();
        Logger.i(TAG,"action - onCheckTagOperatorResult, sequence:"+sequence+",checktag:"+jPushMessage.getCheckTag());
        init(context);
        //根据sequence从之前操作缓存中获取缓存记录
        TagAliasBean tagAliasBean = tagAliasActionCache.get(sequence);
        if(tagAliasBean == null){
            ExampleUtil.showToast("获取缓存记录失败", context);
            return;
        }
        if(jPushMessage.getErrorCode() == 0){
            Logger.i(TAG,"tagBean:"+tagAliasBean);
            tagAliasActionCache.remove(sequence);
            String logs = getActionStr(tagAliasBean.action)+" tag "+jPushMessage.getCheckTag() + " bind state success,state:"+jPushMessage.getTagCheckStateResult();
            Logger.i(TAG,logs);
            ExampleUtil.showToast(logs, context);
        }else{
            String logs = "Failed to " + getActionStr(tagAliasBean.action)+" tags, errorCode:" + jPushMessage.getErrorCode();
            Logger.e(TAG, logs);
            if(!RetryActionIfNeeded(jPushMessage.getErrorCode(),tagAliasBean)) {
                ExampleUtil.showToast(logs, context);
            }
        }
    }
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        Logger.i(TAG,"action - onAliasOperatorResult, sequence:"+sequence+",alias:"+jPushMessage.getAlias());
        init(context);
        //根据sequence从之前操作缓存中获取缓存记录
        TagAliasBean tagAliasBean = tagAliasActionCache.get(sequence);
        if(tagAliasBean == null){
            ExampleUtil.showToast("获取缓存记录失败", context);
            return;
        }
        if(jPushMessage.getErrorCode() == 0){
            Logger.i(TAG,"action - modify alias Success,sequence:"+sequence);
            tagAliasActionCache.remove(sequence);
            String logs = getActionStr(tagAliasBean.action)+" alias success";
            Logger.i(TAG,logs);
            ExampleUtil.showToast(logs, context);
        }else{
            String logs = "Failed to " + getActionStr(tagAliasBean.action)+" alias, errorCode:" + jPushMessage.getErrorCode();
            Logger.e(TAG, logs);
            if(!RetryActionIfNeeded(jPushMessage.getErrorCode(),tagAliasBean)) {
                ExampleUtil.showToast(logs, context);
            }
        }
    }

    public static class TagAliasBean{
        int action;
        Set<String> tags;
        String alias;
        boolean isAliasAction;

        @Override
        public String toString() {
            return "TagAliasBean{" +
                    "action=" + action +
                    ", tags=" + tags +
                    ", alias='" + alias + '\'' +
                    ", isAliasAction=" + isAliasAction +
                    '}';
        }
    }


}
