package proton.repositories;

import proton.entities.BaseDict;


public abstract class AbstractService<E extends BaseDict, R extends BaseRepo<E>>
        implements CommonService<E> {

    protected final R repository;

//    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

//другие методы, переопределённые из интерфейса
}
