package com.meethere.service;

import com.meethere.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable
        ;
import java.util.List;

public interface NewService {
    Page<News> findAll(Pageable pageable);

    News findById(int newsID);

    int create(News news);

    void delById(int newsID);
}
