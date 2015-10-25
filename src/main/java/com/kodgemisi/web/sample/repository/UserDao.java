package com.kodgemisi.web.sample.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.kodgemisi.web.sample.model.User;

import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {

	public User findByEmail(String email) {
		Criteria c = this.createCriteria();

		c.add(Restrictions.eq("email", email));
		return (User) c.uniqueResult();
	}

	public List<User> getAllWithItems() {
		Criteria c = this.createCriteria();

        return c.list();
	}
}
