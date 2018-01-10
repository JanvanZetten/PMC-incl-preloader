/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                movie.setImdbRating(rs.getDouble("ImdbRating"));
                movie.setPersonalRating(rs.getInt("personalRating"));
                movie.setLastView(rs.getInt("lastView"));
                movie.setDirectors(rs.getString("directors"));
                movie.setDuration(rs.getInt("duration"));
                movie.setImdbUrl(rs.getString("ImdbUrl"));
                movie.setYear(rs.getInt("year"));

                //image
                Blob imageAsBlob;
                imageAsBlob = rs.getBlob("imageInBytes");
                movie.setImage(imageAsBlob.getBytes(1, (int) imageAsBlob.length()));

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

    Movie addMovie(String name, String filePath, List<Genre> genres,
            double imdbRating, int personalRating, String Directors,
            int duration, String ImdbUrl, int year, byte[] imageInBytes) throws DalExeption {
        try (Connection con = DBCon.getConnection()) {
            String sql = "INSERT INTO Movie (name, personalRating, ImdbRating, lastView, filePath, ImdbUrl, year, duration, directors, imageInBytes) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);
            statement.setInt(2, personalRating);
            statement.setDouble(3, imdbRating);
            statement.setInt(4, -1); //lastview is -1 because a newly added movie has not been seen yet
            statement.setString(5, filePath);
            statement.setString(6, ImdbUrl);
            statement.setInt(7, year);
            statement.setInt(8, duration);
            statement.setString(9, Directors);

            ByteArrayInputStream bais = new ByteArrayInputStream(imageInBytes);
            statement.setBinaryStream(10, bais, imageInBytes.length);

            if (statement.executeUpdate() == 1) {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                if (addGenresToMovie(id, genres).size() == genres.size()) {
                    Movie movie = new Movie(id, name, year, duration, genres, personalRating, imdbRating, Directors, -1, filePath, ImdbUrl);
                    movie.setImage(imageInBytes);
                    return movie;
                }
            }

            throw new DalExeption("Could not add new Movie to database");

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

    /**
     * Connect genres with movie
     *
     * @param movieId the movies movieId
     * @param genres the list of Genre it should add to the movie
     * @return a list og the combinedlists primarykeys
     */
    private List<Integer> addGenresToMovie(int movieId, List<Genre> genres) throws DalExeption {
        try (Connection con = DBCon.getConnection()) {
            List<Integer> keys = new ArrayList<>();
            String sql = "INSERT INTO GenresInMovie (movieId, genreId) VALUES (?, ?)";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (Genre genre : genres) {
                statement.setInt(1, movieId);
                statement.setInt(2, genre.getId());

                if (statement.executeUpdate() == 1) {
                    ResultSet rs = statement.getGeneratedKeys();
                    rs.next();
                    int id = rs.getInt(1);
                    keys.add(id);
                }
                else{
                    throw new DalExeption("Could not connect Movie and Genre" );
                }
            }
            return keys;

        } catch (SQLException ex) {
            throw new DalExeption(ex.getMessage(), ex.getCause());
        }

    }
}
