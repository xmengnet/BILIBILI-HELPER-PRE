package top.misec.config;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import top.misec.push.Push;
import top.misec.push.impl.*;
import top.misec.push.model.PushMetaInfo;

/**
 * @author JunzhouLiu
 */
@SuppressWarnings("all")
@Data
public class PushConfig {

    /**
     * 电报
     */
    private String TG_BOT_TOKEN;

    /**
     * 电报
     */
    private String TG_USER_ID;

    /**
     * 钉钉
     */
    private String DING_TALK_URL;

    /**
     * push plus++
     */
    private String PUSH_PLUS_TOKEN;

    /**
     * Server酱
     */
    private String SERVER_CHAN_TURBO_SCT_KEY;

    /**
     * 企业微信
     */
    private String WE_COM_TOKEN;

    public PushInfo getPushInfo() {
        if (StringUtils.isNoneBlank(TG_BOT_TOKEN, TG_USER_ID)) {
            return new PushInfo(new TelegramPush(), TG_BOT_TOKEN, TG_USER_ID);
        } else if (StringUtils.isNotBlank(DING_TALK_URL)) {
            return new PushInfo(new DingTalkPush(), DING_TALK_URL);
        } else if (StringUtils.isNotBlank(PUSH_PLUS_TOKEN)) {
            return new PushInfo(new PushPlusPush(), PUSH_PLUS_TOKEN);
        } else if (StringUtils.isNotBlank(SERVER_CHAN_TURBO_SCT_KEY)) {
            return new PushInfo(new ServerChanTurboPush(), SERVER_CHAN_TURBO_SCT_KEY);
        } else if (StringUtils.isNotBlank(WE_COM_TOKEN)) {
            return new PushInfo(new WeComPush(), WE_COM_TOKEN);
        } else {
            return null;
        }
    }

    @Getter
    public static class PushInfo {
        private final Push target;
        private final PushMetaInfo metaInfo;

        public PushInfo(Push target, String token) {
            this.target = target;
            this.metaInfo = PushMetaInfo.builder().numberOfRetries(3).token(token).build();
        }

        public PushInfo(Push target, String token, String chatId) {
            this.target = target;
            this.metaInfo = PushMetaInfo.builder().numberOfRetries(3).token(token).chatId(chatId).build();
        }
    }
}
