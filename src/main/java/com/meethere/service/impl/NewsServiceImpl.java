package com.meethere.service.impl;

import com.meethere.dao.NewsDao;
import com.meethere.entity.News;
import com.meethere.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class NewsServiceImpl implements NewService {
    @Autowired
    private NewsDao newsDao;

    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsDao.findAll(pageable);
    }

    @Override
    public News findById(int newsID) {
        return newsDao.getOne(newsID);
    }

    @Override
    public int create(News news) {
        return newsDao.save(news).getNewsID();
    }

    @Override
    public void delById(int newsID) {
        newsDao.deleteById(newsID);
    }
}
