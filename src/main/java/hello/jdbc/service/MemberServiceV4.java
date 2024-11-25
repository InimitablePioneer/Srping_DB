package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * 트랜잭션 - @Transactional AOP
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV4 {

    //private final TransactionTemplate txTemplate;
    private final MemberRepository memberRepository;

    @Transactional
    public void accountTransfer(String formId, String toId, int money){
        bizLogic(formId, toId, money);
    }

    private void bizLogic(String formId, String toId, int money) {
        Member fromMember = memberRepository.findById(formId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(formId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private static void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); //이후에 커넥션 풀에 들어갈때 상단의 false 가 유지됨으로 이과정 통해 기본값인 true 로 바꿔 두어야함
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
