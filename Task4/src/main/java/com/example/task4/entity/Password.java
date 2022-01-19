package com.example.task4.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "passwords")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Password {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
