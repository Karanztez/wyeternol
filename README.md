# WyEternol ⚡

A high-performance, modular Minecraft server engine built with **Clean Architecture** on top of the modern Minestom runtime.

## 🌟 Architecture Highlights

* **Decoupled Domain**: `wyeternol-core`, `wyeternol-world`, and `wyeternol-game` have **zero** dependencies on the underlying server engine.
* **Custom Model Layer**: Utilizes `WyPlayer`, `WyWorld`, `WyEventBus`, `WyCommand` for all gameplay logic.
* **Engine Adapter**: `wyeternol-server` bridges the underlying runtime (Minestom) to the WyEternol domain.
* **Default Port**: `25598`
* **Java Version**: Java 25+

## 📁 Modules

* `wyeternol-core` - Domain models, interfaces, config, and event bus
* `wyeternol-world` - Procedural world generation and instance abstraction
* `wyeternol-game` - Game logic, player lifecycle listeners, and commands
* `wyeternol-server` - Standalone executable server runtime adapter

## 🚀 Quick Start

### Run Locally (Development)
```bash
./gradlew :wyeternol-server:run
# or on Windows:
run-server.bat
```

### Build Standalone Fat JAR
```bash
./gradlew :wyeternol-server:shadowJar
# or on Windows:
build-jar.bat
```
The resulting fat jar is created at `build/wyeternol-server.jar`.

### Run the Fat JAR
```bash
java -jar build/wyeternol-server.jar
# or on Windows:
run-jar.bat
```
Connect your Minecraft client via: `localhost:25598`.
