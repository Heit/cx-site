Introducing Circumflex
======================

Circumflex is a set of software components for quick and robust application development
using [Scala][] programming language.

Circumflex consists of several separate projects:

  * [Circumflex Web Framework](/web.html)
  * [Circumflex ORM](/orm/html)
  * [Circumflex FreeMarker Views](ftl.html) 
  * [Circumflex Markdown](md.html)
  * [Circumflex Docco](docco.html)
  * [Circumflex Maven Plugin](plugin.html)

## At a glance

All Circumflex components share the same phylosophy: the development process should be
natural and intuitive. They rely on Scala support for domain-specific languages that make
the development extremely efficient. And simple.

Here's a simple web application:

    class Main extends RequestRouter {
      get("/") = "Hello world!"     // match GET /
      post("/form") = {             // match POST /form
        // do some work
        "Form processing complete."
      }
    }


And here's simple domain model:

    // define records
    class Person extends Record[Person] {
      val login = field(Person.login)
      val name = field(Person.name)
      val accounts = oneToMany(Account.person)
    }

    // define a relation, where records are stored
    object Person extends Table[Person] {
      val login   = stringColumn("login")
                    .notNull
                    .unique
                    .validatePattern("^[a-zA-Z0-9_-]{3,8}$")
      val name    = stringColumn("name")
                    .notNull
                    .validateNotEmpty
      primaryKey(login)
    }

Awesome, you say?

## Why Circumflex?

  * Circumflex components require minimum initial configuration, while still allowing
  developers to easily override defaults if necessary.
  * Circumflex is based on Scala. It has all the benefits of Scala.
  It runs on JVM. It is fast. It is concise.
  * Circumflex does not try to solve all the problems the developer might ever face.
  It keeps minimal features set, allowing developers to choose the tools and libraries
  that best suit their particular needs.
  * Circumflex is designed to use the powers of Apache Maven 2 software management
  platform. Adding Circumflex components to your project is a matter of few more lines
  in your `pom.xml`.
  * All Circumflex components are designed for ease-of-use and clarity of your code.
  The development process with Circumflex is intuitive and extremely productive. 
  * Circumflex is completely free, with BSD-style [license](/license.html).

## Quick start

### Use with existing projects

If you already have a project and wish to use one of the Circumflex components, just
 add the corresponding dependency to your project's `pom.xml`:

    <properties>
      <cx.version><!-- desired version --></cx.version>
    </properties>
    <!-- Circumflex Web Framework -->
    <dependency>
      <groupId>ru.circumflex</groupId>
      <artifactId>circumflex-core</artifactId>
      <version>{cx.version}</version>
    </dependency>
    <!-- Circumflex ORM -->
    <dependency>
      <groupId>ru.circumflex</groupId>
      <artifactId>circumflex-orm</artifactId>
      <version>{cx.version}</version>
    </dependency>
    <!-- Circumflex Freemarker Views -->
    <dependency>
      <groupId>ru.circumflex</groupId>
      <artifactId>circumflex-ftl</artifactId>
      <version>{cx.version}</version>
    </dependency>
    <!-- Circumflex Markdown -->
    <dependency>
      <groupId>ru.circumflex</groupId>
      <artifactId>circumflex-md</artifactId>
      <version>{cx.version}</version>
    </dependency>
    <!-- Circumflex Docco -->
    <dependency>
      <groupId>ru.circumflex</groupId>
      <artifactId>circumflex-docco</artifactId>
      <version>{cx.version}</version>
    </dependency>

Note, that all Circumflex components should have the same version. Check out the
[Central Maven Repository][m2-central] to determine the latest version.

### Create new project

As soon as Circumflex is built, you are ready to create your first project. Change
to the directory where you store your projects and run:

    $ mvn archetype:generate

Choose the **circumflex-archetype** from your local catalog:

    Choose archetype:
    1: local -> circumflex-archetype (Circumflex Application Archetype)
    2: internal -> . . .
    Choose a number:  (1/2/3/ . . .) 17: : 1

Provide basic information about your project:

    Define value for groupId: : com.myapp
    Define value for artifactId: : myapp
    Define value for version:  1.0-SNAPSHOT: : 1.0
    Define value for package:  com.myapp: :

After you confirm your choice, a simple Circumflex application will be created. To run
it, go to your project root (it matches `artifactId` that you have specified above)
and execute the following:

    $ mvn compile jetty:run

The following lines indicate that your application is ready to serve requests:

    [INFO] Started Jetty Server
    [INFO] Starting scanner at interval of 5 seconds.

Now you may visit your application at <http://localhost:8180>.

### Build from sources

You can obtain latest Circumflex sources at [GitHub][gh-cx]:

    $ git clone git://github.com/inca/circumflex.git

Circumflex, like all Scala applications, is compiled into Java VM bytecode. Make sure
that latest [Java 6 SDK][jdk] is installed on your system.

Circumflex uses [Apache Maven 2][m2] for build management. If you don't already have
Maven 2, [install it][m2-install]. Note, that some operating systems (e.g. Mac OS X
10.5 and higher) are shipped with Maven 2 by default. On some systems it is also
preferrable to install Maven 2 via package managers. For example, on Debian or Ubuntu
systems you may install Maven 2 by executing the following line:

    $ sudo apt-get install maven2

If you are unfamiliar with Maven, you should probably read the [Maven in 5 Minutes][m2-5min]
article or [Getting Started Guide][m2-gsg].

Once you are ready to build, execute the following in Circumflex root directory:

    $ mvn clean install

After the build has successfully finished, Circumflex with all it's dependencies will
be available in your local Maven 2 repository (it may take a while to download
dependencies the first time).

  [scala]: http://scala-lang.org
  [jdk]: http://java.sun.com/javase/downloads/index.jsp
  [m2]: http://maven.apache.org
  [m2-install]: http://maven.apache.org/download.html#Installation
  [m2-5min]: http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
  [m2-gsg]: http://maven.apache.org/guides/getting-started/index.html
  [m2-central]: http://repo2.maven.org/maven2/ru/circumflex/circumflex-parent
  [gh-cx]: http://github.com/inca/circumflex