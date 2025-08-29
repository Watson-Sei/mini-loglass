# DBをH2にする方法

以下のコードをコピーして該当ファイルを更新して`make server/start`でアプリケーションを起動する

`build.gradle.kts`

```
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
	runtimeOnly("com.h2database:h2:2.2.220")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	jooqGenerator("com.h2database:h2:2.2.220")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

flyway {
	url = "jdbc:h2:~/testdb;AUTO_SERVER=TRUE"
	user = "sa"
	password = ""
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
					driver = "org.h2.Driver"
					url = "jdbc:h2:~/testdb;AUTO_SERVER=TRUE"
					user = "sa"
					password = ""
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.h2.H2Database"
						inputSchema = "PUBLIC"
						includes = ".*"
						excludes = ""
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
```

`src/main/resources/application.properties`

```
spring.application.name=accounting

spring.datasource.url=jdbc:h2:~/testdb;AUTO_SERVER=TRUE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.initialize=true
spring.h2.console.enabled=true

spring.flyway.default-schema=PUBLIC
```


`src/main/resources/db/migration/V3__Create_journal_table.sql`

```sql
CREATE TABLE PUBLIC.journals
(
    journal_no        VARCHAR(30) NOT NULL,
    journal_date      DATE NOT NULL,
    department_code   VARCHAR(30) NOT NULL CONSTRAINT fk_department_code REFERENCES departments(department_code),
    PRIMARY KEY (journal_no)
);

CREATE TABLE PUBLIC.journal_items
(
    journal_no        VARCHAR(30) NOT NULL CONSTRAINT fk_journal_no REFERENCES journals(journal_no),
    row_no            INTEGER NOT NULL,
    debit_credit      VARCHAR(20) NOT NULL,
    account_code      VARCHAR(30) NOT NULL CONSTRAINT fk_account_code REFERENCES accounts(account_code),
    amount            INTEGER NOT NULL,
    PRIMARY KEY (journal_no, row_no)
);

-- 仕訳
INSERT INTO journals(journal_no, journal_date, department_code) VALUES ('1000000000', '2024-01-01', '1001');
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000000', 1, 'DEBIT', '3001', 1000);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000000', 2, 'CREDIT', '1001', 1000);
INSERT INTO journals(journal_no, journal_date, department_code) VALUES ('1000000001', '2024-01-03', '1002');
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000001', 1, 'DEBIT', '3001', 2000);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000001', 2, 'CREDIT', '1001', 2000);
INSERT INTO journals(journal_no, journal_date, department_code) VALUES ('1000000002', '2024-01-19', '1002');
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000002', 1, 'DEBIT', '3002', 2000);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000002', 2, 'CREDIT', '1011', 2000);
INSERT INTO journals(journal_no, journal_date, department_code) VALUES ('1000000003', '2024-01-20', '1001');
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000003', 1, 'DEBIT', '2001', 500);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000003', 2, 'DEBIT', '2003', 100);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000003', 3, 'CREDIT', '3001', 600);
INSERT INTO journals(journal_no, journal_date, department_code) VALUES ('1000000004', '2024-01-20', '1001');
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000004', 1, 'DEBIT', '3001', 2000);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000004', 2, 'CREDIT', '4001', 2000);
INSERT INTO journals(journal_no, journal_date, department_code) VALUES ('1000000005', '2024-01-21', '1002');
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000005', 1, 'DEBIT', '1011', 2000);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000005', 2, 'CREDIT', '3002', 2000);
INSERT INTO journals(journal_no, journal_date, department_code) VALUES ('1000000006', '2024-01-21', '1002');
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000006', 1, 'DEBIT', '3002', 1500);
INSERT INTO journal_items(journal_no, row_no, debit_credit, account_code, amount) VALUES ('1000000006', 2, 'CREDIT', '1011', 1500);
````
