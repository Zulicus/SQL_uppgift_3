package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.MovieBean;

public class SQL_connection {

	static Connection conn = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;

	public static boolean connectSQL() {

		try {
			// Driver setup
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Exception Driver: " + e.getMessage());
			return false;
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies", DatabaseLogin.getuName(),
					DatabaseLogin.getuPass());
			return true;
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			return false;
		}
	}

	public static ArrayList<MovieBean> query_actor_to_movie(String actor) {
		ArrayList<MovieBean> list = new ArrayList<MovieBean>();
		try {

			String requestQuery = "SELECT movies.movie_name, movies.movie_year, actors.actor_name FROM movie_actor JOIN movies ON movies.movie_id = movie_actor.movie_id JOIN actors ON actors.actor_id = movie_actor.actor_id WHERE actors.actor_name LIKE ?";
			stmt = conn.prepareStatement(requestQuery);

			stmt.setString(1, "%" + actor + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				MovieBean bean = new MovieBean();
				bean.setActorName(rs.getString("actor_name"));
				bean.setMovieName(rs.getString("movie_name"));
				bean.setMovieYear(rs.getString("movie_year"));
				list.add(bean);
			}
			rs.close();
			conn.endRequest();
			conn.close();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());

		}
		return list;

	}

	public static ArrayList<MovieBean> query_movie_to_actor(String movie) {
		ArrayList<MovieBean> list = new ArrayList<MovieBean>();
		try {

			String requestQuery = "SELECT movies.movie_name, movies.movie_year, actors.actor_name FROM movie_actor JOIN movies ON movies.movie_id = movie_actor.movie_id JOIN actors ON actors.actor_id = movie_actor.actor_id WHERE movies.movie_name LIKE ?";
			stmt = conn.prepareStatement(requestQuery);

			stmt.setString(1, "%" + movie + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				MovieBean bean = new MovieBean();
				bean.setActorName(rs.getString("actor_name"));
				bean.setMovieName(rs.getString("movie_name"));
				bean.setMovieYear(rs.getString("movie_year"));
				list.add(bean);
			}
			rs.close();
			conn.endRequest();
			conn.close();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());

		}
		return list;

	}

	public static ArrayList<MovieBean> query_best_picture() {
		ArrayList<MovieBean> list = new ArrayList<MovieBean>();
		try {

			String requestQuery = "SELECT movies.movie_name, movies.movie_year, awards.award_category, awards.award_award FROM movie_awards JOIN movies ON movies.movie_id = movie_awards.movie_id JOIN awards ON awards.award_id = movie_awards.award_id WHERE awards.award_category = 'best picture' AND awards.award_award = 'academy awards'";
			stmt = conn.prepareStatement(requestQuery);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MovieBean bean = new MovieBean();
				bean.setAwardCategory(rs.getString("award_category"));
				bean.setAwardName(rs.getString("award_award"));
				bean.setMovieName(rs.getString("movie_name"));
				bean.setMovieYear(rs.getString("movie_year"));
				list.add(bean);
			}
			rs.close();
			conn.endRequest();
			conn.close();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());

		}
		return list;
	}

	public static ArrayList<MovieBean> query_actor_to_movieaward(String actor) {
		ArrayList<MovieBean> list = new ArrayList<MovieBean>();
		try {

			String requestQuery = "SELECT movies.movie_name, movies.movie_year, awards.award_category, awards.award_award FROM movie_awards JOIN movies ON movies.movie_id = movie_awards.movie_id JOIN awards ON awards.award_id = movie_awards.award_id WHERE movies.movie_id IN( SELECT movies.movie_id FROM movie_actor JOIN movies ON movies.movie_id = movie_actor.movie_id JOIN actors ON actors.actor_id = movie_actor.actor_id WHERE actors.actor_name LIKE ? )";
			stmt = conn.prepareStatement(requestQuery);
			stmt.setString(1, "%" + actor + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				MovieBean bean = new MovieBean();
				bean.setAwardCategory(rs.getString("award_category"));
				bean.setAwardName(rs.getString("award_award"));
				bean.setMovieName(rs.getString("movie_name"));
				bean.setMovieYear(rs.getString("movie_year"));
				list.add(bean);
			}
			rs.close();
			conn.endRequest();
			conn.close();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());

		}
		return list;
	}

	public static void add_movie(String movieName, String movieYear) {

		try {

			String requestQuery = "INSERT INTO `movies`( `movie_name`, `movie_year`) VALUES (?,?)";

			stmt = conn.prepareStatement(requestQuery);

			stmt.setString(1, movieName);
			stmt.setString(2, movieYear);
			stmt.executeUpdate();
			conn.endRequest();
			conn.close();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());

		}

	}
}
