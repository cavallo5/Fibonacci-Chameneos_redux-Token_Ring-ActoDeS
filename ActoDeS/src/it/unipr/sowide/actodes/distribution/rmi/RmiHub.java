package it.unipr.sowide.actodes.distribution.rmi;

import it.unipr.sowide.actodes.registry.Reference;

/**
 *
 * The {@code RmiHub} interface defines the remote methods used by a RMI
 * connector for creating and deleting the connections.
 *
**/

public interface RmiHub extends RmiProxy
{
 /**
  * Gets a proxy.
  *
  * @param d  the remote actor space reference.
  *
  * @return the proxy.
  *
  * @throws Exception if the operation fails.
  *
 **/
 RmiProxy get(final Reference d) throws Exception;

 /**
  * Adds a proxy.
  *
  * @param d  the remote actor space reference.
  * @param p  the proxy.
  *
  * @throws Exception if the operation fails.
  *
 **/
 void add(final Reference d, final RmiProxy p) throws Exception;

 /**
  * Removes a proxy.
  *
  * @param d  the remote actor space reference.
  *
  * @throws Exception if the operation fails.
  *
 **/
 void remove(final Reference d) throws Exception;

 /**
  * Lists the registered proxies.
  *
  * @return the proxies.
  *
  * @throws Exception if the operation fails.
  *
 **/
 RmiProxy[] list() throws Exception;
}
