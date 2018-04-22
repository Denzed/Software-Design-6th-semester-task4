package shell.command

import java.io.FileNotFoundException

import shell.model.{Environment, IOEnvironment}

import scala.reflect.io.Directory

/** Command for changing working directory.
  * In case no arguments are provided new directory is user home.
  */
class CdCommandRunner extends CommandRunner("cd") {
  override def run(
                    args: List[String],
                    environment: Environment,
                    ioEnvironment: IOEnvironment
                  ): Unit = {
    val newDirectory: Directory = args.size match {
      case 0 => CdCommandRunnerCompanion.userHomeDir
      case 1 =>
        val arg = Directory(args.head)
        val result = (
          if (arg.isAbsolute) {
            arg
          } else {
            environment.currentDir / arg
          }
        )
          .toFile
          .toCanonical
          .normalize
          .toDirectory
        if (!result.exists) {
          throw new FileNotFoundException("Location not found")
        } else if (!result.isDirectory) {
          throw new FileNotFoundException("Location is not a directory")
        } else {
          result
        }
      case _ => throw new IllegalArgumentException("Too many arguments")
    }
    environment.updateCurrentDirectory(newDirectory)
  }
}

private object CdCommandRunnerCompanion {
  val userHomeDir = Directory(System.getProperty("user.home"))
}