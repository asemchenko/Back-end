package com.netcraker.repositories;

import com.netcraker.model.Announcement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends BaseOptionalRepository<Announcement> {
    List<Announcement> getAll();
    List<Announcement> getAnnouncements(int limit, int offset);
    int getCount();
    Announcement getAnnouncementByTitle();
}
