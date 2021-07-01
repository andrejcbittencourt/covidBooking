package kea.Service;

import kea.Model.Booking;
import kea.Repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookingService {
    @Autowired
    BookingRepo bookingRepo;

    public Iterable<Booking> findAll() {
        return bookingRepo.findAll();
    }

    public Booking save(Booking booking) {
        return bookingRepo.save(booking);
    }

    public void deleteById(Long id) { bookingRepo.deleteById(id); }

    public Iterable<Booking> findByUserID(Long userID) {
        Iterable<Booking> allBookings = bookingRepo.findAll();
        ArrayList<Booking> result = new ArrayList<>();
        for(Booking booking : allBookings) {
            if(booking.getUserID().equals(userID))
                result.add(booking);
        }
        return result;
    }
}
