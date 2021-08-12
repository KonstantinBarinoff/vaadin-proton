package proton.repositories;

import proton.entities.BaseDict;


public abstract class AbstractService<E extends BaseDict, R extends BaseRepo<E>>
        implements BaseService<E> {

    protected final R repository;

//    @Autowired
    public AbstractService(R repository) {

        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }

//другие методы, переопределённые из интерфейса
}
