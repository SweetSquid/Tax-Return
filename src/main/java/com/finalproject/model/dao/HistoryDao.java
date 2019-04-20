package com.finalproject.model.dao;

import com.finalproject.model.entity.History;

import java.util.List;

/**
 * HistoryDao is an interface that sets methods to implement in JDBCHistoryFactory.
 */
public interface HistoryDao extends GenericDao<History> {
    /**
     * The method returns list of user's histories.
     *
     * @param userId user id.
     * @return {@code List<History>} tax returns list of all inspector's users.
     */
    List<History> getByUser(int userId);

    /**
     * The method returns history list in the range.
     *
     * @param offset beginning of the range.
     * @param length end of the range.
     * @param userId user id.
     * @return {@code List<History>} of tax return in the range.
     */
    List<History> getInRange(int offset, int length, int userId);

    /**
     * The method returns count of user's histories.
     *
     * @param userId user id.
     * @return count of all tax returns.
     */
    int getPageCount(int userId);
}
