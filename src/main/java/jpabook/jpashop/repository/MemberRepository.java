package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    /*
        @PersistenceContext -> 원래 표준 어노테이션이지만 spring boot jpa 가 어노테이션만해도 적용될수있게해줌.
        private EntityManager em;
        위의 코드를 생략가능
        spring jpa에서 @Autowired를 붙일수 있게해줌
        @Autowired
        private EntityManager em;
        그렇다면 ROMBOK의 @RequiredArgsConstructor를 이용하여
        FINAL이 붙은곳에 객체 주입(INJECTION)해줌.
    */

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where  m.name = :name", Member.class).setParameter("name", name).getResultList();
    }
}
