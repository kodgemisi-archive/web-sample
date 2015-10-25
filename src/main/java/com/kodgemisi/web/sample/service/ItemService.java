/*
 * Copyright (C) 10, 2015 Kod Gemisi Ltd. <foss@kodgemisi.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kodgemisi.web.sample.service;

import com.kodgemisi.web.sample.model.Item;
import com.kodgemisi.web.sample.repository.ItemDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.kodgemisi.web.sample.model.User;

import javax.transaction.Transactional;

@Service
@Transactional
public class ItemService extends GenericService<Item> {

	@Autowired
	private ItemDao itemDao;

    @Autowired
    protected SessionFactory sessionFactory;

	public Long createAndAttachToUser(Item item) {

        Long retval = super.create(item);

		// Get the current user and add the item among its items
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // User instance is detached from session when grabbed via SecurityContextHolder
        // So we need to bind the user instance to session again to make lazy loading of
        // `items` to work
        sessionFactory.getCurrentSession().refresh(user);

        user.getItems().add(item);

		return retval;
	}
}
