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
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@Test
	@DisplayName("ValidSystem \"startRemoteSystem\"")
	public void ValidUserValidSystem() throws Exception {

		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		// llamada al api a probar
		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(), "[uno, dos]");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + validId);
	}

	@Test
	@DisplayName("InValidSystem \"startRemoteSystem\"")
	public void ValidUserInvalidSystem() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String invalidId = "12345"; // id valido de sistema
		when(mockGenericDao.getSomeData(validUser, "where id=" + invalidId))
				.thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		assertThrows(SystemManagerException.class,
				() -> manager.startRemoteSystem(validUser.getId(), invalidId), "Fallo en la prediccion");

		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + invalidId);
	}

	@Test
	@DisplayName("ValidSystem \"stopRemoteSystem\"")
	public void ValidUserValidSystem2() throws Exception {

		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));

		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));

		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		// llamada al api a probar
		Collection<Object> retorno = manager.stopRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(), "[uno, dos]");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + validId);
	}

	@Test
	@DisplayName("InvalidSystem \"stopRemoteSystem\"")
	public void ValidUserInvalidSystem2() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String invalidId = "12345"; // id valido de sistema
		when(mockGenericDao.getSomeData(validUser, "where id=" + invalidId))
				.thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		assertThrows(SystemManagerException.class,
				() -> manager.stopRemoteSystem(validUser.getId(), invalidId), "Fallo en la prediccion");

		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + invalidId);
	}

	@Test
	@DisplayName("ValidSystem \"addRemoteSystem\"")
	public void ValidUserValidSystem3() throws Exception {

		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.updateSomeData(validUser, validId)).thenReturn(true);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		// llamada al api a probar
		manager.addRemoteSystem(validUser.getId(), validId);
		//assertEquals no necesario ya que la función es de tipo void
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).updateSomeData(validUser, validId);
	}

	@Test
	@DisplayName("InvalidSystem \"addRemoteSystem\"")
	public void ValidUserInvalidSystem3() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String invalidId = "12345"; // id valido de sistema
		//ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.updateSomeData(validUser, invalidId)).thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao,mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		assertThrows(SystemManagerException.class,
				() -> manager.addRemoteSystem(validUser.getId(), invalidId), "Fallo en la prediccion");

		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).updateSomeData(validUser, invalidId);
	}

	@Test
	@DisplayName("ValidSystem \"deleteRemoteSystem\"")
	public void ValidUserValidSystem4() throws Exception {

		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.deleteSomeData(validUser, validId)).thenReturn(true);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockGenericDao);
		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		// llamada al api a probar
		manager.deleteRemoteSystem(validUser.getId(), validId);
		//assertEquals() no necesario por ser función de tipo void
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).deleteSomeData(validUser, validId);
	}

	@Test
	@DisplayName("ValidSystem \"deleteRemoteSystem\"")
	public void ValidUserInvalidSystem4() throws Exception {

		User validUser = new User("1", "Ana", "Lopez", "Madrid",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		String invalidId = "12345"; // id valido de sistema
		when(mockGenericDao.deleteSomeData(validUser, invalidId)).thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockGenericDao);
		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		// llamada al api a probar
		manager.deleteRemoteSystem(validUser.getId(), invalidId);
		assertThrows(SystemManagerException.class, () -> manager.deleteRemoteSystem(validUser.getId(), invalidId));
		//assertEquals() no necesario por ser función de tipo void
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).deleteSomeData(validUser, invalidId);
	}
}
