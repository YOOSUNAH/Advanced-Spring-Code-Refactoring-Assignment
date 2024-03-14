package com.example.potatotilnewsfeed.domain.user.repository;

import static com.example.potatotilnewsfeed.domain.user.entity.QUser.user;

import com.example.potatotilnewsfeed.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return queryFactory.selectFrom(user)
            .where(equalNickname(nickname))
            .fetch()
            .stream()
            .findFirst();
    }

    @Override
    public List<User> findByEmailEndPoint(String email) {
        return queryFactory
            .selectFrom(user)
            .where(
                endWithEmail(email))
            .fetch();
    }

    private BooleanExpression equalNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            return null;
        }
        return user.nickname.eq(nickname);
    }

    private BooleanExpression endWithEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        return user.email.endsWith(email);
    }
}
