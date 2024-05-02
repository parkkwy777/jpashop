package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 트렌젝션 존재해야한다. public 메서드는 트렌젝션 걸려진다.
@RequiredArgsConstructor
/**
//final이 있는 필드만가지고 생성자를 만들어준다. (롬복기능)
생성자가 하나일경우는 @Autowired 생략해도 주입을해줌
    RequiredArgsConstructor는 아래 메서드를 생성해줌(final인값만)

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
}
 */

public class MemberService {

    //final 어노테이션을 넣는이유는 빌드 시점에서 memberRepository가 존재하는지 확인 가능하기때문.
    private final MemberRepository memberRepository;
    /*

    public void setMemberRepository(MemberReopository memberRepository){
         this.memberRepository = memberRepository;
    }
    //private MemberRepository memberRepository;

    //생성자가 하나일경우는 @Autowired 생략해도 주입을해줌
    //RequiredArgsConstructor는 아래 메서드를 생성해줌(final인값만)
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */

    //회원가입
    public Long join(Member member) throws IllegalStateException {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) throws IllegalStateException {
        //EXCEPTION
        /*
        동시성 문제로인해(여러개의 was일경우 동시에 같은이름으로 가입하는문제) member테이블 name값을 유니크 제약조건을 걸어서
        중복 가입을 방지하는 방법이있다.
        */

        List<Member> findMembers = memberRepository.findByName(member.getUsername());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    @Transactional(readOnly = true) //조회부분은 readOnly true를 하면 성능이 좀 더 빠르다. 변경안됨 true할 경우
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 단건 조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
