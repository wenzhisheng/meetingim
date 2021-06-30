package kingim.vo;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-04-29 19:58
 * @className kingim.vo.SearchMessage
 */
public class SearchMessage {

    private Integer id;
    private String type;
    private Integer pageNum;
    private Integer pageSize;
    private String searchStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
}
