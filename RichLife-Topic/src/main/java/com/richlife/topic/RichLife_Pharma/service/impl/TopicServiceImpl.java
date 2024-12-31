package com.richlife.topic.RichLife_Pharma.service.impl;

import com.richlife.topic.RichLife_Pharma.constants.ResponseCode;
import com.richlife.topic.RichLife_Pharma.constants.TopicStatus;
import com.richlife.topic.RichLife_Pharma.dto.request.CreateTopicRequest;
import com.richlife.topic.RichLife_Pharma.dto.response.CreateTopicResponse;
import com.richlife.topic.RichLife_Pharma.dto.response.ResponseData;
import com.richlife.topic.RichLife_Pharma.entities.Topic;
import com.richlife.topic.RichLife_Pharma.repository.TopicRepository;
import com.richlife.topic.RichLife_Pharma.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    @Override
    @Transactional
    public ResponseData<CreateTopicResponse> createTopic(CreateTopicRequest request) {
        try {
            Topic existTopic = topicRepository.findByTitle(request.getTitle());

            if (existTopic != null) {
                throw new RuntimeException("Topic đã tồn tại");
            }
            Topic topic = new Topic();
            topic.setTitle(request.getTitle());
            topic.setDescription(request.getDescription());
            topic.setCreate_by(1);
            topic.setCreate_time(new Date());
            topic.setStatus(TopicStatus.ACTIVE);
            topicRepository.save(topic);

            CreateTopicResponse response = CreateTopicResponse
                    .builder()
                    .id(topic.getId())
                    .title(topic.getTitle())
                    .description(topic.getDescription())
                    .createBy(topic.getCreate_by())
                    .createTime(topic.getCreate_time())
                    .status(topic.getStatus().name)
                    .build();

            return new ResponseData<>(ResponseCode.C200.getCode(), "Tạo topic thành công", response);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }
        catch (Exception e) {
            return new ResponseData<>(ResponseCode.C204.getCode(), "Có lỗi xảy ra trong quá trình tạo topic");
        }
    }
}
