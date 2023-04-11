package com.erphotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erphotel.Booking.domain.Book;
import com.erphotel.Booking.domain.Guest;
import com.erphotel.Booking.service.BookService;
import com.erphotel.Booking.service.GuestService;
import com.erphotel.personManagement.domain.PersonDomain;
import com.erphotel.personManagement.service.PersonService;
import com.erphotel.rooms.domain.Room;
import com.erphotel.rooms.services.RoomService;

@Controller
public class BookingController {

    @Autowired
    PersonService personService;

    @Autowired
    BookService bookService;

    @Autowired
    GuestService guestService;

    @Autowired
    RoomService roomService;

    @GetMapping("/hotel_booking")
    public String bookingWidget(Model model) {
        List<PersonDomain> personas = personService.listPersonas();
        model.addAttribute("personas", personas);
        return "hotel_booking";
    }

    @GetMapping("/book_confirm")
    public void bookingWidget() {
    }

    @PostMapping("/processFormBooking")
    public String create(@ModelAttribute("persona") PersonDomain persona, @ModelAttribute("reserva") Book book, @ModelAttribute("huesped") Guest guest,
            @RequestParam("room_type") String roomType, @RequestParam("nonselected") String existentGuest) {
        List<Room> rooms = roomService.roomList();
        while (book.getRoom_id() == null) {
            for (Room room : rooms) {
                if (room.getRoom_type().equals(roomType)) {
                    if (room.isStatus() != false) {
                        book.setRoom_id((int) room.getRoom_id());
                        room.setStatus(false);
                        roomService.save(room);
                        break;
                    }
                }
            }
        }

        if (!existentGuest.equals("blank")) {
            bookService.save(book);
            guest.setPerson_id(persona.getPerson_id().intValue());;
            guestService.save(guest);
            return "book_confirm";
        } else {
            personService.salvar(persona);
            bookService.save(book);
            System.out.println(guest + guest.getBoard());
            guest.setPerson_id(persona.getPerson_id().intValue());
            guestService.save(guest);

            return "book_confirm";
        }
    }
}
