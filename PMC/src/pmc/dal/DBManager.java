/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     *
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
                movie.setDirectors(rs.getString("directors"));
                movie.setImage(rs.getBytes("imageInBytes"));
                movie.setDuration(rs.getInt("duration"));
                movie.setImdbUrl(rs.getString("ImdbUrl"));
                movie.setYear(rs.getInt("year"));
                movie.setImage(rs.getBytes("imageInBytes"));
                movies.add(movie);
            }

            return movies;
        } catch (SQLException ex) {
            throw new DalExeption(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Adds a new genre to the database
     *
     * @param Name the name the new genre should have
     * @return the newly made Genre object
     * @throws DalExeption
     */
    Genre addNewGenre(String name) throws DalExeption {
        try (Connection con = DBCon.getConnection()) {
            String sql = "INSERT INTO Genre VALUES (?);";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);
            if (statement.executeUpdate() == 1) {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                return new Genre(id, name);
            }
            throw new DalExeption("New genre could not be made, check database connection");

        } catch (SQLException ex) {
            throw new DalExeption(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Delete a genre from the database
     *
     * @param genre the genre to be deleted
     * @return true if deleted succesfully
     * @throws DalExeption
     */
    boolean deleteGenre(Genre genre) throws DalExeption {
        try (Connection con = DBCon.getConnection()) {

            String sql = "DELETE Genre WHERE id=?;";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, genre.getId());

            if (statement.executeUpdate() == 1) {
                return true;
            } else {
                throw new DalExeption("Could not delete genre: " + genre.getName());
            }
        } catch (SQLException ex) {
            throw new DalExeption(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Gets all the genres for a movie
     *
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
