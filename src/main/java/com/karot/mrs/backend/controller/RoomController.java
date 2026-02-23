package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.CreateRoomRequest;
import com.karot.mrs.backend.dto.request.UpdateRoomRequest;
import com.karot.mrs.backend.dto.response.RoomDto;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/theaters/{theaterId}/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/auto")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoomDto> createRoom(@PathVariable Long theaterId, @Validated  @RequestBody CreateRoomRequest request) throws MrsException {
        return roomService.createRoom(theaterId,request);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<RoomDto>> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDto> updateRoom(@Validated @PathVariable Long theaterId, @PathVariable Long roomId, @RequestBody UpdateRoomRequest request) throws MrsException {
        return ResponseEntity.ok(roomService.updateRoom(theaterId, roomId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoomDto> getById(@PathVariable Long theaterId,@PathVariable Long id) throws MrsException {
        return ResponseEntity.ok(roomService.getRoomById(theaterId,id));
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long id){
//        roomService.deleteRoom(id);
//        return ResponseEntity.noContent().build();
//    }
}
