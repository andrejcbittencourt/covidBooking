package kea.Repository;

import kea.Model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface IBookingRepo extends CrudRepository<Booking, Long> {
}
