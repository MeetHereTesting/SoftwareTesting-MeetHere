package com.meethere.dao;

import com.meethere.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDao extends JpaRepository<News,Integer> {

}
