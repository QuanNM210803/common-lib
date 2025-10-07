package nmquan.commonlib.service;

import nmquan.commonlib.dto.SendEmailDto;
import nmquan.commonlib.enums.EmailTemplateEnum;
import nmquan.commonlib.utils.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SendEmailService {
    @Autowired
    private KafkaUtils kafkaUtils;

    @Value("${server.email-admin}")
    private String EMAIL_ADMIN;
    @Value("${kafka.topic.send-email}")
    private String TOPIC_SEND_EMAIL;

    public void sendEmailWithTemplate(List<String> recipients, List<String> ccList, EmailTemplateEnum emailTemplate, Map<String, String> templateVariables) {
        SendEmailDto sendEmailDto = SendEmailDto.builder()
                .sender(EMAIL_ADMIN)
                .recipients(recipients)
                .ccList(ccList)
                .subject(emailTemplate.getSubject())
                .template(emailTemplate)
                .templateVariables(templateVariables)
                .build();
        kafkaUtils.sendWithJson(TOPIC_SEND_EMAIL, sendEmailDto);
    }

    public void sendEmailWithBody(String sender, List<String> recipients, List<String> ccList, String subject, String body, Long orgId) {
        SendEmailDto sendEmailDto = SendEmailDto.builder()
                .sender(sender)
                .recipients(recipients)
                .ccList(ccList)
                .subject(subject)
                .body(body)
                .orgId(orgId)
                .build();
        kafkaUtils.sendWithJson(TOPIC_SEND_EMAIL, sendEmailDto);
    }

    public void sendEmailWithTemplate(SendEmailDto sendEmailDto) {
        kafkaUtils.sendWithJson(TOPIC_SEND_EMAIL, sendEmailDto);
    }

    public void sendEmailWithBody(SendEmailDto sendEmailDto) {
        kafkaUtils.sendWithJson(TOPIC_SEND_EMAIL, sendEmailDto);
    }
}
