# OpenSavvy Sentier

> _Sentier_ • French word for "winding path", "trail"

Traditional logging allows to extract information about the current state of a program. However, in highly parallel systems, it can quickly be hard to find which lines are related to which other. Despite software programs being highly structured, logging frameworks usually carry no structure information.

While events tracked by Sentier are analogous to logger lines, Sentier additionally tracks their parent-child relationship. This allows developers to acquire knowledge about the internal workings of their system, for example finding exactly which database requests were made by which API endpoint, and how long they took. Additionally, Sentier can work across system boundaries, to see which client-side actions trigger which network requests.

Philosophically, Sentier is the middle ground between a debugger (which has complete state information at a given point in time) and a profiler (which has an overall view of what happened): Sentier has information coarse enough to create statistics on (e.g. measure whether a new version has increased the overall performance), but detailed enough to debug complex cases that a profiler wouldn't explain.

## Use with…

Much like [Slf4J](https://www.slf4j.org/) is for Java logging, Sentier doesn't do any analysis by itself: Sentier is a convenient facade which delegates processing to other tools.

[//]: # (TODO: in the future, add a list of compatible tools here)

## License

This project is licensed under the [Apache 2.0 license](LICENSE).

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md).
- To learn more about our coding conventions and workflow, see the [OpenSavvy website](https://opensavvy.dev/open-source/index.html).
- This project is based on the [OpenSavvy Playground](docs/playground/README.md), a collection of preconfigured project templates.

If you don't want to clone this project on your machine, it is also available using [DevContainer](https://containers.dev/) (open in [VS Code](https://code.visualstudio.com/docs/devcontainers/containers) • [IntelliJ & JetBrains IDEs](https://www.jetbrains.com/help/idea/connect-to-devcontainer.html)). Don't hesitate to create issues if you have problems getting the project up and running.
