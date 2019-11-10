package com.netcraker.services.impl;

import com.netcraker.model.Announcement;
import com.netcraker.model.Page;
import com.netcraker.repositories.AnnouncementRepository;
import com.netcraker.services.AnnouncementService;
import com.netcraker.services.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;

@Service
@PropertySource({"classpath:view.properties"})
public class AnnouncementServiceImp implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final PageService pageService;

    @Autowired
    public AnnouncementServiceImp(AnnouncementRepository announcementRepository, PageService pageService) {
        Assert.notNull(announcementRepository, "AnnouncementRepository shouldn't be null");
        Assert.notNull(pageService, "PageService shouldn't be null");
        this.announcementRepository = announcementRepository;
        this.pageService = pageService;
    }

    @Value("${announcements.pageSize}")
    private int pageSize;

    @Override
    public Page<Announcement> getAnnouncements(int page) {
        int total = announcementRepository.getCount();
        int pagesCount = pageService.getPagesCount(total, pageSize);
        int currentPage = pageService.getRestrictedPage(page, pagesCount);
        int offset = (currentPage - 1) * pageSize;
        ArrayList<Announcement> list = (ArrayList<Announcement>) announcementRepository.getAnnouncements(pageSize,offset);
        return new Page<>(currentPage, pagesCount, list);
    }

    @Override
    public Announcement getAnnouncementById() {
        return null;
    }

    @Override
    public boolean addAnnouncement(Announcement announcement) {
        return false;
    }

    @Override
    public boolean updateAnnouncement(Announcement announcement) {
        return false;
    }

    @Override
    public boolean deleteAnnouncement(int id) {
        return false;
    }

    @Override
    public Page<Announcement> getPaginations(int page, int count) {
        return null;
    }
}
