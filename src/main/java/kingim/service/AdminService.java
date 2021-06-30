package kingim.service;

import kingim.model.AdminVO;

public interface AdminService extends BaseService<AdminVO> {

    AdminVO login(AdminVO adminVO);

}
