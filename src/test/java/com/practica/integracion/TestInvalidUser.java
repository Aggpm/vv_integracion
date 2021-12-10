package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@Test
	@DisplayName("ValidSystem \"startRemoteSystem\"")
	public void InvalidUserValidSystem() throws Exception {
		User invalidUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = ("1");
		when(mockGenericDao.getSomeData(null, "where id=" + validId))
				.thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager (mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class,
				() -> manager.startRemoteSystem(invalidUser.getId(), validId));

		//Collection<Object> retorno = manager.startRemoteSystem(invalidUser.getId(), validId);
		//assertEquals(retorno.toString(), null);

		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).getSomeData(null, "where id=" + validId);
	}

	@Test
	@DisplayName("ValidSystem \"startRemoteSystem\"")
	public void InvalidUserinvalidSystem() throws Exception {
		User invalidUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = ("1");
		when(mockGenericDao.getSomeData(null, "where id=" + validId))
				.thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager (mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class,
				() -> manager.startRemoteSystem(invalidUser.getId(), validId));

		//Collection<Object> retorno = manager.startRemoteSystem(invalidUser.getId(), validId);
		//assertEquals(retorno.toString(), null);

		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).getSomeData(null, "where id=" + validId);
	}
}
