package proton.repositories;

import proton.entities.BaseDict;


public abstract class BaseServiceImpl<E extends BaseDict, R extends BaseRepo<E>>
        implements BaseService<E> {

    protected final R repository;

//    @Autowired
    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }
}
