package com.richlife.topic.RichLife_Pharma.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTopicResponse {
    private Integer id;
    private String title;
    private String description;
    private Integer createBy;
    private Date createTime;
    private Integer updateBy;
    private Date updateTime;
    private String status;
}
