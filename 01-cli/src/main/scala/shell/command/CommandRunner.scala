package shell.command

import shell.model.{Environment, IOEnvironment}

/** Base class for an entity that can run a specific shell.command. */
abstract class CommandRunner(val name: String) {
  /** Runs the shell.command with the given args, [[Environment]] and in [[IOEnvironment]]. */
  def run(args: List[String], environment: Environment, ioEnvironment: IOEnvironment)
}