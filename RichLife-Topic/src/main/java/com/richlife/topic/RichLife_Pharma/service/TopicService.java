package com.richlife.topic.RichLife_Pharma.service;

import com.richlife.topic.RichLife_Pharma.dto.request.CreateTopicRequest;
import com.richlife.topic.RichLife_Pharma.dto.response.CreateTopicResponse;
import com.richlife.topic.RichLife_Pharma.dto.response.ResponseData;

public interface TopicService {
    public ResponseData<CreateTopicResponse> createTopic(CreateTopicRequest request);
}
