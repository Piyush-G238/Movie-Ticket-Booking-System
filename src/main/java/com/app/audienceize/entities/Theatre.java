package com.app.audienceize.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "theatres")
@NoArgsConstructor
@AllArgsConstructor
public class Theatre {
    @Id
    private String theatreId;
    @Column(name = "theatre_name", unique = true)
    private String theatreName;
    private String city;
    private String address;
}
