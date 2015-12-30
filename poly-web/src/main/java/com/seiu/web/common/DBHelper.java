package com.seiu.web.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.vega.db.ConnectionPool;

public class DBHelper {
	public static Logger logger = Logger.getLogger(DBHelper.class);

	public static Connection getConnection() throws SQLException {
		ConnectionPool dbPool = ServiceLoader.getDbPool();
		return dbPool.getConnection();
	}

	public static String getString(String sql) {
		String result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			debugQuery(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (SQLException ex) {
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt, conn);
		}
		return result;
	}

	public static String getString(String sql, Object... params) {
		String result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			debugQuery(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (SQLException ex) {
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt, conn);
		}
		return result;
	}

	public static int getInt(String sql, Object... params) {
		int result = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			debugQuery(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		} catch (SQLException ex) {
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt, conn);
		}
		return 0;
	}

	public static List<String> getList(String sql, Object... params) {
		List<String> result = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			debugQuery(stmt);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String r = rs.getString(1);
				if (r != null) {
					result.add(r);
				}
			}
			return result;
		} catch (SQLException ex) {
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt, conn);
		}
		return result;
	}

	public static String getString(Connection conn, String sql, Object... params) {
		String result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			debugQuery(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (SQLException ex) {
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt);
		}
		return result;
	}

	public static int getInt(Connection conn, String sql, Object... params) {
		int result = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			debugQuery(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		} catch (SQLException ex) {
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt);
		}
		return 0;
	}

	public static List<String> getList(Connection conn, String sql, Object... params) {
		List<String> result = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			debugQuery(stmt);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String r = rs.getString(1);
				if (r != null) {
					result.add(r);
				}
			}
			return result;
		} catch (SQLException ex) {
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt);
		}
		return result;
	}

	public static int insertOrUpdate(String query, Object... params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(query);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param == null) {
						stmt.setString(i, null);
					} else {
						stmt.setString(i, String.valueOf(param));
					}
					i++;
				}
			}
			debugQuery(stmt);
			return stmt.executeUpdate();
		} catch (Exception ex) {
			logger.error("error, ex : query " + query, ex);
		} finally {
			freeConnection(stmt, conn);
		}
		return 0;
	}

	public static int insertOrUpdate(Connection conn, String query, Object... params) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			debugQuery(stmt);
			return stmt.executeUpdate();
		} catch (Exception ex) {
			logger.error("error, ex : query " + query, ex);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public static int insert(String sql, boolean getAutoIncreaseKey, Object... params) {
		if (getAutoIncreaseKey) {
			PreparedStatement stmt = null;
			Connection conn = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				if (params != null && params.length > 0) {
					int i = 1;
					for (Object param : params) {
						if (param != null) {
							stmt.setString(i, String.valueOf(param));
						} else {
							stmt.setString(i, null);
						}
						i++;
					}
				}
				debugQuery(stmt);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			} catch (SQLException ex) {
				logger.error("error, ex : query " + sql, ex);
			} finally {
				freeConnection(rs, stmt, conn);
			}
			return 0;
		} else {
			return insertOrUpdate(sql, params);
		}

	}

	public static Map<String, String> getMap(String sql, Object... params) {
		List<Map<String, String>> rows = executeQuery(sql, params);
		if (rows != null && rows.size() > 0) {
			return rows.get(0);
		} else {
			return null;
		}
	}

	public static Map<String, String> getMapWithLimit(String sql, int start, int pageSize,
	        Object... params) {
		List<Map<String, String>> rows = executeQueryWithLimit(sql, start, pageSize, params);
		if (rows != null && rows.size() > 0) {
			return rows.get(0);
		} else {
			return null;
		}
	}

	public static List<Map<String, String>> executeQuery(String sql, Object... params) {
		List<Map<String, String>> result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object param : params) {
					if (param instanceof Integer) {
						stmt.setInt(i, (Integer) param);
					} else {
						if (param != null) {
							stmt.setString(i, String.valueOf(param));
						} else {
							stmt.setString(i, null);
						}
					}
					i++;
				}
			}
			// stmt.setInt(1, 0);
			// stmt.setInt(2, 10);
			debugQuery(stmt);
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			result = new LinkedList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int k = 1; k <= columnsNumber; k++) {
					String columnName = rsmd.getColumnLabel(k);
					map.put(columnName, rs.getString(k));
				}
				result.add(map);
			}
			return result;
		} catch (SQLException ex) {
			// logger.error("error, ex : query " + sql, ex);
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt, conn);
		}
		return result;

	}

	public static List<Map<String, String>> executeQueryWithLimit(String sql, int startIndex,
	        int maxPageSize, Object... params) {
		List<Map<String, String>> result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			int i = 1;
			if (params != null && params.length > 0) {
				for (Object param : params) {
					if (param != null) {
						stmt.setString(i, String.valueOf(param));
					} else {
						stmt.setString(i, null);
					}
					i++;
				}
			}
			stmt.setInt(i++, startIndex);
			stmt.setInt(i++, maxPageSize);
			debugQuery(stmt);
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			result = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int k = 1; k <= columnsNumber; k++) {
					String columnName = rsmd.getColumnName(k);
					map.put(columnName, rs.getString(k));
				}
				result.add(map);
			}
			return result;
		} catch (SQLException ex) {
			// logger.error("error, ex : query " + sql, ex);
			logger.error("error, ex : query " + sql, ex);
		} finally {
			freeConnection(rs, stmt, conn);
		}
		return result;

	}

	public static void freeConnection(PreparedStatement stmt, Connection conn) {
		freeConnection(null, stmt, conn);
	}

	public static void freeConnection(ResultSet rs, PreparedStatement stmt) {
		freeConnection(rs, stmt, null);
	}

	public static void freeConnection(Connection conn) {
		freeConnection(null, null, conn);
	}

	public static void freeConnection(ResultSet rs, PreparedStatement stmt, Connection conn) {
		logger.info("run free connection ");
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				logger.warn("error in close result set: " + ex.getMessage(), ex);
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				logger.warn("error in close statement: " + ex.getMessage(), ex);
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				logger.warn("error in releasing connection: " + ex.getMessage(), ex);
			}
		}
	}

	public static void debugQuery(PreparedStatement stmt) {
		if (stmt != null) {
			String sql_log = stmt.toString();
			System.out.println("============" + sql_log);
			sql_log = sql_log.substring(sql_log.indexOf(":") + 1).trim();
			logger.info(sql_log);
		} else {
			logger.info("stmt=" + stmt);
			System.out.println("============" + stmt);
		}

	}

}
