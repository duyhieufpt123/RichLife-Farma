package com.richlife.topic.RichLife_Pharma.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Builder
public class CreateTopicRequest {
    private String title;
    private String description;
}
