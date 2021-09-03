package proton.base;


import java.util.List;
import java.util.Optional;

/**
 * В простом случае классы Сервисов яыляются обёртками над репозиториями,
 * т.е. просто транслируют запросы соответствуемущему репозиторию.
 * Дополнительно могут реализовывать бизнес-логику.
 */
public abstract class BaseServiceImpl<E extends BaseDict, R extends BaseRepository<E>>
        implements BaseService<E> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public List<E> findAll(String filter) {
        if (filter == null || filter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.findByFilter(filter);
        }
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
