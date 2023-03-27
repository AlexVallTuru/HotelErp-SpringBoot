/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.erphotel.rooms;

import com.erphotel.rooms.domain.Room;
import com.erphotel.rooms.services.RoomService;

import java.util.List;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author alejandro
 */
@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @RequestMapping(path = {"/rooms", "rooms/search"})
    public String mainRoom(Model model, String keyword) {
        if (keyword == null) {
            List<Room> roomList = roomService.roomList();
            roomList.sort(new Comparator<Room>() {
                @Override
                public int compare(Room t, Room t1) {
                    if (t.getRoom_number() < t1.getRoom_number()) {
                        return -1;
                    } else if (t.getRoom_number() > t1.getRoom_number()) {
                        return 1;
                    }
                    return 0;
                }
            });

            model.addAttribute("roomList", roomList);
        } else {
            List<Room> roomList = roomService.getByKeyword(keyword);
            roomList.sort(new Comparator<Room>() {
                @Override
                public int compare(Room t, Room t1) {
                    if (t.getRoom_number() < t1.getRoom_number()) {
                        return -1;
                    } else if (t.getRoom_number() > t1.getRoom_number()) {
                        return 1;
                    }
                    return 0;
                }
            });
            model.addAttribute("roomList", roomList);
        }

        return "rooms";
    }

    @GetMapping("/cleaningStatus")
    public String cleaningStatus(Model model) {
        List<Room> roomList = roomService.roomList();
        roomList.sort(new Comparator<Room>() {
            @Override
            public int compare(Room t, Room t1) {
                if (t.getRoom_number() < t1.getRoom_number()) {
                    return -1;
                } else if (t.getRoom_number() > t1.getRoom_number()) {
                    return 1;
                }
                return 0;
            }
        });

        roomList.removeIf(o -> o.isIs_clean());
        model.addAttribute("roomList", roomList);

        return "cleaningStatus";
    }

    @GetMapping("/newRoom")
    public String addRoom(Model model) {
        model.addAttribute("room", new Room());
        return "newRoom";
    }

    @GetMapping("/room/delete/{room_id}")
    public String deleteRoom(Room room) {
        roomService.delete(room);
        return "redirect:/rooms";
    }

    @GetMapping("/room/editCleaning/{room_id}")
    public String editCleaningStatus(Room room) {
        room = roomService.getRoom(room);
        room.setIs_clean(true);
        roomService.save(room);
        return "redirect:/cleaningStatus";
    }

    @GetMapping("/room/edit/{room_id}")
    public String editRoom(Room room, Model model) {
        room = roomService.getRoom(room);
        model.addAttribute("room", room);
        return "editRoom";

    }

    @PostMapping("/saveRoom")
    public String saveRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);
        return "redirect:/rooms";
    }

    @PostMapping("/updateRoom/{room_id}")
    public String updateRoom(Room room) {
        roomService.save(room);
        return "redirect:/rooms";
    }
}
