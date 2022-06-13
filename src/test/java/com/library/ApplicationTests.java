package com.library;

import com.library.member.controller.MemberController;
import com.library.member.repository.MemberRepository;
import com.library.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTests {

	@Autowired
	private MemberService memberService;

	@MockBean
	private MemberRepository memberRepository;
	@Test
	void contextLoads( ) {

	}

}
