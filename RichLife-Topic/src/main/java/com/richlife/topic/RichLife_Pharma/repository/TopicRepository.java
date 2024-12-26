package com.richlife.topic.RichLife_Pharma.repository;

import com.richlife.topic.RichLife_Pharma.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
