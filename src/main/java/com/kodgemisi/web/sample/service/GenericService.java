
package com.kodgemisi.web.sample.service;

import com.kodgemisi.web.sample.model.AbstractBaseModel;
import com.kodgemisi.web.sample.repository.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.beans.FeatureDescriptor;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

@Transactional
public abstract class GenericService<T extends AbstractBaseModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Logger logger;

	public GenericService() {
		Class<?> type = this.getClass().getSuperclass();
		this.logger = LoggerFactory.getLogger(type);
	}

	@Autowired
	protected GenericDao<T> genericDao;

	public Long create(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		return this.genericDao.create(t);
	}

	public T getById(final Long id) {

		if (id == null) {
			throw new RuntimeException("Id cannot be null");
		}

		return this.genericDao.getById(id);
	}

	public T update(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

        T oldObject = genericDao.getById(t.getId());

        BeanUtils.copyProperties(t, oldObject, this.getNullPropertyNames(t));

		return this.genericDao.update(oldObject);
	}

    public T update(final T t, String[] ignoreProperties) {

        if (t == null) {
            throw new RuntimeException("Model cannot be null");
        }

        T oldObject = genericDao.getById(t.getId());

        BeanUtils.copyProperties(t, oldObject, ignoreProperties);

        return this.genericDao.update(oldObject);
    }

	public void delete(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		this.genericDao.delete(t);
	}

	public void delete(final Long id) {

		if (id == null) {
			throw new RuntimeException("id cannot be null");
		}

		this.genericDao.delete(this.getById(id));
	}

	public List<T> getAll() {

		return this.genericDao.getAll();
	}

	public void hardDelete(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		this.genericDao.delete(t);
	}

    protected static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}