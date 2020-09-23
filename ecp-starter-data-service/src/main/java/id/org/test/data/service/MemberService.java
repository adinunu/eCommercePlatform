package id.org.test.data.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.data.model.Member;
import id.org.test.data.service.wrapper.MemberWrapper;

public interface MemberService extends CommonService<MemberWrapper, Long> {

	Long getMemberId(String username);

	Page<MemberWrapper> getPageableList(String param, int startPage, int pageSize, Sort sort) throws Exception;

	MemberWrapper getByUser(String username) throws Exception;

	void deleteAll();

	MemberWrapper updateProfile(MemberWrapper wrapper) throws Exception;

	MemberWrapper getByEmailAddress(String emailAddress) throws Exception;

	MemberWrapper getByIdUpdate(Long aLong) throws Exception;

	Member findByUsername(String username) throws Exception;

}
