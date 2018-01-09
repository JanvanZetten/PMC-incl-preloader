/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pmc.be.Genre;
import pmc.be.Movie;

/**
 *
 * @author janvanzetten
 */
public class DBManager {

    private final DBConnecter DBCon;

    public DBManager() {
        DBCon = new DBConnecter();
    }

    List<Genre> getAllGenres() throws DalExeption {
        try (Connection con = DBCon.getConnection()) {
            String sql = "SELECT * FROM Genre;";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            List<Genre> genres = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                Genre genre = new Genre(
                        id,
                        rs.getString("name")
                );
                genres.add(genre);
            }

            return genres;
        } catch (SQLException ex) {
            throw new DalExeption(ex.getMessage(), ex.getCause());
        }
    }

    
    /**
     * For getting all th movies in the database
     * @return a list of all the movies in the database
     * @throws DalExeption 
     */
    List<Movie> getAllMovies() throws DalExeption {
        try (Connection con = DBCon.getConnection()) {
            String sql = "SELECT * FROM Movie;";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            List<Movie> movies = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                

                Movie movie = new Movie(id,
                        rs.getString("name"),
                        rs.getString("filePath"),
                        getGenresOfMovie(id)
                );
                movie.setImdbRating(rs.getInt("ImdbRating"));
                movie.setPersonalRating(rs.getInt("personalRating"));
                movie.setLastView(rs.getInt("lastView"));
                movies.add(movie);
            }

            return movies;
        } catch (SQLException ex) {
            throw new DalExeption(ex.getMessage(), ex.getCause());
        }
    }

    
    /**
     * Gets all the genres for a movie
     * @param Movieid the id for the movie from which to find the genres
     * @return a list with genre objects
     * @throws DalExeption 
     */
    private List<Genre> getGenresOfMovie(int Movieid) throws DalExeption {
        try (Connection con = DBCon.getConnection()) {
            String sql = "SELECT * FROM GenresInMovie WHERE movieId = " + Movieid + ";";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            List<Genre> genres = new ArrayList<>();

            while (rs.next()) {
                int genreid = rs.getInt("genreId");

                String sql2 = "SELECT * FROM Genre WHERE id = " + genreid + ";";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                while (rs2.next()) {
                    int id2 = rs2.getInt("id");
                    Genre genre = new Genre(
                            id2,
                            rs2.getString("name")
                    );
                    genres.add(genre);
                }
            }
            
            return genres;
        } catch (SQLException ex) {
            throw new DalExeption(ex.getMessage(), ex.getCause());
        }

    }
}
