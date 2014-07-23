package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ferramentas {

	public static void direcionaParaHtml(HttpServletRequest request,
			HttpServletResponse response, String pagina) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(pagina);
		rd.forward(request, response);
	}

	public static Connection getConection() throws SQLException {
		DriverManager.registerDriver(new org.hsqldb.jdbcDriver());
		return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/lojaVirtual", "SA", "");
	}

	public static void releaseDatabaseResources(PreparedStatement statement, Connection connection) {
		try {
			statement.close();
		} catch (Exception e) {
		}

		try {
			connection.close();
		} catch (Exception e) {
		}
	}
}
