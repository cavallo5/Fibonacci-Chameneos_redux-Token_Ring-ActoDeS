package it.unipr.sowide.actodes.configuration;

import it.unipr.sowide.actodes.actor.Actor;
import it.unipr.sowide.actodes.executor.Executor;

/**
 *
 * The {@code Builder} class provides a partial implementation of a class
 * used by an executor actor for building an initial set of actors.
 *
**/

public abstract class Builder
{
  /**
   * Builds the initial set of actors.
   *
   * @param e  the executor actor.
   *
  **/
  public abstract void build(final Executor<? extends Actor> e);
}
