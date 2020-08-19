package com.workflowassignment.dz.Service;

import com.workflowassignment.dz.config.TemplateConfig;
import com.workflowassignment.dz.util.DummyData;
import com.workflowassignment.dz.model.Message;
import com.workflowassignment.dz.scheduler.JobScheduler;
import com.workflowassignment.dz.scheduler.tasks.AutoNotificationTask;
import com.workflowassignment.dz.util.DateUtil;
import com.workflowassignment.dz.util.MessageUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

import static com.workflowassignment.dz.util.Constants.IS_ABORTTED;

@Service
public class MessageService {


    @Autowired
    private AutoNotificationTask autoNotificationTask;

    @Autowired
    private JobScheduler jobScheduler;

    public void startAutoNotification () throws Exception {

        TemplateConfig.applyArgs();
        IS_ABORTTED = false;

        //get the template 1 every time on page refresh
        JSONObject jsonObject = MessageUtil.getData(DummyData.getDummyData(), "Template");

        if (jsonObject != null) {
            Long delayTime = MessageUtil.getTimeUsingKeyInMillis("Time", jsonObject);

            if (delayTime == 0) {
                delayTime = 1000L;
            }

            //first template delay time
            Date initialDelay = Date.from(Instant.now().plusMillis(delayTime));
            jobScheduler.start(autoNotificationTask, initialDelay, 1L);
        }
    }

    public Message sendMessageAndStopAutoNotification (Message message) {
        IS_ABORTTED = true;
        autoNotificationTask.remove();
        message.setSender("John");
        message.setSentTime(DateUtil.getCurrentTimeString());
        message.setType(Message.MessageType.CHAT);
        return message;
    }

    public Message addUser(Message message,
                           SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        message.setType(Message.MessageType.JOIN);
        autoNotificationTask.add(headerAccessor.getSessionId());
        return message;
    }
}
