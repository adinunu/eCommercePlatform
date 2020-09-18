package id.org.test.data.service.menu;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.data.service.menu.wrapper.CrudControlAccessWrapper;
import id.org.test.data.service.menu.wrapper.UserMenuWrapper;
import id.org.test.data.service.menu.wrapper.UsersMenuBulkWrapper;

public interface UserMenusService extends CommonService<UserMenuWrapper, Long> {

    Page<UserMenuWrapper> getPageableList(String param, int startPage, int pageSize, Sort sort) throws Exception;

    List<UserMenuWrapper> getByUsername(String username) throws Exception;

    Map<String, Object> getByUsernameGroupByCategory(String username) throws Exception;

    void saveAll(List<UserMenuWrapper> wrapperList) throws Exception;

    Boolean validateUserAccess(String username, String menuUrl);

    CrudControlAccessWrapper getByUsernameAndSimilarMenu(String username, String menuUrl);

    void saveUserMenusBulk(UsersMenuBulkWrapper usersMenuBulkWrapper) throws Exception;

    List<Map<String,Object>> getMenuDisplayByUser(String username)throws Exception;
    
    List<UserMenuWrapper> findProMenusList(String username) throws Exception;
    
    List<UserMenuWrapper> findbyAccountProMenus(String username) throws Exception;
    
    List<UserMenuWrapper> findbyUsernameAndMenuGroup(String username) throws Exception;
}
