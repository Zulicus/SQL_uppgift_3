package main;

import java.util.ArrayList;
import java.util.Scanner;

import beans.MovieBean;
import database.SQL_connection;

public class SQL_Start {

	public static void main(String[] args) {
		boolean exit = false;
		Scanner scan = new Scanner(System.in);
		String select;
		ArrayList<MovieBean> movies = new ArrayList<MovieBean>();

		viewOptions();
		while (!exit) {
			movies.clear();
			select = "";
			System.out.println("Select Option: ");
			switch (scan.nextLine()) {
			// all actors in a movie
			case "1":
				System.out.println("1: Enter Movie");
				select = waitForInput(scan);
				if (SQL_connection.connectSQL()) {
					movies = SQL_connection.query_movie_to_actor(select);
				}
				for (int i = 0; i < movies.size(); i++) {
					System.out.println(movies.get(i).getMovieYear() + " \"" + movies.get(i).getMovieName() + "\":  "
							+ movies.get(i).getActorName());
				}
				break;
			// Oscar for best picture
			case "2":
				if (SQL_connection.connectSQL()) {
					movies = SQL_connection.query_best_picture();
				}
				displayAward(movies);
				break;
			// movies that contains a certain actor
			case "3":
				System.out.println("3: Enter Actor");
				select = waitForInput(scan);
				if (SQL_connection.connectSQL()) {
					movies = SQL_connection.query_actor_to_movie(select);
				}
				for (int i = 0; i < movies.size(); i++) {
					System.out.println(movies.get(i).getActorName() + ": \"" + movies.get(i).getMovieName() + "\" from "
							+ movies.get(i).getMovieYear());
				}
				break;
			// awards that a certain actor's movies have
			case "4":
				System.out.println("4: Enter Actor");
				select = waitForInput(scan);
				if (SQL_connection.connectSQL()) {
					movies = SQL_connection.query_actor_to_movieaward(select);
				}
				displayAward(movies);
				break;
			// new movie
			case "5":
				newMovie(scan);
				System.out.println("Movie added");
				break;
			// exit
			case "6":
				exit = true;
				System.out.println("Exiting");
				break;
			// error
			default:
				System.out.println("Please enter a valid Option");
				viewOptions();
				break;
			}

			if (SQL_connection.connectSQL()) {
				// movies = SQL_connection.stateSQL(actor);
			}

		}
		scan.close();
	}

	private static String waitForInput(Scanner scan) {
		while (true) {
			return scan.nextLine();
		}
	}

	private static void displayAward(ArrayList<MovieBean> movies) {
		for (int i = 0; i < movies.size(); i++) {
			System.out.println(movies.get(i).getMovieYear() + " \"" + movies.get(i).getMovieName() + "\":  "
					+ movies.get(i).getAwardCategory() + ", " + movies.get(i).getAwardName());
		}

	}

	private static void newMovie(Scanner scan) {
		String movieName, movieYear;
		System.out.println("Enter movie name: ");
		movieName = waitForInput(scan);
		System.out.println("Enter the year of the movie: ");
		movieYear = waitForInput(scan);
		if (SQL_connection.connectSQL()) {
			SQL_connection.add_movie(movieName, movieYear);
		}
	}

	private static void viewOptions() {
		System.out.println("Options:");
		System.out.println("1: Find all actors in a movie");
		System.out.println("2: Find all movies that have recieved an Oscar for best picture");
		System.out.println("3: Find all movies that contains a certain actor");
		System.out.println("4: Find all awards that a certain actor's movies have recieved");
		System.out.println("5: Enter a new movie");
		System.out.println("6: Exit");

	}

}
