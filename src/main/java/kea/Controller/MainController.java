package kea.Controller;

import kea.Model.Booking;
import kea.Model.User;
import kea.Service.BookingService;
import kea.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;

    @GetMapping
    public String home(Model model) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("template", "home");
        data.put("page", "Home");
        model.addAttribute("data", data);
        return "layout";
    }

    @GetMapping("bookings")
    public String bookings(Model model, @ModelAttribute("messageType") String messageType,
                           @ModelAttribute("message") String message,
                           @ModelAttribute("userID") String userID, Authentication auth) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("messageType", Objects.requireNonNullElse(messageType, ""));
        data.put("message", Objects.requireNonNullElse(message, ""));
        data.put("template", "calendar");
        data.put("page", "Bookings");
        if(!userID.isEmpty()) {
            data.put("events", bookingService.findByUserID(Long.parseLong(userID)));
            data.put("controlUser", userService.getFullNameById(Long.parseLong(userID)));
            data.put("userID", userID);
        } else {
            if(auth.getAuthorities().toArray()[0].toString().equals("ROLE_ADMIN") ||
                auth.getAuthorities().toArray()[0].toString().equals("ROLE_SECRETARY"))
                data.put("events", bookingService.findAll());
            else
                data.put("events", bookingService.findByUserID(userService.getIdByName(auth.getName())));
        }
        model.addAttribute("data", data);
        return "layout";
    }

    @GetMapping("bookings/{id}")
    public String manageBookings(@PathVariable("id") Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("userID", id);
        return "redirect:/bookings";
    }

    @GetMapping("users")
    public String users(Model model, @ModelAttribute("search") String search) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("template", "users");
        data.put("page", "Users");
        Iterable<User> users = userService.findByName(search);
        data.put("users", users);
        data.put("userCount", StreamSupport.stream(users.spliterator(), false).count());
        data.put("search", search);
        model.addAttribute("data", data);
        return "layout";
    }

    @PostMapping("users")
    public String searchUsers(@RequestParam(value = "search", required = false) String search, RedirectAttributes ra) {
        ra.addFlashAttribute("search", search);
        return "redirect:/users";
    }

    @PostMapping("createbookings")
    public String createbooking(@ModelAttribute Booking booking, Authentication auth, RedirectAttributes ra) {
        if(booking.getUserID() != null) {
            booking.setTitle(userService.getFullNameById(booking.getUserID())+": COVID-19 Test");
            ra.addFlashAttribute("userID", booking.getUserID());
        } else {
            booking.setUserID(userService.getIdByName(auth.getName()));
            booking.setTitle(userService.getFullNameByUsername(auth.getName())+": COVID-19 Test");
        }
        try {
            bookingService.save(booking);
            ra.addFlashAttribute("messageType", "success");
            ra.addFlashAttribute("message", "COVID-19 Test Booked successfully.");
        } catch(Exception e) {
            ra.addFlashAttribute("messageType", "error");
            ra.addFlashAttribute("message", "Failed to book COVID-19 Test.");
        }
        return "redirect:/bookings";
    }

    @PostMapping("updatebookings")
    public String updatebooking(@ModelAttribute Booking booking, RedirectAttributes ra) {
        if(booking.getUserID() != null)
            ra.addFlashAttribute("userID", booking.getUserID());
        try {
            bookingService.save(booking);
            ra.addFlashAttribute("messageType", "success");
            ra.addFlashAttribute("message", "COVID-19 Test updated successfully.");
        } catch(Exception e) {
            ra.addFlashAttribute("messageType", "error");
            ra.addFlashAttribute("message", "Failed to update COVID-19 Test.");
        }
        return "redirect:/bookings";
    }

    @GetMapping("deletebookings/{id}")
    public String deletebooking(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            bookingService.deleteById(id);
            ra.addFlashAttribute("messageType", "success");
            ra.addFlashAttribute("message", "COVID-19 Test deleted successfully.");
        } catch(Exception e) {
            ra.addFlashAttribute("messageType", "error");
            ra.addFlashAttribute("message", "Failed to delete COVID-19 Test.");
        }
        return "redirect:/bookings";
    }

    @GetMapping("deletebookings/{userID}/{id}")
    public String deletebooking(@PathVariable("id") Long id, @PathVariable("userID") Long userID, RedirectAttributes ra) {
        try {
            bookingService.deleteById(id);
            ra.addFlashAttribute("messageType", "success");
            ra.addFlashAttribute("message", "COVID-19 Test deleted successfully.");
        } catch(Exception e) {
            ra.addFlashAttribute("messageType", "error");
            ra.addFlashAttribute("message", "Failed to delete COVID-19 Test.");
        }
        ra.addFlashAttribute("userID", userID);
        return "redirect:/bookings";
    }
}
