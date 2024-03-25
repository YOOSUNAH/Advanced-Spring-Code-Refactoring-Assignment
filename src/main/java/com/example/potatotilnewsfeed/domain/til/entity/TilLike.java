package com.example.potatotilnewsfeed.domain.til.entity;

import com.example.potatotilnewsfeed.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_TILLIKE")
@NoArgsConstructor
public class TilLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tilLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tilId", nullable = false)
    private Til til;

    public TilLike(User user, Til til) {
        this.user = user;
        this.til = til;
    }
}
