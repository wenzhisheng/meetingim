package kingim.service.impl;

import kingim.dao.AdminMapper;
import kingim.dao.WhiteIPMapper;
import kingim.model.AdminVO;
import kingim.model.WhiteIP;
import kingim.service.AdminService;
import kingim.service.WhiteIPService;
import kingim.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 好友Service
 * @author jkxqj
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminVO> implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Override
	public Mapper<AdminVO> getMapper() {
		return adminMapper;
	}

	@Override
	public AdminVO login(AdminVO adminVO) {
		CommonUtil.paramEmptyVerify(adminVO.getAccount(), "账号不能为空");
		CommonUtil.paramEmptyVerify(adminVO.getAccount(), "密码不能为空");
		return adminMapper.login(adminVO);
	}
}
