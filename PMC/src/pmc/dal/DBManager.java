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

    List<Genre> getAllGenres() throws DALException {
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
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * For getting all th movies in the database
     *
     * @return a list of all the movies in the database
     * @throws DALException
     */
    List<Movie> getAllMovies() throws DALException {
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
                String description = rs.getString("description");
                if (description != null) {
                    movie.setSummary(description);
                }

                //image
                Blob imageAsBlob;
                imageAsBlob = rs.getBlob("imageInBytes");
                //System.out.println(Arrays.toString(imageAsBlob.getBytes(1, (int) imageAsBlob.length())));
                movie.setImage(imageAsBlob.getBytes(1, (int) imageAsBlob.length()));

                movies.add(movie);
            }

            return movies;
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Adds a new genre to the database
     *
     * @param Name the name the new genre should have
     * @return the newly made Genre object
     * @throws DALException
     */
    Genre addNewGenre(String name) throws DALException {
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
            throw new DALException("New genre could not be made, check database connection");

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Delete a genre from the database
     *
     * @param genre the genre to be deleted
     * @return true if deleted succesfully
     * @throws DALException
     */
    boolean deleteGenre(Genre genre) throws DALException {
        try (Connection con = DBCon.getConnection()) {

            String sql = "DELETE Genre WHERE id=?;";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, genre.getId());

            if (statement.executeUpdate() == 1) {
                return true;
            } else {
                throw new DALException("Could not delete genre: " + genre.getName());
            }
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    Movie addMovie(String name, String filePath, List<Genre> genres,
            double imdbRating, int personalRating, String Directors,
            int duration, String ImdbUrl, int year, byte[] imageInBytes, String description) throws DALException {
        try (Connection con = DBCon.getConnection()) {
            String sql = "INSERT INTO Movie (name, personalRating, ImdbRating, lastView, filePath, ImdbUrl, year, duration, directors, description, imageInBytes) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            statement.setString(10, description);

            if (imageInBytes != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(imageInBytes);
                statement.setBinaryStream(11, bais, imageInBytes.length);
            } else {
                ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{});
                statement.setBinaryStream(11, bais, 0);
            }

            if (statement.executeUpdate() == 1) {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                if (addGenresToMovie(id, genres).size() == genres.size()) {
                    Movie movie = new Movie(id, name, year, duration, genres, personalRating, imdbRating, Directors, -1, filePath, ImdbUrl);
                    movie.setSummary(description);
                    movie.setImage(imageInBytes);
                    return movie;
                }
            }

            throw new DALException("Could not add new Movie to database");

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * update a movie in database
     *
     * @param updatedMovie the movie object which should have the id of the
     * movie wished to be updated
     * @throws DALException
     */
    void updateMovie(Movie updatedMovie) throws DALException {
        try (Connection con = DBCon.getConnection()) {

            String sql = "UPDATE MOVIE SET name= ?, personalRating= ?, ImdbRating= ?, lastView= ?, filePath= ?, ImdbUrl= ?, "
                    + "year= ?, duration= ?, directors= ?, description= ?, imageInBytes= ? WHERE id = ?;";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, updatedMovie.getName());
            statement.setInt(2, updatedMovie.getPersonalRating());
            statement.setDouble(3, updatedMovie.getImdbRating());
            statement.setInt(4, updatedMovie.getLastView());
            statement.setString(5, updatedMovie.getFilePath());
            statement.setString(6, updatedMovie.getImdbUrl());
            statement.setInt(7, updatedMovie.getYear());
            statement.setInt(8, updatedMovie.getDuration());
            statement.setString(9, updatedMovie.getDirectors());
            statement.setString(10, updatedMovie.getSummary());

            if (updatedMovie.getImageInBytes() != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(updatedMovie.getImageInBytes());
                statement.setBinaryStream(11, bais, updatedMovie.getImageInBytes().length);
            } else {
                ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{});
                statement.setBinaryStream(11, bais, 0);
            }

            ByteArrayInputStream bais = new ByteArrayInputStream(updatedMovie.getImageInBytes());
            statement.setBinaryStream(11, bais, updatedMovie.getImageInBytes().length);

            statement.setInt(12, updatedMovie.getId());

            if (statement.executeUpdate() == 1) {
                updateGenresInMovie(updatedMovie.getId(), updatedMovie.getGenres());
                return;
            }
            throw new DALException("Could not update Movie in database");
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Delete a movie from the database
     *
     * @param movie the movie to be deleted
     * @return true if deleted succesfully
     * @throws DALException
     */
    boolean deleteMovie(Movie movie) throws DALException {

        deleteGenresToMovie(movie.getId(), movie.getGenres());

        try (Connection con = DBCon.getConnection()) {

            String sql = "DELETE Movie WHERE id=?;";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, movie.getId());

            if (statement.executeUpdate() == 1) {
                return true;
            } else {
                throw new DALException("Could not delete movie: " + movie.getName());
            }
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Gets all the genres for a movie
     *
     * @param Movieid the id for the movie from which to find the genres
     * @return a list with genre objects
     * @throws DALException
     */
    private List<Genre> getGenresOfMovie(int Movieid) throws DALException {
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
            throw new DALException(ex.getMessage(), ex.getCause());
        }

    }

    /**
     * Connect genres with movie
     *
     * @param movieId the movies movieId
     * @param genres the list of Genre it should add to the movie
     * @return a list og the combinedlists primarykeys
     */
    private List<Integer> addGenresToMovie(int movieId, List<Genre> genres) throws DALException {
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
                } else {
                    throw new DALException("Could not connect Movie and Genre");
                }
            }
            return keys;

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }

    }

    /**
     * updates the genres in the movie
     *
     * @param movieid
     * @param newGenres
     * @throws DALException
     */
    private void updateGenresInMovie(int movieid, List<Genre> newGenres) throws DALException {
        List<Genre> oldGenres = getGenresOfMovie(movieid);
        boolean test = true;
        List<Genre> genresToAdd = new ArrayList<>();
        try (Connection con = DBCon.getConnection()) {
            for (Genre newGenre : newGenres) {
                test = true;
                for (Genre oldGenre : oldGenres) {
                    if (newGenre.getId() == oldGenre.getId()) {
                        oldGenres.remove(oldGenre);
                        test = false;
                        break;
                    }
                }

                if (test) {
                    genresToAdd.add(newGenre);
                }

            }
            addGenresToMovie(movieid, genresToAdd);

            //Delete Genres in oldGenres
            if (!oldGenres.isEmpty()) {
                deleteGenresToMovie(movieid, oldGenres);
            }

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Delete all the rows in the database where the movieid is the given
     * movieid and the genre has the same id as one of the genres in the list
     *
     * @param movieid the movieid from which to delete the genres
     * @param genres the genres to delete from the movie
     */
    private void deleteGenresToMovie(int movieid, List<Genre> genres) throws DALException {
        try (Connection con = DBCon.getConnection()) {
            String sql = "DELETE GenresInMovie WHERE movieId = ? AND genreId = ?;";

            PreparedStatement statement = con.prepareStatement(sql);

            for (Genre genre : genres) {
                statement.setInt(1, movieid);
                statement.setInt(2, genre.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * true if no movies with this name or imdbUrl
     *
     * @param name
     * @param imdbRating
     * @return
     */
    boolean checkForMovie(String name, String imdbUrl) throws DALException {
        try (Connection con = DBCon.getConnection()) {
            String sql = "SELECT id FROM Movie WHERE name = ? OR ImdbUrl = ?";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);
            statement.setString(2, imdbUrl);

            ResultSet rs = statement.executeQuery();

            return !rs.next();

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }

    }

    
    /**
     * true if no Genre in database with the given name
     * @param name
     * @return
     * @throws DALException 
     */
    boolean checkForExistingGenre(String name) throws DALException {
        try (Connection con = DBCon.getConnection()) {
            String sql = "SELECT id FROM Genre WHERE name = ?";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();

            return !rs.next();
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }
}
