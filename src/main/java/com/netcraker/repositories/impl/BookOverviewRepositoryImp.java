package com.netcraker.repositories.impl;

import com.netcraker.model.Book;
import com.netcraker.model.BookOverview;
import com.netcraker.model.User;
import com.netcraker.model.mapper.BookOverviewRowMapper;
import com.netcraker.repositories.BookOverviewRepository;
import com.netcraker.repositories.BookRepository;
import com.netcraker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@PropertySource("classpath:sqlQueries.properties")
@RequiredArgsConstructor
public class BookOverviewRepositoryImp implements BookOverviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(BookOverviewRepositoryImp.class);
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Value("${book_overviews.getById}")
    private String sqlGetById;
    @Value("${book_overviews.getAll}")
    private String sqlGetOverviews;
    @Value("${book_overviews.countByBook}")
    private String sqlCountByBook;
    @Value("${book_overviews.getByBook}")
    private String sqlGetByBook;
    @Value("${book_overviews.getPublishedByBook}")
    private String sqlGetPublishedByBook;
    @Value("${book_overviews.insert}")
    private String sqlInsert;
    @Value("${book_overviews.update}")
    private String sqlUpdate;
    @Value("${book_overviews.delete]")
    private String sqlDelete;
    @Value("${book_overviews.publish}")
    private String sqlPublish;
    @Value("${book_overviews.unpublish}")
    private String sqlUnpublish;
    @Value("${book_overviews.count}")
    private String sqlGetBookOverviewsCount;

    @Override
    public void loadReferences(BookOverview bookOverview) {
        Optional<User> optionalUser = userRepository.getById(bookOverview.getUserId());
        Optional<Book> optionalBook = bookRepository.getById(bookOverview.getBookId());
        if(optionalBook.isPresent() && optionalUser.isPresent()){
            bookOverview.setBook(optionalBook.get());
            bookOverview.setUser(optionalUser.get());
        }
    }

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject(sqlGetBookOverviewsCount, int.class);
    }

    @Override
    public int countByBook(int bookId) {
        return jdbcTemplate.queryForObject(sqlCountByBook, new Object[]{bookId}, int.class);
    }

    @Override
    public List<BookOverview> getByBook(int bookId, int size, int offset) {
        return jdbcTemplate.query(sqlGetByBook, new BookOverviewRowMapper(), bookId, size, offset);
    }

    @Override
    public List<BookOverview> getBookOverviews(int limit, int offset) {
        return jdbcTemplate.query(sqlGetOverviews, new Object[]{limit, offset}, new BookOverviewRowMapper());
    }

    @Override
    public Optional<BookOverview> getPublishedByBook(int bookId) {
        List<BookOverview> bookOverviews = jdbcTemplate.query(sqlGetPublishedByBook,
                new BookOverviewRowMapper(), bookId);
        return bookOverviews.isEmpty() ? Optional.empty() : Optional.of(bookOverviews.get(0));
    }

    @Override
    public Optional<BookOverview> getById(int id) {
        List<BookOverview> bookOverviews = jdbcTemplate.query(sqlGetById,
                new BookOverviewRowMapper(), id);
        return bookOverviews.isEmpty() ? Optional.empty() : Optional.of(bookOverviews.get(0));
    }


    @Override
    public Optional<BookOverview> insert(BookOverview entity) {
        KeyHolder keyHolder;
        try {
            keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getDescription());
                ps.setInt(2, entity.getUserId());
                ps.setInt(3, entity.getBookId());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            logger.info("Book Overview::insert entity: " + entity + ". Stack trace: ");
            e.printStackTrace();
            return Optional.empty();
        }
        return getById((Integer) keyHolder.getKeys().get("book_overview_id"));
    }

    @Override
    public Optional<BookOverview> update(BookOverview entity) {
        try {
            jdbcTemplate.execute(Objects.requireNonNull(sqlUpdate), (PreparedStatementCallback<Boolean>) ps -> {
                ps.setString(1, entity.getDescription());
                ps.setInt(2, entity.getUserId());
                ps.setInt(3, entity.getBookId());
                ps.setInt(4, entity.getBookOverviewId());
                return ps.execute();
            });
            return getById(entity.getBookOverviewId());
        } catch (DataAccessException e) {
            logger.info("Book Overview::update entity: " + entity + ". Stack trace: ");
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            return jdbcTemplate.update(sqlDelete, id) == 1;
        }catch (DataAccessException e){
            logger.info("BookOverview::delete entityId: " + id + ". Stack trace: ");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void publish(int id) {
        Object[] params = {id};
        jdbcTemplate.update(sqlPublish, params);

    }

    @Override
    public void unpublish(int id){
        Object[] params = {id};
        jdbcTemplate.update(sqlUnpublish, params);

    }
}