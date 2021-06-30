package kingim.service;

import com.github.pagehelper.PageInfo;
import kingim.model.PageVO;
import kingim.model.WhiteIP;

public interface WhiteIPService extends BaseService<WhiteIP> {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:31
     * @description: 分页条件查询IP白名单
     * @param: [pageVO, whiteIP]
     * @return: com.github.pagehelper.PageInfo<kingim.model.WhiteIP>
     */
    PageInfo<WhiteIP> page(PageVO pageVO, WhiteIP whiteIP);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:45
     * @description: 批量删除IP白名单
     * @param: []
     * @return: int
     */
    int deleteWhiteIP(WhiteIP whiteIP);

    boolean isWhiteIP(WhiteIP whiteIP);

}
