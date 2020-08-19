package com.workflowassignment.dz.scheduler.tasks;

import com.workflowassignment.dz.config.TemplateConfig;
import com.workflowassignment.dz.model.Message;
import com.workflowassignment.dz.util.DateUtil;
import com.workflowassignment.dz.util.MessageUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashSet;
import java.util.Set;

import static com.workflowassignment.dz.util.Constants.DUMMY_DATA;
import static com.workflowassignment.dz.util.Constants.IS_ABORTTED;

@Service
public class AutoNotificationTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoNotificationTask.class);

    @Autowired
    private final SimpMessagingTemplate template;

    private Set<String> listeners = new HashSet<>();

    public AutoNotificationTask(SimpMessagingTemplate template) {
        this.template = template;
    }
    public void add(String sessionId) {
        this.remove();
        listeners.add(sessionId);
    }

    public void remove() {
        listeners.clear();
    }


    @EventListener
    public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        LOGGER.info("Disconnecting " + sessionId + "!");
        remove();
    }


    @Override
    public void run() {
        if (!IS_ABORTTED && TemplateConfig.getTemplate() != null && TemplateConfig.getTemplate().size() > 0 && !DUMMY_DATA.isEmpty()) {
            for (String listener : listeners) {
                LOGGER.info("Sending notification to " + listener);

                SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                headerAccessor.setSessionId(listener);
                headerAccessor.setLeaveMutable(true);

                //get template data and msg
                JSONObject jsonObject = MessageUtil.getData(DUMMY_DATA, "Template");
                String content = MessageUtil.getMessage(jsonObject);
                LOGGER.info(content);

                //prepare message and send to topic
                Message message = new Message();
                message.setSentTime(DateUtil.getCurrentTimeString());
                message.setContent(content);
                message.setType(Message.MessageType.CHAT);
                message.setSender("BOT");

                //push the notification to public topic
                if (content != null && content.length() > 0) {
                    template.convertAndSend("/topic/public", message, headerAccessor.getMessageHeaders());
                }

                //delay in push notification
                if (MessageUtil.getData() != null && MessageUtil.getData().size() > 0) {
                    JSONObject nextDummyData = (JSONObject) MessageUtil.getData().get(0);
                    Long startTime = MessageUtil.getTimeUsingKeyInMillis("Time", jsonObject);
                    Long endTime = MessageUtil.getTimeUsingKeyInMillis("Time", nextDummyData);

                    try {
                        Long delayInterval = endTime - startTime;
                        Thread.sleep(delayInterval);
                    } catch (InterruptedException e) {
                        LOGGER.error(e.getLocalizedMessage());
                    }
                }
            }
        }
    }
}
