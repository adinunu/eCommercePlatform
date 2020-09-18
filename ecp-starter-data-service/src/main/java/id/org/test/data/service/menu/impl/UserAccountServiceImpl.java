package id.org.test.data.service.menu.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.org.test.data.model.UserAccount;
import id.org.test.data.repository.UserAccountRepository;
import id.org.test.data.repository.UsersRepository;
import id.org.test.data.service.menu.UserAccountService;
import id.org.test.data.service.menu.wrapper.UserAccountWrapper;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

	private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);

    private final UserAccountRepository userAccountRepository;

    private final UsersRepository usersRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UsersRepository usersRepository) {
        this.userAccountRepository = userAccountRepository;
        this.usersRepository = usersRepository;
    }

    private UserAccountWrapper toWrapper(UserAccount entity) {
        UserAccountWrapper wrapper = new UserAccountWrapper();
        wrapper.setId(entity.getId());
        wrapper.setFirstName(entity.getFirstName());
        wrapper.setLastName(entity.getLastName());
        wrapper.setNickName(entity.getNickName());
        wrapper.setAccountAge(entity.getAccountAge());
        wrapper.setAccountSex(entity.getAccountSex());
        wrapper.setAccountAddress(entity.getAccountAddress());
        wrapper.setAccountContactNo(entity.getAccountContactNo());
        wrapper.setAccountEmail(entity.getAccountEmail());
        wrapper.setUserUsername(entity.getUser().getUsername());
        return wrapper;
    }

    private UserAccount toEntity(UserAccountWrapper wrapper) {
        UserAccount model = new UserAccount();
        if (wrapper.getId() != null) { //edit mode
            model = userAccountRepository.findOne(wrapper.getId());
        }
        model.setFirstName(wrapper.getFirstName());
        model.setLastName(wrapper.getLastName());
        model.setNickName(wrapper.getNickName());
        model.setAccountAge(wrapper.getAccountAge());
        model.setAccountSex(wrapper.getAccountSex());
        model.setAccountAddress(wrapper.getAccountAddress());
        model.setAccountContactNo(wrapper.getAccountContactNo());
        model.setAccountEmail(wrapper.getAccountEmail());
        model.setUser(usersRepository.findOne(wrapper.getUserUsername()));
        return model;
    }

    private List<UserAccountWrapper> toWrapperList(List<UserAccount> modelList) {
        List<UserAccountWrapper> wrapperList = null;
        if (modelList != null) {
            wrapperList = new ArrayList<>();
            for (UserAccount temp : modelList) {
                wrapperList.add(toWrapper(temp));
            }
        }
        return wrapperList;
    }

    @Override
    public Long getNum() {
        return userAccountRepository.count();
    }

    @Override
    public UserAccountWrapper save(UserAccountWrapper wrapper) throws Exception {
        return toWrapper(userAccountRepository.save(toEntity(wrapper)));
    }

    @Override
    public UserAccountWrapper getById(Long aLong) throws Exception {
        return toWrapper(userAccountRepository.findOne(aLong));
    }

    @Override
    public Boolean delete(Long PK) throws Exception {
        try {
            userAccountRepository.delete(PK);
            return true;
        } catch (Exception e) {
            logger.error("Fail delete", e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public List<UserAccountWrapper> getAll() throws Exception {
        return toWrapperList((List<UserAccount>) userAccountRepository.findAll());
    }

    @Override
    public UserAccountWrapper getByUsername(String username) throws Exception {
        return toWrapper(userAccountRepository.getByUsername(username));
    }

}
