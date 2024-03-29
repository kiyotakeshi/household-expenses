# household-expenses

## table of contents

- [requirement](#requirement)

- [architecture](#architecture)

---
## requirement

- kotlin(1.6),java(17)
    - use [asdf](https://asdf-vm.com/guide/getting-started.html) and [asdf-java](https://github.com/halcyon/asdf-java), [asdf-kotlin](https://github.com/asdf-community/asdf-kotlin) plugin

```shell
asdf install java liberica-17.0.3.1+2

asdf install kotlin 1.6.21

$ kotlinc -version
info: kotlinc-jvm 1.6.21 (JRE 17.0.3.1+2-LTS)

$ java -version                                    
openjdk version "17.0.3.1" 2022-04-22 LTS
OpenJDK Runtime Environment (build 17.0.3.1+2-LTS)
OpenJDK 64-Bit Server VM (build 17.0.3.1+2-LTS, mixed mode, sharing)
```

- docker, docker-compose

---
## architecture

@see [architecture.md](./docs/architecture.md)

---
## run

```shell
$ docker compose up -d

# exec flyway migration
# generate OpenAPI, jOOQ code
$ ./gradlew clean build

$ java -jar build/libs/household-expenses-0.0.1-SNAPSHOT.jar
```
