package com.richlife.topic.RichLife_Pharma.entities;

import com.richlife.topic.RichLife_Pharma.constants.TopicStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Integer create_by;
    private Date create_time;
    private Integer update_by;
    private Date update_time;
    private TopicStatus status;
}
