package upchi.api.movie.film.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upchi.api.movie.film.controllers.dtos.requests.PostFilmRequest;
import upchi.api.movie.film.controllers.dtos.requests.UpdateFilmRequest;
import upchi.api.movie.film.controllers.dtos.responses.GetFilmResponse;
import upchi.api.movie.film.controllers.dtos.responses.PostFilmResponse;
import upchi.api.movie.film.entities.Film;
import upchi.api.movie.film.repositories.IFilmRepository;
import upchi.api.movie.film.services.interfaces.IFilmService;

@Service
public class FilmServiceImpl implements IFilmService {

    @Autowired
    IFilmRepository repository;

    @Override
    public List<GetFilmResponse> list() {
        return repository
                .findAll()
                .stream()
                .map(this::filmToGetResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GetFilmResponse get(Long id) {
        return repository
                .findById(id)
                .map(this::filmToGetResponse)
                .orElseThrow(() -> new RuntimeException("Film not found"));
    }

    @Override
    public PostFilmResponse create(PostFilmRequest request) {

        Film film = postRequestToFilm(request);
        return filmToPostResponse(repository.save(film));

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public GetFilmResponse update(Long id, UpdateFilmRequest request) {
        Film film = repository.findById(id).orElseThrow(()-> new RuntimeException("No Film found for update"));
        return filmToGetResponse(repository.save(updateRequestToFilm(request, film)));
    }

    /*** Métodos de conversión de datos ***/

    private Film postRequestToFilm(PostFilmRequest request) {

        Film film = new Film();

        film.setTitle(request.getTitle());
        film.setDuration(request.getDuration());
        film.setYear(request.getYear());
        film.setGenre(request.getGenre());

        return film;
    }

    private PostFilmResponse filmToPostResponse(Film film) {

        PostFilmResponse response = new PostFilmResponse();

        response.setId(film.getId());
        response.setTitle(film.getTitle());
        response.setDuration(film.getDuration());

        return response;
    }

    private GetFilmResponse filmToGetResponse(Film film) {

        GetFilmResponse response = new GetFilmResponse();

        response.setId(film.getId());
        response.setTitle(film.getTitle());
        response.setDuration(film.getDuration());
        response.setImg(film.getImage());

        return response;

    }

    private Film updateRequestToFilm(UpdateFilmRequest request, Film film) {

        film.setDuration(request.getDuration());
        film.setTitle(request.getTitle());

        return film;

    }

}