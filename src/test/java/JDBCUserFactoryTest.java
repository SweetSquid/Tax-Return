import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.impl.JDBCDaoFactory;
import com.finalproject.model.dao.impl.JDBCTaxReturnFactory;
import com.finalproject.model.dao.impl.JDBCUserFactory;
import com.finalproject.model.entity.ChangeInspectorReport;
import com.finalproject.model.service.ChangeInspectorReportService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertTrue;

public class JDBCUserFactoryTest {
    private JDBCDaoFactory jdbcDaoFactory;
    private Connection connection;
    private JDBCUserFactory jdbcUserFactory;
    private DaoFactory daoFactory;

    @Before
    public void init() {
        daoFactory = DaoFactory.getInstance();
    }


    @Test
    public void udpateTest() {
        JDBCTaxReturnFactory dao = daoFactory.createTaxReturn();
        assertTrue(dao.taxReturnHasReport(21));
    }


    @Test
    public void changeInspectorTest() {
        ChangeInspectorReportService changeInspectorReportService = new ChangeInspectorReportService();
        ChangeInspectorReport changeInspectorReport = changeInspectorReportService.id(7);
        changeInspectorReport.setNewInspectorId(2);
        changeInspectorReport.setMessage("");
        changeInspectorReport.setStatus(ChangeInspectorReport.Status.APPROVED);
        changeInspectorReportService.update(changeInspectorReport, 3);
    }


}
