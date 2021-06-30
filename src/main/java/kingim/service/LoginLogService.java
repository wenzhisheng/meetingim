package kingim.service;

import com.github.pagehelper.PageInfo;
import kingim.model.LoginLogVO;
import kingim.model.PageVO;

public interface LoginLogService extends BaseService<LoginLogVO> {

    PageInfo<LoginLogVO> page(PageVO pageVO, LoginLogVO loginLogVO);

}
