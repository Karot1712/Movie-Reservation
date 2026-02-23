package com.karot.mrs.backend.entity;

import com.karot.mrs.backend.dto.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "seats",uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "row_label", "col_number"}))
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rowLabel;
    private Integer colNumber;
    private String seatCode;

    @Enumerated(EnumType.STRING)
    private SeatType type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @PrePersist
    @PreUpdate
    public void generateSeatCode(){
        this.seatCode = this.rowLabel + this.colNumber;
    }
}
