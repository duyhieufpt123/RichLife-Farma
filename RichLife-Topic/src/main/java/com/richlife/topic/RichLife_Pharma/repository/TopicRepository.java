package com.richlife.topic.RichLife_Pharma.repository;

import com.richlife.topic.RichLife_Pharma.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query("SELECT t FROM Topic t WHERE t.title LIKE %:title%")
    Topic findByTitle(String title);
}
