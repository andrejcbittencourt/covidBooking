package kea.Repository;

import kea.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class BookingRepo implements IBookingRepo {
    @Autowired
    JdbcTemplate template;

    @Override
    public <S extends Booking> S save(S s) {
        StringBuilder sql = new StringBuilder();
        if(s.getBookingID() == null) {
            sql.append("INSERT INTO booking(UserID, `Date`, `Time`, Title) VALUES(?, ?, ?, ?)");
            template.update(sql.toString(), s.getUserID(), s.getDate(), s.getTime(), s.getTitle());
        } else {
            ArrayList<Object> params = new ArrayList<Object>();
            sql.append("UPDATE booking SET ");
            int count = 1;
            if(s.getDate() != null) {
                sql.append("`Date`=?");
                params.add(s.getDate());
                count++;
            }
            if(s.getTime() != null) {
                sql.append((count>1)?",":"").append("`Time`=?");
                params.add(s.getTime());
                count++;
            }
            if(s.getTitle() != null) {
                sql.append((count>1)?",":"").append("`Title`=?");
                params.add(s.getTitle());
            }
            params.add(s.getBookingID());
            sql.append(" WHERE BookingID=?");
            template.update(sql.toString(),params.toArray());
        }
        return null;
    }

    @Override
    public <S extends Booking> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        String sql = "SELECT * FROM booking WHERE BookingID = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return Optional.ofNullable(template.queryForObject(sql, rowMapper, id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Booking> findAll() {
        String sql = "SELECT * FROM booking";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql,rowMapper);
    }

    @Override
    public Iterable<Booking> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM booking WHERE BookingID = ?";
        template.update(sql, id);
    }

    @Override
    public void delete(Booking booking) {
        String sql = "DELETE FROM booking WHERE BookingID = ?";
        template.update(sql, booking.getBookingID());
    }

    @Override
    public void deleteAll(Iterable<? extends Booking> iterable) { }

    @Override
    public void deleteAll() { }
}
