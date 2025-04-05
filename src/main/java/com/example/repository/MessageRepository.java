package com.example.repository;

import com.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    public List<Message> findAllByPostedBy(Integer accountId);
}
