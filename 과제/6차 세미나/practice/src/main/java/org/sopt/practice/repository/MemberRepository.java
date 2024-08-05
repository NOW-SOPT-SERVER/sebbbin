package org.sopt.practice.repository;

import org.sopt.practice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Jparepository가 어디서 작동할지, 어떤..
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
}
