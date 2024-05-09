package jpabook.jpashop.queryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.Hello;
import jpabook.jpashop.QHello;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static jpabook.jpashop.domain.QMember.member;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class QuerydslApplicationTests {
    @Autowired
    EntityManager em;

    @Test
    void contxtLoads(){
        Hello hello = new Hello();
        em.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QHello qHello = QHello.hello;

        Hello result = query.selectFrom(qHello).fetchOne();

        Assertions.assertThat(result).isEqualTo(hello);
        Assertions.assertThat(result.getId()).isEqualTo(hello.getId());

        long total = query.selectFrom(member).fetchCount();
        System.out.println("total : " + total);

    }
}
