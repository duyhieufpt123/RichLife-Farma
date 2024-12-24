package com.richlife.topic.RichLife_Pharma.entities;

import com.richlife.topic.RichLife_Pharma.constants.TopicStatus;
import jakarta.persistence.*;
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
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "create_by")
    private Integer create_by;
    @Column(name = "create_time")
    private Date create_time;
    @Column(name = "update_by")
    private Integer update_by;
    @Column(name = "update_time")
    private Date update_time;
    @Column(name = "status")
    private TopicStatus status;
}
