import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.impl.JDBCTaxReturnFactory;
import com.finalproject.model.service.ChangeInspectorReportService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JDBCUserFactoryTest {
    private DaoFactory daoFactory;

    @Before
    public void init() {
        daoFactory = DaoFactory.getInstance();
    }


    @Test
    public void updateTest() {
        JDBCTaxReturnFactory dao = daoFactory.createTaxReturn();
        assertTrue(dao.taxReturnHasReport(21));
    }


    @Test
    public void changeInspectorTest() {
        ChangeInspectorReportService changeInspectorReportService = new ChangeInspectorReportService();
    }


}
