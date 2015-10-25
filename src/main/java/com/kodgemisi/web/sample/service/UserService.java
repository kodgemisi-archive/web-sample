package com.kodgemisi.web.sample.service;

import com.kodgemisi.web.sample.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kodgemisi.web.sample.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class UserService extends GenericService<User> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;

	@Override
	public Long create(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		return super.create(user);
	}

    @Override
    public User update(User user) {

        String[] ignoreProps = this.getNullPropertyNames(user);

        // if password is unchanged, don't update it otherwise it becomes empty string
        if(user.getPassword().isEmpty()) {
            ignoreProps = Stream.of(ignoreProps, new String[]{"password"}).flatMap(Stream::of)
                    .toArray(String[]::new);
        }

        return super.update(user, ignoreProps);
    }

    public List<User> getAllWithItems() {
        return userDao.getAllWithItems();
    }
}
