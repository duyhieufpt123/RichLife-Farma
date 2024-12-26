package com.richlife.topic.RichLife_Pharma.controller;

import com.richlife.topic.RichLife_Pharma.constants.ResponseCode;
import com.richlife.topic.RichLife_Pharma.dto.request.CreateTopicRequest;
import com.richlife.topic.RichLife_Pharma.dto.response.CreateTopicResponse;
import com.richlife.topic.RichLife_Pharma.dto.response.ResponseData;
import com.richlife.topic.RichLife_Pharma.service.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topic")
@AllArgsConstructor
@CrossOrigin
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<ResponseData<CreateTopicResponse>> createTopic(@RequestBody @Valid CreateTopicRequest request) {
        if (request == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData<>(ResponseCode.C205.getCode(), "Bài viết chứa thông tin bị trống."));
        }
        ResponseData<CreateTopicResponse> responseData = topicService.createTopic(request);
            if (responseData.getStatus() == ResponseCode.C200.getCode()) {
                return ResponseEntity.status(HttpStatus.OK).body(responseData);
            } else if (responseData.getStatus() == ResponseCode.C205.getCode()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }
}
