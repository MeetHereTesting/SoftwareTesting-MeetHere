package com.meethere.IntegrationTest.Service;

import com.meethere.MeetHereApplication;
import com.meethere.dao.NewsDao;
import com.meethere.entity.News;
import com.meethere.service.impl.NewsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@Transactional
public class NewsServiceTest {
    @Autowired
    private NewsServiceImpl newsService;

    @Test
    public void return_all_news_paged_by_page() {
        Pageable pageable= PageRequest.of(0,10);
        newsService.findAll(pageable);
    }

    @Test
    void return_news_by_newsID() {
        int id=4;
        News res= newsService.findById(id);
        assertEquals(id,res.getNewsID());

    }

    @Test
    void create_news() {
        int id=1;
        String title="title";
        String content="this is content";
        LocalDateTime ldt=LocalDateTime.now();
        News news=new News(id,title,content,ldt);
        int res = newsService.create(news);

    }

    @Test
    void del_news_by_newsID() {
        newsService.delById(1);
        newsService.delById(2);
    }

    @Test
    void update_news() {
        int id = 1;
        String title = "title";
        String content = "this is content";
        LocalDateTime ldt = LocalDateTime.now();
        News news = new News(id, title, content, ldt);
        newsService.update(news);
    }
}
