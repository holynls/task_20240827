## Project Structure
### Subprojects
이 프로젝트는 [Gradle Multi-Project Builds](https://docs.gradle.org/current/userguide/multi_project_builds.html) 를 사용하며,
아래와 같은 subprojects 및 디렉토리로 구성되어있습니다.

Hexagonal Architecture 를 적용하여 아래와 같은 subprojects 및 디렉토리로 구성되어있습니다.

```
.
├── domain            #(1)
├── application       #(2)
└── port              #(3)
```
---

#### domain
> 모든 비즈니스 로직과 도메인 모델들이 들어있는 모듈입니다.
> 
> 비즈니스 코드의 결합을 방지하기 위해 다른 어떠한 모듈도 참조하지 않습니다. 

#### application
> 어플리케이션의 진입점이 되는 모듈입니다. `domain`의 코드를 참조하여 비즈니스 로직을 조합하는 역할을 담당하고 있습니다.

#### port
> Hexagonal Architecture 에서의 Port 역할을 하는 모듈입니다. `domain`과 `application` 모듈을 참조하며, 외부와의 통신을 위한 인터페이스를 제공합니다.
> 
> 외부에서 들어오는 요청을 처리하기 위한 `input` 포트와 외부로 나가는 응답을 처리하기 위한 `output` 포트를 제공합니다.
> 
> 현재 가벼운 구현을 위해 mysql이 아닌 H2 데이터베이스를 사용하고 있습니다.


---
### 실행

`./gradlew port:bootRun` 명령어를 통해 어플리케이션을 실행할 수 있습니다.

또한 IntelliJ 를 이용한 실행도 가능합니다.