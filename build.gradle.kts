buildscript {
	dependencies {
		classpath("com.mysql:mysql-connector-j:8.3.0")
		classpath("org.flywaydb:flyway-mysql:9.7.0")
	}
}

plugins {
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.flywaydb.flyway") version "9.7.0"
	id("nu.studer.jooq") version "9.0"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jooq:jooq")
	implementation("org.jooq:jooq-codegen")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	jooqGenerator("com.mysql:mysql-connector-j")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val databaseUrl = "jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Tokyo"
val mysqlUser = "user"
val mysqlPassword = "password"

flyway {
	url = databaseUrl
	user = mysqlUser
	password = mysqlPassword
	cleanDisabled = false
}

jooq {
	version.set("3.19.1")  // the default (can be omitted)
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // the default (can be omitted)

	configurations {
		create("main") {  // name of the jOOQ configuration
			generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

			jooqConfiguration.apply {
				jdbc.apply {
					driver = "com.mysql.cj.jdbc.Driver"
					url = databaseUrl
					user = mysqlUser
					password = mysqlPassword
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.mysql.MySQLDatabase"
						inputSchema = "db"
						includes = ".*"
						excludes = "flyway_schema_history"
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = "jooq"
						directory = "build/generated-src/jooq/main"  // default (can be omitted)
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq").configure {
	dependsOn(tasks.named("flywayMigrate"))

	inputs.files(fileTree("src/main/resources/db/migration"))
		.withPropertyName("migrations")
		.withPathSensitivity(PathSensitivity.RELATIVE)

	allInputsDeclared.set(true)
}
