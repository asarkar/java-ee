package name.abhijitsarkar.javaee.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
@Data
@AllArgsConstructor
public class Page<T> {
    private int pageNum;
    private int pageSize;
    private long numPages;
    private Collection<T> data;

    public Page() {
        data = new ArrayList<>();
    }

    public <S extends Collection<T>> void setData(S data) {
        this.data = data;
    }

    public void updateFrom(Page<T> from) {
        pageNum = from.pageNum;
        pageSize = from.pageSize;
        numPages = from.numPages;
        data = from.data;
    }
}
